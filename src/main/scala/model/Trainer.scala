package model

import breeze.linalg.DenseMatrix
import breeze.linalg._

// TODO: require compat. matrices, add logging
case class Trainer(optimizer: Optimizer) {
  import Util._

  def train(network: FeedForwardNetwork, trainingData: TrainingData): FeedForwardNetwork = {
    val (trainX, trainY) = trainingData
    backpropagate(forwardpropagate(network,trainX), trainY)
  }

  /**
    * Does forwardpropagation for a single input matrix (can hold any number of input vectors)
    */
  def forwardpropagate(network: FeedForwardNetwork, trainX: DenseMatrix[Double]): (DenseMatrix[Double], FeedForwardNetwork) = {
    def step(input: DenseMatrix[Double], layer: Layer): (DenseMatrix[Double], Layer) = {
      val z = layer.z(input)

      val newLayer = Layer(
        weightMatrix = layer.weightMatrix,
        biasMatrix = layer.biasMatrix,
        activation = layer.activation,
        z = Some(z),
        sigma = Some(input)
      )

      val sigma = layer.sigma(z)

      (sigma: DenseMatrix[Double], newLayer: Layer)
    }

    val (outputSigma, layers) = mapAccumLeft(network.layers)(trainX, step)
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
      val newDelta = (layer.weightMatrix.t * delta) * layer.sigmaPrime(layer.z.get)
      val nablaLayer = updateLayer(layer, newDelta)

      (newDelta: DenseMatrix[Double], nablaLayer: Layer)
    }

    val (_, nablaHiddenLayers) = mapAccumRight(hiddenLayers)(outputDelta, step)
    FeedForwardNetwork(nablaHiddenLayers :+ nablaOutputLayer)
  }

  /**
    * TODO: Move this to FeedForwardNetworkMonoid???
    * Updates the network after doing backprop on a minibatch by combining the backprop'd network with the "base" one.
    * Note: we can think of this as an associative op: (x + y) + z = x + (y + z)
    */
  def updateNetwork(
      minibatchSize: Int,
      baseNetwork: FeedForwardNetwork,
      nablasNetwork: FeedForwardNetwork): FeedForwardNetwork = {
    val ffnMonoid = new FeedForwardNetworkMonoid
    val scaledNetwork = FeedForwardNetwork(
      layers = nablasNetwork.layers.map(_.scale(1 - (optimizer.learningRate / minibatchSize)))
    )
    ffnMonoid.plus(baseNetwork, scaledNetwork)
  }

  /**
    * Applies a layer update for an arbitrary delta to the provided layer and returns a new layer. This copies over the z
    * and sigma values to be accessed on future iterations.
    */
  def updateLayer(layer: Layer, delta: DenseMatrix[Double]): Layer = {
    val z = layer.z.get
    val sigma = layer.sigma.get

    Layer(
      weightMatrix = delta * sigma.t,
      biasMatrix = delta,
      activation = layer.activation,
      z = Some(z),
      sigma = Some(sigma)
    )
  }
}
