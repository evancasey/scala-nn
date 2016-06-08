package model

object FeedForwardNetwork {
  def apply(layers: Seq[Layer]): FeedForwardNetwork = {
    FeedForwardNetwork(layers.to[Vector])
  }

  def combineLayers(l1: Layer, l2: Layer): Layer = {
    //TODO: assert same activation
    //assert(l1.activation == l2.activation)
    Layer(
      weightMatrix = l1.weightMatrix + l2.weightMatrix,
      biasMatrix = l1.biasMatrix + l2.biasMatrix,
      activation = l1.activation,
      z = Some(l1.z.get + l2.z.get),
      sigma = Some(l1.sigma.get + l2.sigma.get)
    )
  }
}

case class FeedForwardNetwork(layers: Vector[Layer])

class FeedForwardNetworkMonoid extends Monoid[FeedForwardNetwork] {
  import FeedForwardNetwork._

  def zero: FeedForwardNetwork = FeedForwardNetwork(Seq.empty[Layer])

  def plus(l: FeedForwardNetwork, r: FeedForwardNetwork): FeedForwardNetwork = {
    val cLayers = l.layers
      .zip(r.layers)
      .map { case (l1, l2) => combineLayers(l1, l2) }
    FeedForwardNetwork(cLayers)
  }
}

