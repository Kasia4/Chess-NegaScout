package chess.board

abstract class Piece() {
  val position: Point
  val moved: Boolean

  def moves(): List[Point]
  def captures(): List[Point]
  def toChar: Char
}