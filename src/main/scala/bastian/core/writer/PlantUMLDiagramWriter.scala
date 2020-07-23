package bastian.core.writer

import java.io.FileOutputStream

import bastian.core.config.Config
import bastian.core.models.UsecaseContext
import net.sourceforge.plantuml.{FileFormat, FileFormatOption, SourceStringReader}
import wvlet.log.LogSupport

object PlantUMLDiagramWriter extends DiagramWriter with LogSupport {

  override def write(contexts: Seq[UsecaseContext], config: Config): WriteResult = {
    val contextNotations = contexts.map { ctx =>
      val note = s"""note right of ${ctx.usecase.id} : ${ctx.usecase.specifications.mkString("""\n""")}"""
      val reason =
        if (ctx.usecase.reasons.nonEmpty) s": ${ctx.usecase.reasons.mkString("""\n""")}"
        else ""

      s"""package ${ctx.name} {
         |  usecase ${ctx.usecase.name} as ${ctx.usecase.id}
         |  $note
         |}
         |actor "${ctx.actorName}" as ${ctx.actorName.id}
         |${ctx.actorName.id} --> ${ctx.usecase.id} $reason
         |""".stripMargin
    }.mkString("\n")
    val diagram = s"""@startuml
       |left to right direction
       |skinparam monochrome true
       |skinparam backgroundColor #FFFFFF
       |$contextNotations
       |@enduml""".stripMargin

    val reader = new SourceStringReader(diagram)
    val option = new FileFormatOption(FileFormat.SVG)
    val outPath = config.outputLocation.resolve("usecases.svg")
    val out = new FileOutputStream(outPath.toFile)
    reader.generateImage(out, option)
    WriteResult(outPath)
  }
}
