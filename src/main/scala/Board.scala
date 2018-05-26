package chess.board

case class Board (pieces: Map[Point, Piece] = Map(), rect: Rectangle = Rectangle(Point(8,8))) {

  def add(pos: Point, piece: Piece):Option[Board] = {
    if (rect.contains(pos) && isEmptyAt(pos))
      Some(Board(pieces + (pos -> piece)))
    else None
  }

  def remove(pos: Point) = Board(pieces - pos)
  def isEmptyAt(pos: Point): Boolean = pieces.get(pos).isEmpty
  def isOccupiedAt(pos: Point): Boolean = pieces.get(pos).isDefined
  def getAt(pos: Point):Option[Piece] = pieces.get(pos)
  def ofColor(color: Color): Map[Point, Piece] = pieces.filter(p => p._2.color == color)
  def ofType(pieceType: PieceType): Map[Point, Piece] = pieces.filter(p => p._2.ptype == pieceType)



  def possibleMoves(pos: Point): List[Point] = {
    if (isEmptyAt(pos)) List.empty[Point]
    else {
      val piece = getAt(pos).get
      piece.ptype match {
        case Pawn => {
          val dist = if (piece.moved) 1 else 2
          val dir = if (piece.color == White) Up.vec else Down.vec
          pos.path(dir, dist).toList.span(isEmptyAt)._1
        }
        case Knight => pos.lNeighbors()
        case King => pos.neighbors()
        case dir_piece: DirectionalType => scanDirs(dir_piece.dirs, pos)
      }
    }.filter(canMove)

  }

  def findOccupiedFieldInDirection(from: Point, dir: Direction): Option[Point] = {
    if (rect contains from) {
      if (isEmptyAt(from)) findOccupiedFieldInDirection(from + dir.vec, dir)
      else Some(from)
    }
    else None
  }

  def scanDirs(dirs: List[Direction], start: Point): List[Point] = {
    (for (dir <- dirs) yield
      rect.pathToBorder(start, dir).span(isEmptyAt)._1).flatten
  }

  def canMove(pos: Point): Boolean = isEmptyAt(pos) && rect.contains(pos)
}

