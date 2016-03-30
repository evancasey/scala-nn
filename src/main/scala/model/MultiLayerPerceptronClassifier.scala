package model

import breeze.linalg.DenseMatrix

// TODO: add type-safe builder
case class MultiLayerPerceptronClassifier(
  activation: Option[Activation],
  layers: Seq[Layer],
  optimizer: Option[Optimizer]) {

  def withActivation(providedActivation: Activation) = {
    MultiLayerPerceptronClassifier(
      activation = Some(providedActivation),
      layers = layers,
      optimizer = optimizer
    )
  }

  def withLayer(providedLayer: Layer) = {
    MultiLayerPerceptronClassifier(
      activation = activation,
      layers = layers :+ providedLayer,
      optimizer = optimizer
    )
  }

  def withOptimizer(providedOptimizer: Optimizer) = {
    MultiLayerPerceptronClassifier(
      activation = activation,
      layers = layers,
      optimizer = Some(providedOptimizer)
    )
  }

  // TODO: allow multiple types of dense matrix!
  def train(
    trainX: DenseMatrix[Double],
    trainY: DenseMatrix[Double],
    learningRate: Int,
    numIterations: Int) = ???

  def predict(testX: DenseMatrix[Double]): Int = ???

  /**
    * Iterate over layers
    *
    *
    */
  def forwardProp(trainX: DenseMatrix[Double]) = ???
}
