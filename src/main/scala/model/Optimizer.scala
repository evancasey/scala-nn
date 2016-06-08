package model

import breeze.linalg._

trait Optimizer {
  val cost: Cost
  val learningRate: Double

  // Returns updated weights
  //def minimize(backProp: Any)(weights: Seq[DenseMatrix[Double]], deltas: Seq[DenseMatrix[Double]]): DenseMatrix[Double]
}

case class GradientDescentOptimizer(cost: Cost, learningRate: Double) extends Optimizer {


}
