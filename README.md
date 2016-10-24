scala-nn
--------

A functional neural network in Scala. Uses [breeze](https://github.com/scalanlp/breeze) for fast numerical processing.

## Design

scala-nn provides three core capabilities:

1. A DSL-like interface for defining networks of arbitrary size and type
2. An optimization library and cost functions for backprogation-style updates
3. Benchmarks (coming soon)

## Usage

```scala
import breeze.linalg._
import model._

val w1: DenseMatrix[Double] = DenseMatrix.rand(inputLayerSize, hiddenLayerSize)
val w2: DenseMatrix[Double] = DenseMatrix.rand(hiddenLayerSize, outputLayerSize)

val b1: DenseMatrix[Double] = DenseMatrix.rand(1, inputLayerSize)
val b2: DenseMatrix[Double] = DenseMatrix.rand(1, hiddenLayerSize)

val layers = Seq(
  Layer(weightMatrix = w1, biasMatrix = b1, SigmoidActivation),
  Layer(weightMatrix = w2, biasMatrix = b2, SigmoidActivation) // output layer
)

val optimizer = GradientDescentOptimizer(L2Cost, 1.0)
val trainer = Trainer(optimizer)
val res = trainer.res(layers, trainingData._1)
```
