package bastian.core.models

trait ValueClass {
  def value: String

  override def toString: String = value
}
