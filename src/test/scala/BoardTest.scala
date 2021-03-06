import chess._

class BoardTest extends org.scalatest.FunSuite{
  test("When piece is added to board, pieces map contains it") {
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

    val unmoved_moves = board.possibleShifts(Point(4,1))

    assert(unmoved_moves.toSet == Set(Point(4,2), Point(4,3)))
  }

  test("moved white pawn movement on empty board one field up") {
    val board = Board()
      .add(Point(7,1), Piece(Pawn, White)).get

    val moved_moves = board.possibleShifts(Point(7,1))

    assert(moved_moves.toSet == Set(Point(7,2)))
  }

  test("unmoved black pawn movement on empty board can move two fields down") {
    val board = Board()
      .add(Point(4,7), Piece(Pawn, Black, moved = false)).get

    val unmoved_moves = board.possibleShifts(Point(4,7))

    assert(unmoved_moves.toSet == Set(Point(4,6), Point(4,5)))
  }

  test("moved black pawn movement on empty board can move one field down") {
    val board = Board()
      .add(Point(4,7), Piece(Pawn, Black)).get

    val moves = board.possibleShifts(Point(4,7))

    assert(moves.toSet == Set(Point(4,6)))
  }

  test("unmoved pawn can move only one field when it's blocked") {
    val board = Board()
      .add(Point(1,1), Piece(Pawn, White, moved = false)).get
      .add(Point(1,3), Piece(Rook, Black)).get

    val moves = board.possibleShifts(Point(1,1))

    assert(moves.toSet == Set(Point(1,2)))
  }

  test("pawn can't move when it's blocked directly") {
    val board = Board()
      .add(Point(1,1), Piece(Pawn, White, moved = false)).get
      .add(Point(1,2), Piece(Rook, Black)).get

    val moves = board.possibleShifts(Point(1,1))

    assert(moves.toSet == Set.empty[Point])
  }

  test("knight can move according to L-shape pattern on unoccupied fields on board") {
    val board = Board()
      .add(Point(1,0), Piece(Knight, White)).get
      .add(Point(2,2), Piece(Queen, White)).get

    val moves = board.possibleShifts(Point(1,0))
    assert(moves.toSet == Set(Point(0,2), Point(3,1)))
  }

  test("bishop can move only on diagonals") {
    val board = Board()
      .add(Point(1,1), Piece(Bishop, White)).get
      .add(Point(3,3), Piece(Pawn, Black)).get

    val moves = board.possibleShifts(Point(1,1))
    assert(moves.contains(Point(0,2)))
    assert(moves.contains(Point(2,2)))
    assert(!moves.contains(Point(3,3)))
    assert(!moves.contains(Point(3,1)))
  }

  test("rook can move only on lines") {
    val board = Board()
      .add(Point(1,1), Piece(Rook, White)).get
      .add(Point(3,1), Piece(Pawn, Black)).get

    val moves = board.possibleShifts(Point(1,1))
    assert(moves.contains(Point(0,1)))
    assert(moves.contains(Point(1,7)))
    assert(!moves.contains(Point(7,1)))
    assert(!moves.contains(Point(0,0)))
  }

  test("queen can move only on lines and diagonals") {
    val board = Board()
      .add(Point(1,1), Piece(Queen, White)).get
      .add(Point(3,1), Piece(Pawn, Black)).get

    val moves = board.possibleShifts(Point(1,1))
    assert(moves.contains(Point(3,3)))
    assert(moves.contains(Point(1,7)))
    assert(!moves.contains(Point(7,1)))
    assert(!moves.contains(Point(3,4)))
  }

  test("king can move only on adjoin fields") {
    val board = Board()
      .add(Point(1,1), Piece(King, White)).get
      .add(Point(2,1), Piece(Pawn, Black)).get

    val moves = board.possibleShifts(Point(1,1))

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
    assert(board.scanOpponent(List(chess.Right), Point(3,3), Black).isEmpty)
  }

