package chess

import scala.collection

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

  /**
    * Calculate distance between points on X axis
    * @param other other point
    * @return distance value
    */
  def distX(other: Point): Int = Math.abs(this.x - other.x)

  /**
    * Calculate distance between points on Y axis
    * @param other other point
    * @return distance value
    */
  def distY(other: Point): Int = Math.abs(this.y - other.y)

  /**
    * Checks if two points lies on same line (vertical or horizontal)
    * @param other other point
    * @return
    */
  def onSameLine(other: Point): Boolean = this.x == other.x || this.y == other.y

  /**
    * Checks if two points lies on same diagonal
    * @param other other point
    * @return
    */
  def onSameDiagonal(other: Point): Boolean = this.y - this.x == other.y - other.x ||  this.y + this.x == other.y + other.x

  /**
    * Check if two points adjoins
    * @param other other point
    * @return
    */
  def adjoin(other: Point): Boolean = this.distX(other) <= 1 && this.distY(other) <= 1

  /**
    * Check if two points are L-Neighbors (according to Knight's moving pattern)
    * @param other other point
    * @return
    */
  def isLNeighbor(other: Point): Boolean =
    (this.distX(other) == 1 && this.distY(other) == 2) ||
    (this.distX(other) == 2 && this.distY(other) == 1)

  def above(other: Point): Boolean = this.y > other.y
  def below(other: Point): Boolean = this.y < other.y
  def atLeft(other: Point): Boolean = this.x < other.x
  def atRight(other: Point): Boolean = this.x > other.x

  /**
    * Checks if both coordinates fill given condition
    * @param other other point
    * @param cond condition
    * @return
    */
  def onBothCoord(other: Point, cond :(Int, Int) => Boolean): Boolean = cond(this.x, other.x) && cond(this.y, other.y)

  def >(other: Point): Boolean = onBothCoord(other, _ > _)
  def >=(other: Point): Boolean = onBothCoord(other, _ >= _)
  def <(other: Point): Boolean = onBothCoord(other, _ < _)
  def <=(other: Point): Boolean = onBothCoord(other, _ <= _)

  /**
    * Generates sequence of points where each point is translated from previous point by shift
    * @param shift shift between consecutive points
    * @param length sequence length
    * @return generated sequence
    */
  def path(shift: Point, length: Int): IndexedSeq[Point] = for(i <- 1 to length) yield this + shift * i

  /**
    * Generates list of given point's l-neighbors (according to knight's move pattern)
    * @return
    */
  def lNeighbors(): List[Point] =
    for(x <- List(-2,-1,1,2);
        y <- List(-2,-1,1,2) if Math.abs(x) + Math.abs(y) == 3)
      yield this + Point(x, y)

  /**
    * Generates list of given point's neighbors
    * @return
    */
  def neighbors(): List[Point] =
    for(x <- List(-1,0,1);
        y <- List(-1,0,1) if x != 0 || y != 0)
      yield this + Point(x, y)

  /**
    * Generates sequence of points lying between two points (on line or diagonal)
    * @param other other point
    * @return generated sequence
    */
  def pointsBetween(other: Point): IndexedSeq[Point] = {
    if (this == other) IndexedSeq.empty[Point]
    else if (!(onSameDiagonal(other) || onSameLine(other))) IndexedSeq.empty[Point]
    else {
      val x =
        if (this.x == other.x) 0
        else if (this atLeft other) 1
        else -1
      val y =
        if (this.y == other.y) 0
        else if (this below other) 1
        else -1
      path(Point(x, y), Math.max(distX(other), distY(other)) -1 )
    }
  }

}