package model

import breeze.linalg.DenseMatrix

object Util {

  /* Types */

  type TrainX = DenseMatrix[Double]
  type TrainY = DenseMatrix[Double]
  type TrainingData = (TrainX, TrainY)

  /* Builder Utils */

  abstract class TRUE

  abstract class FALSE

  /* Seq impl of MapAccum */

  /**
    *  This is a mapAccumLeft with out reversing the output list
    */
  final def mapAccum[A, B, C](as: Seq[A])(c: C, f: (C, A) => (C, B)): (C, Seq[B]) =
    as.foldLeft((c, Nil: Seq[B])) { case ((c, bs), a) =>
      val (c0, b) = f(c, a)
      (c0, b +: bs)
    }

  /** All of the `B`s, in order, and the final `C` acquired by a
    * stateful left fold over `as`. */
  final def mapAccumLeft[A, B, C](as: Seq[A])(c: C, f: (C, A) => (C, B)): (C, Seq[B]) = {
    val (c0, list) = mapAccum(as)(c, f)
    (c0, list.reverse)
  }

  /** All of the `B`s, in order `as`-wise, and the final `C` acquired
    * by a stateful right fold over `as`. */
  final def mapAccumRight[A, B, C](as: Seq[A])(c: C, f: (C, A) => (C, B)): (C, Seq[B]) =
    mapAccum(as.reverse)(c, f)



  final def zipWithIndex[A](as: Seq[A]): Seq[(Int, A)] =
    mapAccumLeft(as)(0, (i: Int, a: A) => (i + 1, (i: Int, a: A)))._2
}

