package bastian.core.config

import java.nio.file.{Path, Paths}

import scala.util.matching.Regex

case class Config(
  backlogItemLocation: Path,
  outputLocation: Path,
  userTypeRegex: Regex,
  activityRegex: Regex,
  motiveRegex: Regex
)

object Config {

  val default: Config = Config(
    backlogItemLocation = Paths.get("backlogs").toAbsolutePath,
    outputLocation = Paths.get(""),
    userTypeRegex = """^.*\*(.*)\*.*として""".r,
    activityRegex = """として.*\*(.*)\*.*したい""".r,
    motiveRegex = """なぜなら.*\*(.*)\*.*からだ""".r
  )
}
