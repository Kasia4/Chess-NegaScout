package chess.board

import scala.collection.immutable.Stack

case class GameState(current_player: Color = White,
                     board: Board = Board.startBoard,
                     history: List[MoveLog] = List.empty[MoveLog]) {

  def applyMove(move: Move): Option[GameState] = {
    val opt_board = board.applyLegalMove(move, current_player)
    if (opt_board.isEmpty) None
    else Some(GameState(current_player.opponent, opt_board.get._1))
  }

}
