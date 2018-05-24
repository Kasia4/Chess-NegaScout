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

  def onSameLine(other: Point): Boolean = this.x == other.x || this.y == other.y
  def onSameDiagonal(other: Point): Boolean = this.y - this.x == other.y - other.x ||  this.y + this.x == other.y + other.x
}

