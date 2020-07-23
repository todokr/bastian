package bastian.core.models

import bastian.core.Hasher

case class UsecaseSpecification(value: String) extends ValueClass {
  def id: String = Hasher.sha1(value)
}
