package bastian.core.writer

import bastian.core.config.Config
import bastian.core.models.UsecaseContext

trait DiagramWriter {

  def write(contexts: Seq[UsecaseContext], config: Config): WriteResult
}
