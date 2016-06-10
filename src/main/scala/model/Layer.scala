package model

import breeze.linalg.DenseMatrix
import breeze.linalg._

//TODO: add l1/l2 regularization
case class Layer(
  weightMatrix: DenseMatrix[Double],
  biasMatrix: DenseMatrix[Double],
  activation: Activation,
  z: DenseMatrix[Double],
  sigma: DenseMatrix[Double]
) {

  def scale(factor: Double): Layer = {
    Layer(
      weightMatrix = weightMatrix * factor,
      biasMatrix = biasMatrix * factor,
      z = z * factor,
      sigma = sigma * factor,
      activation = activation
    )
  }
}
