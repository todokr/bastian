package bastian

import java.io.File

import bastian.core.Bastian
import bastian.core.config.Config
import scopt.{OParser, OParserBuilder}
import wvlet.log.LogSupport

object Main extends LogSupport {

  val builder: OParserBuilder[Config] = OParser.builder
  val parser: OParser[Unit, Config] = {
    import builder._
    OParser.sequence(
      programName("bastian"),
      opt[File]('b', "backlogItemLocation")
        .valueName("<file>")
        .action { (file, c) =>
          c.copy(backlogItemLocation = file.toPath)
        },
      opt[File]('o', "outputLocation")
        .valueName("<file>")
        .action { (file, c) =>
          c.copy(outputLocation = file.toPath)
        },
      opt[String]('u', "userTypeRegex")
        .valueName("<regex-string>")
        .action { (str, c) =>
          c.copy(userTypeRegex = str.r)
        },
      opt[String]('a', "activityRegex")
        .valueName("<regex-string>")
        .action { (str, c) =>
          c.copy(activityRegex = str.r)
        },
      opt[String]('m', "motiveRegex")
        .valueName("<regex-string>")
        .action { (str, c) =>
          c.copy(motiveRegex = str.r)
        }
    )
  }

  def main(args: Array[String]): Unit =
    OParser.parse(parser, args, Config.default) match {
      case Some(config) => Bastian.generateDiagram(config)
      case _            =>
    }
}
