package model

import breeze.linalg.DenseVector

object Util {

  /* Builder Utils */

  abstract class TRUE
  abstract class FALSE

  /* Breez Utils */

  case class Placeholder(numRows: Int, numColumns: Int) {

    lazy val vec: DenseVector[Double] = ???
  }
}
