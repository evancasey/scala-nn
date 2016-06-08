package builder

import breeze.linalg.DenseMatrix
import model.Util._
import model._

case class MultiLayerPerceptron[L, O](
  layers: Seq[Layer],
  optimizer: Option[Optimizer]) {

  def withLayer(providedLayer: Layer) = {
    MultiLayerPerceptron[TRUE, O](
      layers = layers :+ providedLayer,
      optimizer = optimizer
    )
  }

  def withOptimizer(providedOptimizer: Optimizer) = {
    MultiLayerPerceptron[L, TRUE](
      layers = layers,
      optimizer = Some(providedOptimizer)
    )
  }

}

object MultiLayerPerceptron {
  implicit def enableBuild(builder: MultiLayerPerceptron[TRUE, TRUE]) =
    new {

      import Util._

      def train(
        trainX: DenseMatrix[Double],
        trainY: DenseMatrix[Double],
        numIterations: Int): MultiLayerPerceptronClassifier = {

        ???
      }
    }

  def builder = new MultiLayerPerceptron[FALSE, FALSE](
    layers = Seq.empty,
    optimizer = None
  )
}
