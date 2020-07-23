package bastian.core.loader

import java.nio.file.{Files, Path}

import scala.jdk.CollectionConverters._
import scala.util.matching.Regex

import bastian.core.PartialTapSyntax
import bastian.core.config.Config
import bastian.core.models._
import wvlet.log.LogSupport

object MarkdownLoader extends Loader with LogSupport with PartialTapSyntax {

  override def load(config: Config): Seq[UserStory] = {
    if (!Files.exists(config.backlogItemLocation))
      throw new Exception(s"backlog item directory not found. path=${config.backlogItemLocation}")
    val files = Files.list(config.backlogItemLocation).iterator().asScala
    val parsed = files.map(parseMarkdownFile(_, config)).toSeq
    parsed.collect { case Left(file) => file }.foreach(file => logger.warn(s"Failed to parse: ${file.toAbsolutePath}"))
    parsed.collect { case Right(item) => item }
  }

  def parseMarkdownFile(path: Path, config: Config): Either[Path, UserStory] = {
    val lines = Files.readAllLines(path).asScala
    val specs = lines.collect { case DashPattern(spec) => spec }.map(Specification)
    val userStory = for {
      category <- lines.collectFirst { case CategoryHashPattern(value) => CategoryName(value) }
        .partialTap { case None => logger.warn(s"Not match. file=$path, pattern=${CategoryHashPattern.regex}") }
      userStoryLine <- lines.collectFirst { case UserStoryHashPattern(value) => value }
        .partialTap { case None => logger.warn(s"Not match. file=$path, pattern=${UserStoryHashPattern.regex}") }
      userType <- config.userTypeRegex.findFirstMatchIn(userStoryLine).map(m => UserType(m.group(1)))
        .partialTap { case None => logger.warn(s"Not match. file=$path, pattern=${config.userTypeRegex.regex}") }
      activity <- config.activityRegex.findFirstMatchIn(userStoryLine).map(m => ActivityName(m.group(1)))
        .partialTap { case None => logger.warn(s"Not match. file=$path, pattern=${config.activityRegex.regex}") }
      reason = config.motiveRegex.findFirstMatchIn(userStoryLine).map(m => Motive(m.group(1)))
        .partialTap { case None => logger.info(s"Not match. file=$path, pattern=${config.motiveRegex.regex}") }
    } yield UserStory(category, userType, activity, specs, reason.toSeq)
    userStory.toRight(path)
  }

  private val CategoryHashPattern: Regex = """^#\s+(\S+).*$""".r
  private val UserStoryHashPattern: Regex = """^##\s+(\S+).*$""".r
  private val DashPattern: Regex = """^-\s+(\S+).*$""".r
}
