package chess.board

case class Board (pieces: Map[Point, Piece] = Map()) {
  def add(pos: Point, piece: Piece) = Board(pieces + (pos -> piece))
  def remove(pos: Point) = Board(pieces - pos)
}

