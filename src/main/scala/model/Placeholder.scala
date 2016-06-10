package model

import breeze.linalg.DenseMatrix

case class Placeholder(
  weightMatrix: DenseMatrix[Double],
  biasMatrix: DenseMatrix[Double],
  activation: Activation
) {

  def z(input: DenseMatrix[Double]): DenseMatrix[Double] = (weightMatrix * input) + biasMatrix

}
