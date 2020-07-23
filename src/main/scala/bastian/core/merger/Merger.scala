package bastian.core.merger

import bastian.core.models._
import wvlet.log.LogSupport

object Merger extends LogSupport {

  def merge(items: Seq[UserStory]): Seq[UsecaseContext] = {
    items.groupBy(_.id).values.map { items =>
      val merged = items.reduceLeft(_.merge(_))
      UsecaseContext(
        name = UsecaseContextName(merged.categoryName.value),
        actorName = ActorName(merged.userType.value),
        usecase = Usecase(
          name = UsecaseName(merged.activityName.value),
          specifications = merged.specifications.map(spec => UsecaseSpecification(spec.value)),
          reasons = merged.motives.map(reason => UsecaseReason(reason.value))
        )
      )
    }
  }.toSeq
}
