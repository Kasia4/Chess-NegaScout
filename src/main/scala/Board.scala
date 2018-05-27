package chess.board

case class Board (pieces: Map[Point, Piece] = Map(), rect: Rectangle = Rectangle(Point(8,8))) {

  def add(pos: Point, piece: Piece):Option[Board] = {
    if (isEmptyAt(pos))
      Some(Board(pieces + (pos -> piece)))
    else None
  }

  def add(new_pieces: Map[Point, Piece]): Option[Board] = {
    if (!new_pieces.keys.forall(isEmptyAt)) None
    else Some(Board(new_pieces ++ pieces))
  }

  def replace(pos: Point, piece: Piece):Option[Board] = {
    if (rect.contains(pos))
      Some(Board(pieces + (pos -> piece)))
    else None
  }

  def remove(pos: Point) = Board(pieces - pos)

  def isEmptyAt(pos: Point): Boolean = pieces.get(pos).isEmpty && rect.contains(pos)
  def isOccupiedAt(pos: Point): Boolean = pieces.get(pos).isDefined
  def getAt(pos: Point):Option[Piece] = pieces.get(pos)
  def getAt(pos_opt: Option[Point]): Option[Piece] = if (pos_opt.isEmpty) None else getAt(pos_opt.get)
  def ofColor(color: Color): Map[Point, Piece] = pieces.filter(p => p._2.color == color)
  def ofType(pieceType: PieceType): Map[Point, Piece] = pieces.filter(p => p._2.ptype == pieceType)


  def applyMove(move: Move): Option[Board] = {
    if (isOccupiedAt(move.from) && isEmptyAt(move.to)) {
      val moved = getAt(move.from).get
      this.remove(move.from).add(move.to, moved)
    }
    else None
  }

  def applyCapture(move: Move): Option[Board] = {
    if (isOccupiedAt(move.from) && isOccupiedAt(move.to)) {
      if (getAt(move.from).get.color == getAt(move.to).get.color)
        None
      else {
        val moved = getAt(move.from).get
        this.remove(move.from).replace(move.to, moved)
      }
    }
    else None
  }

  def possibleMoves(pos: Point): List[Point] = {
    if (isEmptyAt(pos)) List.empty[Point]
    else {
      val piece = getAt(pos).get
      piece.ptype match {
        case Pawn => {
          val dist = if (piece.moved) 1 else 2
          val dir = piece.color.direction
          pos.path(dir, dist).toList.span(isEmptyAt)._1
        }
        case Knight => pos.lNeighbors()
        case King => pos.neighbors()
        case dir_piece: DirectionalType => scanDirs(dir_piece.dirs, pos)
      }
    }.filter(isEmptyAt)
  }

  def possibleCaptures(pos: Point): List[Point] = {
    if (isEmptyAt(pos)) List.empty[Point]
    else {
      val piece = getAt(pos).get
      piece.ptype match {
        case Pawn => {
          val dir = piece.color.direction
          List(Right.vec + dir + pos, Left.vec + dir + pos)
        }.filter(isOpponent(_,piece.color))
        case Knight => pos.lNeighbors().filter(isOpponent(_, piece.color))
        case King => pos.neighbors().filter(isOpponent(_, piece.color))
        case dir_piece: DirectionalType => scanOpponent(dir_piece.dirs, pos, piece.color)
      }
    }
  }

  def scanDirs(dirs: List[Direction], start: Point): List[Point] = {
    (for (dir <- dirs) yield
      rect.pathToBorder(start, dir).span(isEmptyAt)._1).flatten
  }

  def scanOpponent(dirs: List[Direction], start: Point, color: Color): List[Point] = {
    val opts = for(dir <- dirs) yield {
      rect.findInDirection(start, dir, isOccupiedAt)
    }
    for (opt <- opts if opt.isDefined && isOpponent(opt.get, color)) yield opt.get

  }

  def isOpponent(pos: Point, color: Color): Boolean = isOccupiedAt(pos) && getAt(pos).get.color == color.opponent
}

object Board {
  val startBoard: Board = {
    val types = IndexedSeq(Rook, Knight, Bishop, Queen, King, Bishop, Knight, Rook)
    val w_pawn = Piece(Pawn, White)
    val b_pawn = Piece(Pawn, Black)
    val row = Point(-1, 0).path(Right.vec, length = 8).toIndexedSeq
    val pieces =
      (for (i <- 0 to 7) yield List(
        row(i) -> Piece(types(i), White),
        row(i) + Point(0, 1) -> w_pawn,
        row(i) + Point(0, 6) -> b_pawn,
        row(i) + Point(0, 7) -> Piece(types(i), Black)
      ).flatten).flatten.toMap[Point, Piece]
    Board(pieces)
  }
}