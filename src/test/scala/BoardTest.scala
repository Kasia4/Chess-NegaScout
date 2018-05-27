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

  test("test isEmptyAt") {
    val board = Board().add(Point(5,2), Piece(Bishop, White)).get
    assert(board.isEmptyAt(Point(5,3)))
    assert(!board.isEmptyAt(Point(9,10)))
    assert(!board.isEmptyAt(Point(5,2)))
  }

  test("test scanDirs") {
    val board = Board()
      .add(Point(3,3), Piece(Queen, Black)).get
    val dirs = List(UpRight, Down)
    assert(board.scanDirs(dirs, Point(1,1)).toSet == Set(Point(1,0), Point(2,2)))
  }

  test("unmoved white pawn movement on empty board can move two fields up") {
    val board = Board()
      .add(Point(4,1), Piece(Pawn, White, moved = false)).get

    val unmoved_moves = board.possibleMoves(Point(4,1))

    assert(unmoved_moves.toSet == Set(Point(4,2), Point(4,3)))
  }

  test("moved white pawn movement on empty board one field up") {
    val board = Board()
      .add(Point(7,1), Piece(Pawn, White)).get

    val moved_moves = board.possibleMoves(Point(7,1))

    assert(moved_moves.toSet == Set(Point(7,2)))
  }

  test("unmoved black pawn movement on empty board can move two fields down") {
    val board = Board()
      .add(Point(4,7), Piece(Pawn, Black, moved = false)).get

    val unmoved_moves = board.possibleMoves(Point(4,7))

    assert(unmoved_moves.toSet == Set(Point(4,6), Point(4,5)))
  }

  test("moved black pawn movement on empty board can move one field down") {
    val board = Board()
      .add(Point(4,7), Piece(Pawn, Black)).get

    val moves = board.possibleMoves(Point(4,7))

    assert(moves.toSet == Set(Point(4,6)))
  }

  test("unmoved pawn can move only one field when it's blocked") {
    val board = Board()
      .add(Point(1,1), Piece(Pawn, White, moved = false)).get
      .add(Point(1,3), Piece(Rook, Black)).get

    val moves = board.possibleMoves(Point(1,1))

    assert(moves.toSet == Set(Point(1,2)))
  }

  test("pawn can't move when it's blocked directly") {
    val board = Board()
      .add(Point(1,1), Piece(Pawn, White, moved = false)).get
      .add(Point(1,2), Piece(Rook, Black)).get

    val moves = board.possibleMoves(Point(1,1))

    assert(moves.toSet == Set.empty[Point])
  }

  test("knight can move according to L-shape pattern on unoccupied fields on board") {
    val board = Board()
      .add(Point(1,0), Piece(Knight, White)).get
      .add(Point(2,2), Piece(Queen, White)).get

    val moves = board.possibleMoves(Point(1,0))
    assert(moves.toSet == Set(Point(0,2), Point(3,1)))
  }

  test("bishop can move only on diagonals") {
    val board = Board()
      .add(Point(1,1), Piece(Bishop, White)).get
      .add(Point(3,3), Piece(Pawn, Black)).get

    val moves = board.possibleMoves(Point(1,1))
    assert(moves.contains(Point(0,2)))
    assert(moves.contains(Point(2,2)))
    assert(!moves.contains(Point(3,3)))
    assert(!moves.contains(Point(3,1)))
  }

  test("rook can move only on lines") {
    val board = Board()
      .add(Point(1,1), Piece(Rook, White)).get
      .add(Point(3,1), Piece(Pawn, Black)).get

    val moves = board.possibleMoves(Point(1,1))
    assert(moves.contains(Point(0,1)))
    assert(moves.contains(Point(1,7)))
    assert(!moves.contains(Point(7,1)))
    assert(!moves.contains(Point(0,0)))
  }

  test("queen can move only on lines and diagonals") {
    val board = Board()
      .add(Point(1,1), Piece(Queen, White)).get
      .add(Point(3,1), Piece(Pawn, Black)).get

    val moves = board.possibleMoves(Point(1,1))
    assert(moves.contains(Point(3,3)))
    assert(moves.contains(Point(1,7)))
    assert(!moves.contains(Point(7,1)))
    assert(!moves.contains(Point(3,4)))
  }

  test("king can move only on adjoin fields") {
    val board = Board()
      .add(Point(1,1), Piece(King, White)).get
      .add(Point(2,1), Piece(Pawn, Black)).get

    val moves = board.possibleMoves(Point(1,1))

    assert(moves.contains(Point(0,1)))
    assert(moves.contains(Point(1,2)))
    assert(!moves.contains(Point(2,1)))
    assert(!moves.contains(Point(1,3)))
  }

  test("scan opponent returns first opponent piece encountered in every given direction") {
    val board = Board()
      .add(Point(1,1), Piece(Queen, White)).get
      .add(Point(3,1), Piece(Rook, Black)).get

    val fields = board.scanOpponent(List(Down), Point(1,3), Black)
    assert(fields.contains(Point(1,1)))
    assert(!fields.contains(Point(3,1)))
  }

  test("if there is no opponent in given direction, scanOpponent returns empty list") {
    val board = Board()
    assert(board.scanOpponent(List(Right), Point(3,3), Black).isEmpty)
  }

  test("if opponent piece is covered by player's piece, scanOpponent returns empty list") {
    val board = Board()
      .add(Point(1,1), Piece(Pawn, Black)).get
      .add(Point(2,1), Piece(Rook, White)).get
    assert(board.scanOpponent(List(Right), Point(0,1), Black).isEmpty)
  }


}
