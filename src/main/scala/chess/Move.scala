package chess

case class Move (from: Point, to: Point)

case class MoveLog (move: Move, captured: Option[Piece] = None, first_move: Boolean = false)
