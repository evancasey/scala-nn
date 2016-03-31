package model

import breeze.linalg.DenseMatrix
import breeze.numerics.exp
import model.Util.{FALSE, TRUE}

// TODO: allow multiple types of dense matrix!
case class MultiLayerPerceptron[A, L, O](
  activation: Option[Activation],
  layers: Seq[Layer],
  optimizer: Option[Optimizer]) {

  def withActivation(providedActivation: Activation) = {
    MultiLayerPerceptron[TRUE, L, O](
      activation = Some(providedActivation),
      layers = layers,
      optimizer = optimizer
    )
  }

  def withLayer(providedLayer: Layer) = {
    MultiLayerPerceptron[A, TRUE, O](
      activation = activation,
      layers = layers :+ providedLayer,
      optimizer = optimizer
    )
  }

  def withOptimizer(providedOptimizer: Optimizer) = {
    MultiLayerPerceptron[A, L, TRUE](
      activation = activation,
      layers = layers,
      optimizer = Some(providedOptimizer)
    )
  }

}

object MultiLayerPerceptron {
  implicit def enableBuild(builder: MultiLayerPerceptron[TRUE, TRUE, TRUE]) =
    new {

      private val activation = builder.activation.get
      private val layers = builder.layers
      private val optimizer = builder.optimizer.get

      def train(
        trainX: DenseMatrix[Double],
        trainY: DenseMatrix[Double],
        learningRate: Int,
        numIterations: Int): MultiLayerPerceptronClassifier = {

        val activation = builder.activation.get

        ???
      }

      def forwardProp(trainX: DenseMatrix[Double]): DenseMatrix[Double] = {
        // require compat. matrices
        // change to log.debug
        layers.foldLeft(trainX){ (input, layer) =>
          println(s"input: $input")
          println(s"layer weights: ${layer.weightMatrix}")
          val zw = input * layer.weightMatrix // TODO: add biases!

          println(s"zw: $zw")
          val activ = activation.transform(zw)

          println(s"activ: $activ")
          println
          activ
        }
      }
    }

  def builder = new MultiLayerPerceptron[FALSE, FALSE, FALSE](
    activation = None,
    layers = Seq.empty,
    optimizer = None
  )
}
