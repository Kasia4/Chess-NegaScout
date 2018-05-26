import chess.board._

class BoardTest extends org.scalatest.FunSuite{
  test("When piece is added to board, pieces map cotains it") {
    val board = Board()
    val board2 = board.add(Point(5,5), Piece(Pawn, White))
    assert(board2.pieces(Point(5,5)) == Piece(Pawn, White))
  }

  test("When piece is removed from board, pieces map doesn't contain it") {
    val board = Board()
    val board2 = board.add(Point(5,5), Piece(Pawn, Black))
    val board3 = board.remove(Point(5,5))
    assert(board3.pieces.get(Point(5,5)).isEmpty)
  }
}