  test("if opponent piece is covered by player's piece, scanOpponent returns empty list") {
    val board = Board()
      .add(Point(1,1), Piece(Pawn, Black)).get
      .add(Point(2,1), Piece(Rook, White)).get
    assert(board.scanOpponent(List(chess.Right), Point(0,1), Black).isEmpty)
  }

  test("White pawn can capture opponent's pieces on fields on corners above it") {
    val board = Board()
      .add(Point(1,1), Piece(Pawn, White)).get
      .add(Point(0,2), Piece(Bishop, Black)).get
      .add(Point(2,2), Piece(Knight, White)).get
      .add(Point(0,0), Piece(Rook, Black)).get
    assert(board.possibleCaptures(Point(1,1)).toSet == Set(Point(0,2)))
  }

  test("Black pawn can capture opponent's pieces on fields on corners below it") {
    val board = Board()
      .add(Point(1,7), Piece(Pawn, Black)).get
      .add(Point(0,6), Piece(Bishop, White)).get
      .add(Point(2,6), Piece(Knight, Black)).get
      .add(Point(0,7), Piece(Rook, White)).get
    assert(board.possibleCaptures(Point(1,7)).toSet == Set(Point(0,6)))
  }

  test("Knight can capture opponent's pieces only on L-shape pattern") {
    val board = Board()
      .add(Point(4,4), Piece(Knight, Black)).get
      .add(Point(2,5), Piece(Knight, White)).get
      .add(Point(4,5), Piece(Rook, Black)).get
    assert(board.possibleCaptures(Point(4,4)).toSet == Set(Point(2,5)))
  }

  test("King can capture opponent's pieces only on adjoin fileds") {
    val board = Board()
      .add(Point(4,4), Piece(King, Black)).get
      .add(Point(3,4), Piece(Knight, White)).get
      .add(Point(6,4), Piece(Rook, White)).get
    assert(board.possibleCaptures(Point(4,4)).toSet == Set(Point(3,4)))
  }

  test("Bishop can capture opponent's pieces only on diagonals") {
    val board = Board()
      .add(Point(4,4), Piece(Bishop, Black)).get
      .add(Point(7,7), Piece(Rook, White)).get
      .add(Point(4,6), Piece(Rook, White)).get
    assert(board.possibleCaptures(Point(4,4)).toSet == Set(Point(7,7)))
  }

  test("Rook can capture opponent's pieces only on lines") {
    val board = Board()
      .add(Point(4,4), Piece(Rook, Black)).get
      .add(Point(7,7), Piece(Bishop, White)).get
      .add(Point(4,6), Piece(Rook, White)).get
    assert(board.possibleCaptures(Point(4,4)).toSet == Set(Point(4,6)))
  }

  test("Queen can capture opponent's pieces only on diagonals and lines") {
    val board = Board()
      .add(Point(4,4), Piece(Queen, Black)).get
      .add(Point(7,7), Piece(Rook, White)).get
      .add(Point(4,6), Piece(Rook, White)).get
      .add(Point(3,6), Piece(Knight, White)).get

    assert(board.possibleCaptures(Point(4,4)).toSet == Set(Point(7,7), Point(4,6)))
  }

  test("When piece is moved, it isn't on origin position") {
    val piece = Piece(Queen, Black)
    val from = Point(1,1)
    val to = Point(1,7)
    val board = Board()
      .add(from, piece).get
      .applyShift(Move(from, to)).get
    assert(board.getAt(from).isEmpty)
  }

  test("When piece is moved, it is on new position") {
    val piece = Piece(Queen, Black)
    val from = Point(1,1)
    val to = Point(1,7)
    val board = Board()
      .add(from, piece).get
      .applyShift(Move(from, to)).get
    assert(board.getAt(to).get == piece)
  }

  test("When origin field is empty, applyShift returns None") {
    assert(Board().applyShift(Move(Point(1,1),Point(1,2))).isEmpty)
  }

