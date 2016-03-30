package model

import breeze.linalg.{DenseMatrix, DenseVector}
import org.scalatest._
import Matchers._

class MultiLayerPerceptronClassifierSpec extends WordSpec {

  "ForwardProp" should {
    "propagate weights correctly" in {

      val trainX: DenseMatrix[Double] = DenseMatrix(
        (-1.0, 0.0),
        (0.0, 1.0),
        (1.0, 1.0)
      )

      val trainY: DenseMatrix[Double] = DenseMatrix(
        (0.0, 1.0, 1.0)
      )

//      val testData: TestData = Seq(
//        DenseVector(-1, 0),
//        DenseVector(0, 1),
//        DenseVector(1, 1)
//      )

      val activation = SigmoidActivation()


//      val nn = MultiLayerPerceptronClassifier


//      val lr = LogisticRegression(100000, 100.0)
//
//      val lrModel = lr.train(trainData)
//
//      val predictedLabels = lrModel.predict(testData)
//      val trainLabels = trainData.map(_.label)
//
//      predictedLabels.zip(trainLabels).foreach { case (predicted, train) =>
//        predicted shouldEqual train +- .0001
//      }
    }
  }

}
