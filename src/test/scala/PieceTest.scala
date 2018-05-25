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

  test("test knight moves") {
    val knight = Piece(Knight, Black)
    assert(knight.canMove(Point(0,0), Point(1,2)))
    assert(!knight.canMove(Point(0,0), Point(2,0)))
  }

  test("test bishop moves") {
    val bishop = Piece(Bishop, Black)
    assert(bishop.canMove(Point(3,3), Point(1,1)))
    assert(!bishop.canMove(Point(3,3), Point(3,1)))
  }

  test("test rook moves") {
    val rook = Piece(Rook, Black)
    assert(rook.canMove(Point(3,3), Point(5,3)))
    assert(!rook.canMove(Point(3,3), Point(4,4)))
  }

  test("test queen moves") {
    val queen = Piece(Queen, Black)
    assert(queen.canMove(Point(3,3), Point(5,5)))
    assert(queen.canMove(Point(3,3), Point(3,5)))
    assert(!queen.canMove(Point(3,3), Point(4,5)))
  }

  test("test king moves") {
    val king = Piece(King, Black)
    assert(king.canMove(Point(3,3), Point(3,4)))
    assert(king.canMove(Point(3,3), Point(4,4)))
    assert(!king.canMove(Point(3,3), Point(3,5)))
  }

  test("test white pawn attack") {
    val pawn = Piece(Pawn, White)
    assert(pawn.canAttack(Point(1,1), Point(2,2)))
    assert(pawn.canAttack(Point(1,1), Point(0,2)))
    assert(!pawn.canAttack(Point(1,1), Point(2,0)))
  }

  test("test black pawn attack") {
    val pawn = Piece(Pawn, Black)
    assert(pawn.canAttack(Point(7,7), Point(6,6)))
    assert(pawn.canAttack(Point(7,7), Point(8,6)))
    assert(!pawn.canAttack(Point(7,7), Point(8,8)))
  }

  test("test other pieces attack") {
    val piece = Piece(Knight, Black)
    assert(piece.canAttack(Point(5,5), Point(6,7)))
    assert(!piece.canAttack(Point(5,5), Point(7,5)))
  }
}
