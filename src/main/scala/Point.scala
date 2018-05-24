package chess.board

case class Point(x: Int, y: Int) {
  def +(other: Point): Point = {
    Point(this.x + other.x, this.y + other.y)
  }

  def -(other: Point): Point = {
    Point(this.x - other.x, this.y - other.y)
  }

  def flipX(): Point = {
    Point(-this.x, this.y)
  }

  def flipY(): Point = {
    Point(this.x, -this.y)
  }
}
