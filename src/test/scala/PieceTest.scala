import chess.board._

class PieceTest extends org.scalatest.FunSuite {
  test("test unmoved white pawn moves") {
    val pawn = Piece(Pawn, White, false)
    assert(pawn.canMove(Point(1,1), Point(1,3)))
    assert(pawn.canMove(Point(1,1), Point(1,2)))
    assert(!pawn.canMove(Point(1,1), Point(1,1)))
    assert(!pawn.canMove(Point(1,1), Point(0,2)))
  }

  test("test white pawn moves") {
    val pawn = Piece(Pawn, White, true)
    assert(!pawn.canMove(Point(1,1), Point(1,3)))
    assert(pawn.canMove(Point(1,1), Point(1,2)))
    assert(!pawn.canMove(Point(1,1), Point(1,1)))
    assert(!pawn.canMove(Point(1,1), Point(0,2)))
  }

  test("test black unmoved pawn moves") {
    val pawn = Piece(Pawn, Black, false)
    assert(pawn.canMove(Point(1,7), Point(1,6)))
    assert(pawn.canMove(Point(1,7), Point(1,5)))
    assert(!pawn.canMove(Point(1,7), Point(2,5)))
    assert(!pawn.canMove(Point(1,7), Point(1,8)))
  }

  test("test black pawn moves") {
    val pawn = Piece(Pawn, Black, true)
    assert(pawn.canMove(Point(1,7), Point(1,6)))
    assert(!pawn.canMove(Point(1,7), Point(1,5)))
    assert(!pawn.canMove(Point(1,7), Point(2,5)))
    assert(!pawn.canMove(Point(1,7), Point(1,8)))
  }
}
