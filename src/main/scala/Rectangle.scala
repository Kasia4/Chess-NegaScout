package chess.board

case class Rectangle(size: Point) {
  def contains(point: Point): Boolean = point >= Point(0,0) && point < size
}
