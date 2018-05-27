package chess.board

case class Move (from: Point, to: Point)

case class MoveLog (move: Move, captured: Option[Piece] = None)
