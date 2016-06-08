package model

// TODO: Use algebird monoid?
trait Monoid[T] {
  def zero: T
  def isNonZero(v: T): Boolean = (v != zero)
  def plus(l: T, r: T): T
}
