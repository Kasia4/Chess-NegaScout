package chess.board

case class Board (pieces: Map[Point, Piece] = Map()) {
  def add(pos: Point, piece: Piece) = Board(pieces + (pos -> piece))
  def remove(pos: Point) = Board(pieces - pos)
  def isEmptyAt(pos: Point): Boolean = pieces.get(pos).isEmpty
  def getAt(pos: Point):Option[Piece] = pieces.get(pos)
  def ofColor(color: Color): Map[Point, Piece] = pieces.filter(p => p._2.color == color)
  def ofType(pieceType: PieceType): Map[Point, Piece] = pieces.filter(p => p._2.ptype == pieceType)
}

