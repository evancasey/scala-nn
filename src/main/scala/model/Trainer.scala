package model

import breeze.linalg.DenseMatrix
import breeze.linalg._

// TODO: require compat. matrices, add logging
case class Trainer(optimizer: Optimizer) {
  import Util._

  def train(placeholders: Seq[Placeholder], trainingData: TrainingData): FeedForwardNetwork = {
    val miniBatches: Seq[TrainingData] = ???
    val miniBatchSize: Int = ???

    miniBatches.map { batch =>
      val (trainX, trainY) = batch
      backpropagate(forwardpropagate(placeholders,trainX), trainY)
    }.reduce { (l, r) =>
      updateNetwork(miniBatchSize, l, r)
    }
  }

  /**
    * Does forwardpropagation for a single input matrix (can hold any number of input vectors)
    */
  def forwardpropagate(placeholders: Seq[Placeholder], trainX: DenseMatrix[Double]): (DenseMatrix[Double], FeedForwardNetwork) = {
    def step(input: DenseMatrix[Double], placeholder: Placeholder): (DenseMatrix[Double], Layer) = {
      val z = placeholder.z(input)

      val layer = Layer(
        weightMatrix = placeholder.weightMatrix,
        biasMatrix = placeholder.biasMatrix,
        activation = placeholder.activation,
        z = z,
        sigma = input
      )

      val sigma = placeholder.activation.transform(z)
      (sigma: DenseMatrix[Double], layer: Layer)
    }

    val (outputSigma, layers) = mapAccumLeft(placeholders)(trainX, step)
    (outputSigma, FeedForwardNetwork(layers))
  }

  /**
    * Does backpropagation over a pre-forwardprop'd network and a single output matrix (can hold any number of output points)
    */
  def backpropagate(forward: (DenseMatrix[Double], FeedForwardNetwork), trainY: DenseMatrix[Double]): FeedForwardNetwork = {
    val (outputSigma, network) = forward
    val (hiddenLayers, outputLayer) = (network.layers.dropRight(1), network.layers.last)

    val outputDelta = optimizer.cost.delta(outputLayer, outputSigma, trainY)
    val nablaOutputLayer = updateLayer(outputLayer, outputDelta)

    def step(delta: DenseMatrix[Double], layer: Layer): (DenseMatrix[Double], Layer) = {
      val newDelta = (layer.weightMatrix.t * delta) * layer.activation.transformPrime(layer.z)
      val nablaLayer = updateLayer(layer, newDelta)

      (newDelta: DenseMatrix[Double], nablaLayer: Layer)
    }

    val (_, nablaHiddenLayers) = mapAccumRight(hiddenLayers)(outputDelta, step)
    FeedForwardNetwork(nablaHiddenLayers :+ nablaOutputLayer)
  }

  /**
    * Updates the network after doing backprop on a minibatch by combining the backprop'd network with the "base" one.
    * Note: this as only associative for vanilla SGD
    */
  def updateNetwork(
      minibatchSize: Int,
      baseNetwork: FeedForwardNetwork,
      nablasNetwork: FeedForwardNetwork): FeedForwardNetwork = {
    val ffnMonoid = new FeedForwardNetworkMonoid
    val scaledNetwork = FeedForwardNetwork(
      layers = nablasNetwork.layers.map(_.scale(1.0 - (optimizer.learningRate / minibatchSize)))
    )
    ffnMonoid.plus(baseNetwork, scaledNetwork)
  }

  /**
    * Applies a layer update for an arbitrary delta to the provided layer and returns a new layer. This copies over the z
    * and sigma values to be accessed on future iterations.
    */
  def updateLayer(layer: Layer, delta: DenseMatrix[Double]): Layer = {
    Layer(
      weightMatrix = delta * layer.sigma.t,
      biasMatrix = delta,
      activation = layer.activation,
      z = layer.z,
      sigma = layer.sigma
    )
  }
}
