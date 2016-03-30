package model

trait Optimizer {
  def minimize(cost: Cost): Optimizer
}

class GradientDescentOptimizer extends Optimizer {
  def minimize(cost: Cost) = ???

  //    private def gradientDescent(
  //      trainData: TrainData,
  //      itw: DenseVector[Double],
  //      maxIterations: Int,
  //      learningRate: Double): DenseVector[Double] = {
  //
  //      for (i <- 1 to maxIterations) {
  //        println("On iteration " + i)
  //        val gradient: DenseVector[Double] = trainData.map { point =>
  //          point.featureVector * point.label * ((1 / (1 + exp(itw.dot(point.featureVector) * -point.label))) - 1)
  //        }.reduce(_ + _)
  //
  //        println("Target w:" + itw)
  //        itw -= gradient * learningRate
  //      }
  //
  //      println("Final w: " + itw)
  //      itw
  //    }
}
