package chess.board

sealed trait Direction {
  val dir: Point
  def apply(): Point = dir
}

sealed trait LineDirection extends Direction

object Up extends LineDirection         { val dir = Point(0, 1) }

object Down extends LineDirection       { val dir = Point(0, -1) }

object Right extends LineDirection      { val dir = Point(1, 0) }

object Left extends LineDirection       { val dir = Point(-1, 0) }

object UpRight extends Direction    { val dir = Point(1, 1) }

object UpLeft extends Direction     { val dir = Point(-1, 1) }

object DownRight extends Direction  { val dir = Point(1, -1) }

object DownLeft extends Direction   { val dir = Point(-1, -1) }

object Direction {
  lazy val horizontalDir    = List(Right, Left)
  lazy val vertivalDir      = List(Up, Down)
  lazy val lineDir          = List(horizontalDir :: vertivalDir)
  lazy val diagonalDir      = List(UpRight, UpLeft, DownRight, DownLeft)
  lazy val allDir           = List(diagonalDir :: lineDir)
}
