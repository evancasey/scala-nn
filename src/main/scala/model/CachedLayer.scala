package model

import breeze.linalg.DenseMatrix
import breeze.linalg._

//TODO: add l1/l2 regularization
case class CachedLayer(
  weightMatrix: DenseMatrix[Double],
  biasMatrix: DenseMatrix[Double],
  activation: Activation,
  z: DenseMatrix[Double],
  sigma: DenseMatrix[Double]
) {


  def applyDelta(delta: DenseMatrix[Double]): Layer = {
    Layer(
      weightMatrix = delta * sigma.t,
      biasMatrix = delta,
      activation = activation
    )
  }

}


case class Layer(
  weightMatrix: DenseMatrix[Double],
  biasMatrix: DenseMatrix[Double],
  activation: Activation
) {

  def z(input: DenseMatrix[Double]): DenseMatrix[Double] = (weightMatrix * input) + biasMatrix

  def scale(factor: Double): Layer = {
    Layer(
      weightMatrix = weightMatrix * factor,
      biasMatrix = biasMatrix * factor,
      activation: Activation
    )
  }

}

