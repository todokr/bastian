package bastian.core.models

case class UsecaseContext(
  name: UsecaseContextName,
  actorName: ActorName,
  usecase: Usecase
)