  test("When target field is occupied, applyShift returns None") {
    val from = Point(1,1)
    val to = Point(1,7)
    val piece = Piece(Queen, Black)
    val piece2 = Piece(Knight, White)
    assert(Board()
      .add(from, piece).get
      .add(to, piece2).get
      .applyShift(Move(from, to)).isEmpty)
  }

  test("After capturing, origin field is empty") {
    val from = Point(1,1)
    val to = Point(1,7)
    val piece = Piece(Queen, Black)
    val piece2 = Piece(Knight, White)
    assert(
      Board()
        .add(from, piece).get
        .add(to, piece2).get
        .applyCapture(Move(from, to)).get
        .getAt(from).isEmpty
    )
  }

  test("After capturing, attacking piece is on target field") {
    val from = Point(1,1)
    val to = Point(1,7)
    val piece = Piece(Queen, Black)
    val piece2 = Piece(Knight, White)
    assert(
      Board()
        .add(from, piece).get
        .add(to, piece2).get
        .applyCapture(Move(from, to)).get
        .getAt(to).get == piece
    )
  }

  test("applyCapture returns None if origin field is empty") {
    assert(
      Board().applyCapture(Move(Point(1,1), Point(5,1))).isEmpty
    )
  }

  test("applyCapture returns None if target field is empty") {
    val from = Point(1,1)
    val to = Point(5,5)
    assert(
      Board()
        .add(from, Piece(Queen, White)).get
        .applyCapture(Move(from, to)).isEmpty
    )
  }

  test("applyCapture return None if both pieces have same color") {
    val from = Point(1,1)
    val to = Point(5,5)
    val piece = Piece(Queen, Black)
    val piece2 = Piece(Knight, Black)
    assert(
      Board()
        .add(from, piece).get
        .add(to, piece2).get
        .applyCapture(Move(from, to)).isEmpty
    )
  }

  test("Board contains all pieces added from another map") {
    val pawn = Piece(Pawn, White)
    val pieces: Map[Point, Piece] = List(Point(0,1) -> pawn, Point(1,1) -> pawn).toMap
    val board = Board()
        .add(Point(2,1), pawn).get
        .add(pieces).get
    assert(
      board.getAt(Point(0,1)).get == pawn &&
      board.getAt(Point(1,1)).get == pawn &&
      board.getAt(Point(2,1)).get == pawn
    )
  }

  test("test kingPosition") {
    val king_pos = Point(6,0)
    assert(
      Board()
        .add(king_pos, Piece(King, White)).get
        .kingPosition(White) == king_pos
    )
  }

  test("test possibleCapturesOf") {
    assert(
      Board().add(
        List(
          Point(1,1) -> Piece(Pawn, White),
          Point(0,2) -> Piece(Rook, Black),
          Point(2,2) -> Piece(Knight, Black),
          Point(2,5) -> Piece(Rook, White)
        ).toMap
      ).get
        .possibleCapturesOf(White).toSet ==
        Set(
          Move(Point(1,1), Point(0,2)),
          Move(Point(1,1), Point(2,2)),
          Move(Point(2,5), Point(2,2))
        )
    )
  }

  test("test canBeCaptured") {
    val board = Board()
      .add(
        List(
          Point(1,1) -> Piece(Pawn, White),
          Point(0,2) -> Piece(Rook, Black),
          Point(1,2) -> Piece(Knight, Black)
        ).toMap
      ).get
    assert(board.canBeCaptured(Point(0,2)))
    assert(!board.canBeCaptured(Point(1,2)))
  }

  test("If check on king is possible checkOf returns true") {
    val board = Board()
      .add(
        List(
          Point(1,1) -> Piece(King, White),
          Point(7,7) -> Piece(Queen, Black),
          Point(1,7) -> Piece(King, Black)
        ).toMap
      ).get
    assert(board.checkOf(White))
    assert(!board.checkOf(Black))
  }

  test("If from field is empty, applyLegalMove returns None") {
    assert(
      Board().applyLegalMove(Move(Point(1,1), Point(3,1)), White).isEmpty
    )
  }

