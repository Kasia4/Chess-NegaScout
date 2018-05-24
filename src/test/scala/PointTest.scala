
import chess.board.Point

class PointTest extends org.scalatest.FunSuite {
  test("when points are added, result is sum of them") {
    val p1 = new Point(3,4)
    val p2 = p1 + Point(1,2)
    assert(p2 == Point(4,6))
  }

  test("when points are subtracted, result is difference of them") {
    val p1 = new Point(5,3)
    val p2 = p1 - Point (1,2)
    assert(p2 == Point (4,1))
  }
}
