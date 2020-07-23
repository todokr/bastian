package bastian

import java.nio.file.Paths

import bastian.core.Bastian
import bastian.core.config.Config

object Main {

  def main(args: Array[String]): Unit = {
    val config = Config.default.copy(backlogItemLocation = Paths.get("src/sbt-test/sbt-bastian/simple/backlogs"))
    Bastian.generateDiagram(config)
  }
}
