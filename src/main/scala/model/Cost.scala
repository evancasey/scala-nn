package model

import breeze.linalg.{sum, DenseMatrix}
import breeze.numerics.pow

trait Cost {

  def cost(yHat: DenseMatrix[Double], y: DenseMatrix[Double]): Double

  def costPrime(z: DenseMatrix[Double]): Double

  def delta(layer: CachedLayer, yHat: DenseMatrix[Double], trainY: DenseMatrix[Double]): DenseMatrix[Double]
}

object L2Cost extends Cost {

  def cost(yHat: DenseMatrix[Double], y: DenseMatrix[Double]): Double = sum(pow(y - yHat, 2)) * 0.5

  def costPrime(z: DenseMatrix[Double]): Double = ???

  def delta(layer: CachedLayer, yHat: DenseMatrix[Double], trainY: DenseMatrix[Double]): DenseMatrix[Double] = ???
}
