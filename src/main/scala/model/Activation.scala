package model

import breeze.linalg.DenseMatrix
import breeze.numerics.sigmoid

trait Activation {

  def transform(hiddenLayer: DenseMatrix[Double]): DenseMatrix[Double]
}

object SigmoidActivation extends Activation {

  /* Equivalent to:  1 / (1 + exp(-x)) where x is each value in matrix */
  def transform(hiddenLayer: DenseMatrix[Double]): DenseMatrix[Double] = sigmoid(hiddenLayer)
}
