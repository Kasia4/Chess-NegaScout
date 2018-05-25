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

  def distX(other: Point): Int = Math.abs(this.x - other.x)
  def distY(other: Point): Int = Math.abs(this.y - other.y)

  def onSameLine(other: Point): Boolean = this.x == other.x || this.y == other.y
  def onSameDiagonal(other: Point): Boolean = this.y - this.x == other.y - other.x ||  this.y + this.x == other.y + other.x
  def adjoin(other: Point): Boolean = this.distX(other) <= 1 && this.distY(other) <= 1
  def isLNeighbor(other: Point): Boolean =
    (this.distX(other) == 1 && this.distY(other) == 2) ||
    (this.distX(other) == 2 && this.distY(other) == 1)

  def above(other: Point): Boolean = this.y > other.y
  def below(other: Point): Boolean = this.y < other.y
  def atLeft(other: Point): Boolean = this.x < other.x
  def atRight(other: Point): Boolean = this.x > other.x
}

object Point {

  val right     = Point(1,0)
  val left      = Point(-1,0)
  val up        = Point(0,1)
  val down      = Point(0,-1)
  val upRight   = Point(1,1)
  val upLeft    = Point(-1,1)
  val downRight = Point(1,-1)
  val downLeft  = Point(-1,-1)

  lazy val horizontalDir   = List(right, left)
  lazy val verticalDir     = List(up, down)
  lazy val lineDir         = List(horizontalDir ::: verticalDir)
  lazy val diagonalDir     = List(upRight, upLeft, downRight, downLeft)

}