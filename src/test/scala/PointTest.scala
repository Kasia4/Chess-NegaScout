import chess.board.Point

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
}
