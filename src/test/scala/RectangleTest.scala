import chess.board._

class RectangleTest extends org.scalatest.FunSuite{
  test("Test contains funciton") {
    val rect = Rectangle(Point(8,8))
    assert(rect.contains(Point(4,5)))
    assert(!rect.contains(Point(8,1)))
  }
}