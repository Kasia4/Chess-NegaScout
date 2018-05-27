package chess.board

case class Board (pieces: Map[Point, Piece] = Map(), rect: Rectangle = Rectangle(Point(8,8))) {

  def add(pos: Point, piece: Piece):Option[Board] = {
    if (rect.contains(pos) && isEmptyAt(pos))
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

