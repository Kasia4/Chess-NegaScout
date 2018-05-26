import chess.board._

class BoardTest extends org.scalatest.FunSuite{
  test("When piece is added to board, pieces map cotains it") {
    val board = Board()
    val board2 = board.add(Point(5,5), Piece(Pawn, White)).get
    assert(board2.pieces(Point(5,5)) == Piece(Pawn, White))
  }

  test("When piece is removed from board, pieces map doesn't contain it") {
    val board = Board().add(Point(5,5), Piece(Pawn, Black)).get
    val board2 = board.remove(Point(5,5))
    assert(board2.pieces.get(Point(5,5)).isEmpty)
  }

  test("when piece is added to board, getAt returns that piece") {
    val pos = Point(3,3)
    val board = Board().add(pos, Piece(Pawn, Black)).get
    assert(board.getAt(pos).get == Piece(Pawn, Black))
  }

  test("when field is empty, getAt returns None") {
    val pos = Point(3,3)
    val board = Board()
    assert(board.getAt(pos).isEmpty)
  }

  test("ofColor returns all pieces of given color") {
    val board = Board()
      .add(Point(0,1), Piece(Pawn, White)).get
      .add(Point(0,0), Piece(Rook, White)).get
      .add(Point(2,7), Piece(Pawn, Black)).get
    val filtered = board.ofColor(White)
    assert(filtered.size == 2)
    assert(filtered.contains(Point(0,1)))
    assert(filtered.contains(Point(0,0)))
  }

  test("ofType returns all pieces of given type") {
    val board = Board()
      .add(Point(0,1), Piece(Pawn, White)).get
      .add(Point(0,0), Piece(Rook, White)).get
      .add(Point(2,7), Piece(Pawn, Black)).get
    val filtered = board.ofType(Pawn)
    assert(filtered.size == 2)
    assert(filtered.contains(Point(0,1)))
    assert(filtered.contains(Point(2,7)))
  }

  test("isEmptyAt checks if given field is empty") {
    assert(Board().isEmptyAt(Point(1,5)))
    assert(!Board().add(Point(1,5), Piece(Pawn, White)).get.isEmptyAt(Point(1,5)))
  }

}
