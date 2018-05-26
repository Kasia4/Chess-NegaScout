package chess.board

case class Board (pieces: Map[Point, Piece] = Map(), rect: Rectangle = Rectangle(Point(8,8))) {

  def add(pos: Point, piece: Piece):Option[Board] = {
    if (rect.contains(pos) && isEmptyAt(pos))
      Some(Board(pieces + (pos -> piece)))
    else None
  }

  def remove(pos: Point) = Board(pieces - pos)
  def isEmptyAt(pos: Point): Boolean = pieces.get(pos).isEmpty
  def getAt(pos: Point):Option[Piece] = pieces.get(pos)
  def ofColor(color: Color): Map[Point, Piece] = pieces.filter(p => p._2.color == color)
  def ofType(pieceType: PieceType): Map[Point, Piece] = pieces.filter(p => p._2.ptype == pieceType)

  def findOccupiedFieldInDirection(from: Point, dir: Point): Option[Point] = {
    if (rect contains from) {
      if (isEmptyAt(from)) findOccupiedFieldInDirection(from + dir, dir)
      else Some(from)
    }
    else None
  }
}

