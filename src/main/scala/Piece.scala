package chess.board

trait Piece {
  def moves(): List[Point]
  def toChar(): Char
}

