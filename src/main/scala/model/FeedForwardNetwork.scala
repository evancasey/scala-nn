package model

trait FeedForwardNetwork[A] {

  def layers: Iterable[A]

  def ++(other: FeedForwardNetwork[A]): FeedForwardNetwork[A]

}

case class FFN(layers: Seq[Layer]) extends FeedForwardNetwork[Layer] {

  def ++(other: FeedForwardNetwork[Layer]): FFN = {
    val cLayers = layers
      .zip(other.layers)
      .map { case (l1, l2) => combinePlaceholders(l1, l2) }
    FFN(cLayers)
  }

  def merge(other: FFN, batchSize: Int, optimizer: Optimizer): FFN = {
    // call opt.minimize to apply gradUpdates
    // do scaling logic here
//    val scaledNetwork = FFN(
//      layers = other.layers.map(_.scale(1.0 - (optimizer.learningRate / batchSize)))
//    )
//    this ++ scaledNetwork
    ???
  }

  private def combinePlaceholders(l1: Layer, l2: Layer): Layer = {
    //TODO: assert same activation
    //assert(l1.activation == l2.activation)
    Layer(
      weightMatrix = l1.weightMatrix + l2.weightMatrix,
      biasMatrix = l1.biasMatrix + l2.biasMatrix,
      activation = l1.activation
    )
  }

}

object FFN {

  def apply(layers: Iterable[Layer]): FeedForwardNetwork[Layer] = {
    FFN(layers.to[Seq])
  }

  def zero: FFN = FFN(Vector.empty)

}

case class CachedFFN(layers: Vector[CachedLayer]) extends FeedForwardNetwork[CachedLayer] {

  def ++(other: FeedForwardNetwork[CachedLayer]): FeedForwardNetwork[CachedLayer] = {
    val cLayers = layers
      .zip(other.layers)
      .map { case (l1, l2) => combineLayers(l1, l2) }
    CachedFFN(cLayers)
  }

  private def combineLayers(l1: CachedLayer, l2: CachedLayer): CachedLayer = {
    //TODO: assert same activation
    //assert(l1.activation == l2.activation)
    CachedLayer(
      weightMatrix = l1.weightMatrix + l2.weightMatrix,
      biasMatrix = l1.biasMatrix + l2.biasMatrix,
      activation = l1.activation,
      z = l1.z + l2.z,
      sigma = l1.sigma + l2.sigma
    )
  }

}

object CachedFFN {

  def apply(layers: Iterable[CachedLayer]): CachedFFN = {
    CachedFFN(layers.to[Vector])
  }

  def zero: FeedForwardNetwork[CachedLayer] = CachedFFN(Vector.empty[CachedLayer])

}


