package model

import breeze.linalg.DenseMatrix
import breeze.linalg._

case class Layer(
  weightMatrix: DenseMatrix[Double],
  biasMatrix: DenseMatrix[Double],
  activation: Activation,
  z: Option[DenseMatrix[Double]] = None,
  sigma: Option[DenseMatrix[Double]] = None
) {
  //TODO: add l1/l2 regularization

  //TODO: figure out a better way to cache these instead of recomputing everytime....
  def z(input: DenseMatrix[Double]): DenseMatrix[Double] = (weightMatrix * input) + biasMatrix

  def sigma(z: DenseMatrix[Double]): DenseMatrix[Double] = activation.transform(z)

  def sigmaPrime(z: DenseMatrix[Double]): DenseMatrix[Double] = activation.transformPrime(z)

  def scale(factor: Int): Layer = {
    Layer(
      weightMatrix = weightMatrix * factor,
      biasMatrix = biasMatrix * factor,
      z = Some(z.get * factor),
      sigma = Some(sigma.get * factor),
      activation = activation
    )
  }
}
