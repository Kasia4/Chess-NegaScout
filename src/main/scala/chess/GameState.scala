package chess

case class GameState(current_player: Color = White,
                     board: Board = Board.startBoard,
                     history: List[MoveLog] = List.empty[MoveLog])
{

  def applyMove(move: Move): Option[GameState] = {
    val opt_board = board.applyLegalMove(move, current_player)
    if (opt_board.isEmpty) None
    else Some(GameState(current_player.opponent, opt_board.get._1, opt_board.get._2 +: history))
  }

  def undo(): Option[GameState] = {
    if (history.isEmpty) None
    else Some(GameState(current_player.opponent, board.undo(history.head).get, history.tail))
  }

  lazy val Result: GameResult = Turn(current_player)

  def result(): GameResult = {
    Result
  }

  def possibleMoves() : List[Move] = {
    board.possibleMovesOf(current_player)
  }

  def isTerminated(): Boolean = {
    Result match {
      case _: Finished => true
      case _: Pending => false
    }
  }
}
