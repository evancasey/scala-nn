package model

import breeze.linalg.{DenseMatrix, DenseVector}
import breeze.numerics.sigmoid

trait Activation {

  def transform(hiddenLayer: DenseMatrix[Double])
}

case class SigmoidActivation(features: DenseVector) extends Activation {

  /* Equivalent to:  1 / (1 + exp(-x)) where x is each value in matrix */
  def transform(hiddenLayer: DenseMatrix[Double]) = sigmoid(hiddenLayer)
}
