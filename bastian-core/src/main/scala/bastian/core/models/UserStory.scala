package bastian.core.models

import scala.util.matching.Regex

import bastian.core.Hasher

case class UserStory(
  categoryName: CategoryName,
  userType: UserType,
  activityName: ActivityName,
  specifications: Seq[Specification],
  motives: Seq[Motive]
) {
  import UserStory._

  def id: String = Hasher.sha1(s"${categoryName.value}-${userType.value}-${activityName.value}")

  def merge(other: UserStory): UserStory = {
    val specs = _merge(specifications.map(_.value), other.specifications.map(_.value)).map(Specification)
    val ms = _merge(motives.map(_.value), other.motives.map(_.value)).map(Motive)
    copy(specifications = specs, motives = ms)
  }

  def _merge(xs: Seq[String], ys: Seq[String]): Seq[String] = {
    val (minusItems, items) = (xs ++ ys).partition(_.matches(MinusPattern.regex))
    val minuses = minusItems.collect { case MinusPattern(s) => s }
    items.filterNot(i => minuses.contains(i)).distinct
  }
}

object UserStory {
  val MinusPattern: Regex = """^.*~(.+)~.*$""".r
}
