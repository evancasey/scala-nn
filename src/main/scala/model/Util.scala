package model

import breeze.linalg.DenseVector

object Util {

  case class Placeholder(numRows: Int, numColumns: Int) {

    lazy val vec: DenseVector[Double] = ???
  }
}
