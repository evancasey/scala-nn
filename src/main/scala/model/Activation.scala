package model

import breeze.linalg.DenseMatrix
import breeze.numerics.sigmoid

trait Activation {

  def transform(z: DenseMatrix[Double]): DenseMatrix[Double]

  def transformPrime(z: DenseMatrix[Double]): DenseMatrix[Double]
}

object SigmoidActivation extends Activation {

  /**
    *  Equivalent to:  1 / (1 + exp(-x)) where x is each value in matrix
    */
  def transform(z: DenseMatrix[Double]): DenseMatrix[Double] = sigmoid(z)

  def transformPrime(z: DenseMatrix[Double]): DenseMatrix[Double] = ???
}
