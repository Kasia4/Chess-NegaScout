import chess.board._

class BoardTest extends org.scalatest.FunSuite{
  test("When piece is added to board, pieces map cotains it") {
    val board = Board()
    val board2 = board.add(Point(5,5), Piece(Pawn, White))
    assert(board2.pieces(Point(5,5)) == Piece(Pawn, White))
  }

  test("When piece is removed from board, pieces map doesn't contain it") {
    val board = Board().add(Point(5,5), Piece(Pawn, Black))
    val board2 = board.remove(Point(5,5))
    assert(board2.pieces.get(Point(5,5)).isEmpty)
  }

  test("when piece is added to board, getAt returns that piece") {
    val pos = Point(3,3)
    val board = Board().add(pos, Piece(Pawn, Black))
    assert(board.getAt(pos).get == Piece(Pawn, Black))
  }

  test("when field is empty, getAt returns None") {
    val pos = Point(3,3)
    val board = Board()
    assert(board.getAt(pos).isEmpty)
  }

  test("ofColor returns all pieces of given color") {
    val board = Board()
      .add(Point(0,1), Piece(Pawn, White))
      .add(Point(0,0), Piece(Rook, White))
      .add(Point(2,7), Piece(Pawn, Black))
    val filtered = board.ofColor(White)
    assert(filtered.size == 2)
    assert(filtered.contains(Point(0,1)))
    assert(filtered.contains(Point(0,0)))
  }

  test("ofType returns all pieces of given type") {
    val board = Board()
      .add(Point(0,1), Piece(Pawn, White))
      .add(Point(0,0), Piece(Rook, White))
      .add(Point(2,7), Piece(Pawn, Black))
    val filtered = board.ofType(Pawn)
    assert(filtered.size == 2)
    assert(filtered.contains(Point(0,1)))
    assert(filtered.contains(Point(2,7)))
  }
}
