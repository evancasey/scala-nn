package model

import model.Util.Placeholder

trait Cost {

  def calcLoss = ???
}

class L2Cost extends Cost {

  def calcLoss(activation: Activation, numInputs: Int, outputWeights: Placeholder) = ???
}
