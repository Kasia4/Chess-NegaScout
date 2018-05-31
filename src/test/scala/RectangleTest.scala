import chess.{Down, DownLeft, DownRight, Point, Rectangle, Up, UpLeft, UpRight}

class RectangleTest extends org.scalatest.FunSuite{
  test("Test contains function") {
    val rect = Rectangle(Point(8,8))
    assert(rect.contains(Point(4,5)))
    assert(!rect.contains(Point(8,1)))
  }

  test("Test distToBorder function") {
    val rect = Rectangle(Point(8,8))
    assert(rect.distToBorder(Point(2,3), Up) == 4)
    assert(rect.distToBorder(Point(2,3), Down) == 3)
    assert(rect.distToBorder(Point(2,3), chess.Left) == 2)
    assert(rect.distToBorder(Point(2,3), chess.Right) == 5)
    assert(rect.distToBorder(Point(2,3), UpRight) == 4)
    assert(rect.distToBorder(Point(2,3), UpLeft) == 2)
    assert(rect.distToBorder(Point(2,3), DownRight) == 3)
    assert(rect.distToBorder(Point(2,3), DownLeft) == 2)
  }

  test("Test pathToBorder function") {
    val rect = Rectangle(Point(8,8))
    assert(rect.pathToBorder(Point(2, 3), Up) == Point(2,3).path(Up.vec, length = 4))
  }

}