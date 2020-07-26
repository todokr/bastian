package bastian.core

import bastian.core.config.Config
import bastian.core.loader.{Loader, MarkdownLoader}
import bastian.core.merger.Merger
import bastian.core.writer.{DiagramWriter, PlantUMLDiagramWriter}
import wvlet.log.LogFormatter.SimpleLogFormatter
import wvlet.log.{LogSupport, Logger}

object Bastian extends LogSupport {
  Logger.setDefaultFormatter(SimpleLogFormatter)

  val loader: Loader = MarkdownLoader
  val writer: DiagramWriter = PlantUMLDiagramWriter

  def generateDiagram(config: Config): Unit = {
    logger.info(s"config: $config")
    val items = loader.load(config)
    val usecaseContexts = Merger.merge(items)
    val result = writer.write(usecaseContexts, config)
    logger.info(s"generated: ${result.outPath.toAbsolutePath}")
  }
}
