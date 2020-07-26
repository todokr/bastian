package bastian.sbt

import java.nio.file.{Path, Paths}

import bastian.core.Bastian
import bastian.core.config.Config
import sbt._
import sbt.plugins.JvmPlugin

trait PluginInterface {

  // tasks
  val bastian = taskKey[Unit]("Generate usecase diagram")

  // settings
  val bastianBacklogItemLocation = settingKey[Path]("Directory contains PBIs")
  val bastianOutputLocation = settingKey[Path]("Location to output usecase diagram")
  val bastianUserTypeRegex = settingKey[String]("")
  val bastianActivityRegex = settingKey[String]("")
  val bastianMotiveRegex = settingKey[String]("")
}

object BastianPlugin extends AutoPlugin {

  override def trigger: PluginTrigger = allRequirements
  override def requires: Plugins = JvmPlugin

  object autoImport extends PluginInterface

  import autoImport._

  override lazy val projectSettings = Seq(
    bastian := {
      val config = Config(
        backlogItemLocation = bastianBacklogItemLocation.value,
        outputLocation = bastianOutputLocation.value,
        userTypeRegex = bastianUserTypeRegex.value.r,
        activityRegex = bastianActivityRegex.value.r,
        motiveRegex = bastianMotiveRegex.value.r
      )
      Bastian.generateDiagram(config)
    },
    bastianBacklogItemLocation := bastianBacklogItemLocation.?.value.getOrElse(Paths.get("backlogs")),
    bastianOutputLocation := bastianOutputLocation.?.value.getOrElse(Paths.get("")),
    bastianUserTypeRegex := bastianUserTypeRegex.?.value.getOrElse("""^.*\*(.*)\*.*として"""),
    bastianActivityRegex := bastianActivityRegex.?.value.getOrElse("""として.*\*(.*)\*.*したい"""),
    bastianMotiveRegex := bastianMotiveRegex.?.value.getOrElse("""なぜなら.*\*(.*)\*.*からだ""")
  )

  override lazy val buildSettings = Seq()

  override lazy val globalSettings = Seq()
}

/** Keys for zugen sbt plugin.
  *
  * import this in *.scala build setting.
  * `import bastian.sbt.BastianKeys._`
  */
object BastianKeys extends PluginInterface
