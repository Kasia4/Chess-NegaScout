import chess.board.{Point, Up}

class PointTest extends org.scalatest.FunSuite {
  test("when points are added, result is sum of them") {
    val p1 = Point(3,4)
    val p2 = p1 + Point(1,2)
    assert(p2 == Point(4,6))
  }

  test("when points are subtracted, result is difference of them") {
    val p1 = Point(5,3)
    val p2 = p1 - Point (1,2)
    assert(p2 == Point (4,1))
  }

  test("test flipX") {
    val p1 = Point(5,3)
    assert(p1.flipX() == Point(-5,3))
  }

  test("test flipY") {
    val p1 = Point(5,3)
    assert(p1.flipY() == Point(5,-3))
  }

  test("when points are on a same line horizontally, onSameLine returns true") {
    val p1 = Point(1,1)
    val p2 = Point(5,1)
    assert(p1 onSameLine p2)
  }

  test("when points are on a same line vertically, onSameLine returns true") {
    val p1 = Point(1,1)
    val p2 = Point(1,7)
    assert(p1 onSameLine p2)
  }

  test("When points aren't on a same line, onSameLine returns false") {
    val p1 = Point(1, 1)
    val p2 = Point(3, 5)
    assert(!(p1 onSameLine p2))
  }

  test("When points are on a same right diagonal, onSameDiagonal returns true") {
    val p1 = Point(3,1)
    val p2 = Point(5,3)
    assert(p1 onSameDiagonal p2)
  }

  test("When points are on a same left diagonal, onSameDiagonal returns true") {
    val p1 = Point(1,3)
    val p2 = Point(2,2)
    assert(p1 onSameDiagonal p2)
  }

  test("When points aren't on a same diagonal, onSameDiagonal returns false") {
    val p1 = Point(1,1)
    val p2 = Point(2,3)
    assert(!p1.onSameDiagonal(p2))
  }

  test("distX returns distance between two points on X axis") {
    val p1 = Point(1,1)
    val p2 = Point(8,1)
    assert(p1.distX(p2) == 7)
    assert(p2.distX(p1) == 7)
  }

  test("distY returns distance between two points on Y axis") {
    val p1 = Point(3,1)
    val p2 = Point(3,5)
    assert(p1.distY(p2) == 4)
    assert(p2.distY(p1) == 4)
  }

  test("adjoin test") {
    val p1 = Point(1,1)
    assert(p1.adjoin(Point(1,2)))
    assert(p1.adjoin(Point(2,1)))
    assert(p1.adjoin(Point(2,2)))
    assert(!p1.adjoin(Point(3,4)))
  }

  test("isLNeighbor") {
    val p1 = Point(5,5)
    assert(p1.isLNeighbor(Point(7,4)))
    assert(p1.isLNeighbor(Point(7,6)))
    assert(p1.isLNeighbor(Point(3,4)))
    assert(p1.isLNeighbor(Point(6,7)))
    assert(p1.isLNeighbor(Point(4,3)))
    assert(!p1.isLNeighbor(Point(2,2)))
  }

  test("atLeft returns true only when x coordinate of p1 is lesser then p2") {
    assert(Point(0,0) atLeft Point(1,0))
    assert(!(Point(0,0) atLeft Point(-1,0)))
  }

  test("atRight returns true only when x coordinate of p1 is greater then p2") {
    assert(Point(0, 0) atRight Point(-1, 0))
    assert(!(Point(4, 0) atRight Point(5, 3)))
  }

  test("above returns true only when y coordinate of p1 is greater then p2") {
    assert(Point(3,4) above Point(9,2))
    assert(!(Point(3,4) above Point(1,7)))
  }

  test("below returns true only when y coordinate of p1 is lesser then p2") {
    assert(Point(3,4) below Point(1,7))
    assert(!(Point(3,4) below Point(9,2)))
  }

  test("test point relational operators") {
    assert(Point(3,4) > Point(1,2))
    assert(!(Point(3,4) > Point(3,2)))
    assert(Point(3,4) >= Point(3,4))
    assert(!(Point(3,4) >= Point(4,4)))
    assert(Point(3,4) < Point(4,5))
    assert(!(Point(3,3) < Point(3,4)))
    assert(Point(3,4) <= Point(3,4))
    assert(!(Point(3,4) <= Point(2,4)))
  }

  test("path generates sequence of consecutive points") {
    val seq = Point(0,0).path(Up.vec, length = 3)
    assert(seq.length == 3)
    assert(seq(0) == Point(0,1))
    assert(seq(1) == Point(0,2))
    assert(seq(2) == Point(0,3))


  }
}
