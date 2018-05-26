package chess.board
import scala.collection.immutable

case class Point(x: Int, y: Int) {

  def +(other: Point): Point = {
    Point(this.x + other.x, this.y + other.y)
  }

  def -(other: Point): Point = {
    Point(this.x - other.x, this.y - other.y)
  }

  def *(s: Int): Point = {
    Point(this.x * s, this.y * s)
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

  def onBothCoord(other: Point, cond :(Int, Int) => Boolean): Boolean = cond(this.x, other.x) && cond(this.y, other.y)

  def >(other: Point): Boolean = onBothCoord(other, _ > _)
  def >=(other: Point): Boolean = onBothCoord(other, _ >= _)
  def <(other: Point): Boolean = onBothCoord(other, _ < _)
  def <=(other: Point): Boolean = onBothCoord(other, _ <= _)

  def path(start: Point, shift: Point, length: Int): IndexedSeq[Point] = for(i <- 1 to length) yield start + shift * i
}