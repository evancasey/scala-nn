//package builder
//
//import breeze.linalg._
//import model._
//import org.scalatest._
//
//class MultiLayerPerceptronSpec extends WordSpec {
//
//  def getRandomWeights(
//    inputLayerSize: Int,
//    hiddenLayerSize: Int,
//    outputLayerSize: Int): (DenseMatrix[Double], DenseMatrix[Double]) = {
//    // num features in train = trainX.cols
//    // TODO: scale between -1 and 1
//    val w1: DenseMatrix[Double] = DenseMatrix.rand(inputLayerSize, hiddenLayerSize)
//    val w2: DenseMatrix[Double] = DenseMatrix.rand(hiddenLayerSize, outputLayerSize)
//
//    (w1, w2)
//  }
//
//  def getDummyWeights: (DenseMatrix[Double], DenseMatrix[Double]) = {
//    val w1: DenseMatrix[Double] = DenseMatrix(
//      (1.76405235,  0.40015721,  0.97873798),
//      (2.2408932,   1.86755799, -0.97727788)
//    )
//    val w2: DenseMatrix[Double] = DenseMatrix(
//      0.95008842,
//      -0.15135721,
//      -0.10321885
//    )
//
//    (w1, w2)
//  }
//
//  def getDummyBiases: (DenseMatrix[Double], DenseMatrix[Double]) = ???
//
//  def getNormalizedTrainData: (DenseMatrix[Double], DenseMatrix[Double]) = {
//    // TODO: make this work with normalize
//    val trainXNorm: DenseMatrix[Double] = DenseMatrix(
//      (3.0 / 10.0, 5.0 / 5.0),
//      (5.0 / 10.0, 1.0 / 5.0),
//      (10.0 / 10.0, 2.0 / 5.0)
//    )
//
//    val trainY: DenseMatrix[Double] = DenseMatrix(
//      (75.0, 82.0, 93.0)
//    )
//
//    val trainYNorm: DenseMatrix[Double] = trainY / 100.0
//
//    (trainXNorm, trainYNorm)
//  }
//
//  "ForwardProp" should {
//    "propagate weights correctly" in {
//      val (trainX, trainY) = getNormalizedTrainData
//      val (w1, w2) = getDummyWeights
//      val (b1, b2) = getDummyBiases
//
//      println(trainX)
//      println(trainY)
//
//      val p1 = Placeholder(weightMatrix = w1, biasMatrix = b1, SigmoidActivation)
//      val p2 = Placeholder(weightMatrix = w2, biasMatrix = b2, SigmoidActivation) // output layer
//
//      // activation per layer!
//      val output = MultiLayerPerceptron.builder
//        .withLayer(p1)
//        .withLayer(p2)
//        .withOptimizer(GradientDescentOptimizer(L2Cost, 1.0))
//        .train(trainX, trainY, 100)
//
//      println(output)
//
//
//    }
//  }
//
//}
