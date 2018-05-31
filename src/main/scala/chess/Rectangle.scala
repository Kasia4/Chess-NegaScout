package chess

case class Rectangle(size: Point) {
  def contains(point: Point): Boolean = point >= Point(0,0) && point < size
  def distToBorder(point: Point, dir: Direction): Int =
    if (!contains(point)) 0
    else dir match {
      case Up => size.y - point.y - 1
      case Down => point.y
      case chess.Right => size.x - point.x - 1
      case chess.Left => point.x
      case UpRight => Math.min(distToBorder(point, Up), distToBorder(point, chess.Right))
      case UpLeft => Math.min(distToBorder(point, Up), distToBorder(point, chess.Left))
      case DownRight => Math.min(distToBorder(point, Down), distToBorder(point, chess.Right))
      case DownLeft => Math.min(distToBorder(point, Down), distToBorder(point, chess.Left))
    }

  def pathToBorder(start: Point, dir: Direction): IndexedSeq[Point] = {
    start.path(dir.vec, distToBorder(start, dir))
  }

  def findInDirection(start: Point, dir: Direction, cond: Point => Boolean): Option[Point] = {
    pathToBorder(start, dir).find(cond)
  }
}
