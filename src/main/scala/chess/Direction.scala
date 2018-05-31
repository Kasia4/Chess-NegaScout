package chess

sealed trait Direction {
  val vec: Point
}

sealed trait LineDirection extends Direction
sealed trait DiagDirection extends Direction

object Up extends LineDirection         { val vec = Point(0, 1) }

object Down extends LineDirection       { val vec = Point(0, -1) }

object Right extends LineDirection      { val vec = Point(1, 0) }

object Left extends LineDirection       { val vec = Point(-1, 0) }

object UpRight extends DiagDirection    { val vec = Point(1, 1) }

object UpLeft extends DiagDirection     { val vec = Point(-1, 1) }

object DownRight extends DiagDirection  { val vec = Point(1, -1) }

object DownLeft extends DiagDirection   { val vec = Point(-1, -1) }

object Direction {
  lazy val horizontalDir            = List(Right, Left)
  lazy val verticalDir              = List(Up, Down)
  lazy val lineDir: List[Direction] = horizontalDir ::: verticalDir
  lazy val diagonalDir              = List(UpRight, UpLeft, DownRight, DownLeft)
  lazy val allDir: List[Direction]  = diagonalDir ::: lineDir
}
