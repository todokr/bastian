package bastian.core

import scala.language.implicitConversions

trait PartialTapSyntax {
  implicit final def partialTapOps[A](a: A): PartialTapOps[A] = new PartialTapOps(a)
}

final class PartialTapOps[A](private val self: A) extends AnyVal {

  def partialTap[U](f: PartialFunction[A, U]): A = {
    f.lift(self)
    self
  }
}
