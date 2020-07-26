package bastian.core.loader

import bastian.core.config.Config
import bastian.core.models.UserStory

trait Loader {

  def load(config: Config): Seq[UserStory]
}
