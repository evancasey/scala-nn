package model

import breeze.linalg.DenseMatrix
import breeze.linalg._

// TODO: require compat. matrices, add logging
case class Trainer(optimizer: Optimizer) {

  import Util._

  def train(placeholders: Seq[Layer], trainingData: Seq[(TrainX, TrainY)]): FFN = {

    val miniBatches: Seq[MiniBatch] = trainingData
    val miniBatchSize: Int = trainingData.size

    def step(network: FFN, batch: MiniBatch): FFN = {
      val (trainX, trainY) = batch
      val update = backpropagate(forwardpropagate(network.layers, trainX), trainY)

      network.merge(update, miniBatchSize, optimizer)
    }

    miniBatches.foldLeft(FFN(placeholders))(step)
  }

  /**
    * Does forward propagation for a single input matrix (can hold any number of input vectors)
    */
  def forwardpropagate(placeholders: Seq[Layer], trainX: DenseMatrix[Double]): (DenseMatrix[Double], CachedFFN) = {
    def step(input: DenseMatrix[Double], layer: Layer): (DenseMatrix[Double], CachedLayer) = {
      val z = layer.z(input)

      val cachedLayer = CachedLayer(
        weightMatrix = layer.weightMatrix,
        biasMatrix = layer.biasMatrix,
        activation = layer.activation,
        z = z,
        sigma = input
      )

      val sigma = layer.activation.transform(z)
      (sigma: DenseMatrix[Double], cachedLayer: CachedLayer)
    }

    val (outputSigma, layers) = mapAccumLeft(placeholders)(trainX, step)
    (outputSigma, CachedFFN(layers))
  }

  /**
    * Does backpropagation over a pre-forwardprop'd network and a single output matrix (can hold any number of output points)
    */
  def backpropagate(forward: (DenseMatrix[Double], CachedFFN), trainY: DenseMatrix[Double]): FFN = {
    val (outputSigma, network) = forward
    val (hiddenLayers, outputLayer) = (network.layers.dropRight(1), network.layers.last)

    val outputDelta = optimizer.cost.delta(outputLayer, outputSigma, trainY)
    val nablaOutputLayer = outputLayer.applyDelta(outputDelta)

    def step(delta: DenseMatrix[Double], layer: CachedLayer): (DenseMatrix[Double], Layer) = {
      val newDelta = (layer.weightMatrix.t * delta) * layer.activation.transformPrime(layer.z)
      val nablaLayer = layer.applyDelta(newDelta)

      (newDelta: DenseMatrix[Double], nablaLayer: Layer)
    }

    val (_, nablaHiddenLayers) = mapAccumRight(hiddenLayers)(outputDelta, step)
    FFN(nablaHiddenLayers :+ nablaOutputLayer)
  }
}
