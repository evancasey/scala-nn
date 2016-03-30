package model

import breeze.linalg.DenseVector
import model.Util.Placeholder

case class Layer(
  placeholder: Placeholder,
  weightVec: DenseVector[Double],
  biasVec: DenseVector[Double]
) {

  def xxx = ???
}