  test("If move if legal, applyLegalMove returns new board state") {
    assert(
      Board()
        .add(Point(1,1), Piece(King,White)).get
        .applyLegalMove(Move(Point(1,1), Point(1,2)), White).isDefined
    )
  }

  test("If capturing is legal, applyLegalMove returns new board state") {
    assert(
      Board()
        .add(
          List(
            Point(1,1) -> Piece(King,White),
            Point(1,2) -> Piece(Pawn,Black)
          ).toMap
        ).get
        .applyLegalMove(Move(Point(1,1), Point(1,2)), White).isDefined
    )
  }

  test("If move causes check, applyLegalMove returns None") {
    assert(
      Board()
        .add(
          List(
            Point(1,1) -> Piece(King,White),
            Point(1,2) -> Piece(Pawn,Black),
            Point(7,2) -> Piece(Rook,Black)
          ).toMap
        ).get
        .applyLegalMove(Move(Point(1,1), Point(1,2)), White).isEmpty
    )
  }

  test("applyLegalMove returns executed move log") {
    val from = Point(1,1)
    val to = Point(1,2)
    assert(
      Board()
        .add(from, Piece(King,White)).get
        .applyLegalMove(Move(from, to), White)
        .get._2 == MoveLog(Move(from,to))
    )
  }

  test("applyLegalMove returns executed capture log") {
    val from = Point(1,1)
    val to = Point(1,2)
    val move = Move(from, to)
    val pawn = Piece(Pawn, Black)
    assert(
      Board()
        .add(
          List(
            from -> Piece(King,White),
            to -> pawn
          ).toMap
        ).get
        .applyLegalMove(move, White)
        .get._2 == MoveLog(move,Some(pawn))
    )
  }

  test("Undo returns None if target field is empty") {
    assert(
      Board().undo(MoveLog(Move(Point(3,1), Point(1,1)))).isEmpty
    )
  }

  test("Undo move piece from target field to origin field") {
    val origin = Point(1,1)
    val target = Point(3,1)
    val pawn = Piece(Pawn, White)
    assert(
      Board()
        .add(target, pawn).get
        .undo(MoveLog(Move(origin, target))).get
        .getAt(origin).get == pawn
    )
  }

  test("If there is checkmate, checkateOf returns true") {
    assert(
      Board()
        .add(
          List(
            Point(7,3) -> Piece(Queen, White),
            Point(6,4) -> Piece(Rook, White),
            Point(7,5) -> Piece(King, Black)
          ).toMap
        ).get
        .checkmateOf(Black)
    )
  }

  test("If there is no check, checkmateOf returns false") {
    assert(
      !Board()
        .add(Point(5,5), Piece(King, Black)).get
        .checkmateOf(Black)
    )
  }

  test("If king can escape, checkmateOf returns false") {
    assert(
      !Board()
        .add(
          List(
            Point(7,3) -> Piece(Queen, White),
            Point(7,5) -> Piece(King, Black)
          ).toMap
        ).get
        .checkmateOf(Black)
    )
  }

  test("If only attacker can be captured, checkmateOf returns false") {
    assert(
      !Board()
        .add(
          List(
            Point(5,7) -> Piece(Queen, White),
            Point(5,6) -> Piece(Rook, White),
            Point(7,7) -> Piece(King, Black),
            Point(0,7) -> Piece(Rook, Black)
          ).toMap
        ).get
        .checkmateOf(Black)
    )
  }

  test("If check can be blocked, checkmateOf returns false") {
    assert(
      !Board()
        .add(
          List(
            Point(5,6) -> Piece(Rook, White),
            Point(5,5) -> Piece(Rook, White),
            Point(3,0) -> Piece(Rook, Black),
            Point(7,7) -> Piece(King, Black),
            Point(0,7) -> Piece(Queen, White)
          ).toMap
        ).get
        .checkmateOf(Black)
    )
  }
}
