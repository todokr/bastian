package bastian.core.models

import bastian.core.Hasher

case class Usecase(
  name: UsecaseName,
  specifications: Seq[UsecaseSpecification],
  reasons: Seq[UsecaseReason]
) {
  def id: String = Hasher.sha1(name.value)
}
