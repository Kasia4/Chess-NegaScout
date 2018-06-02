package chess

case class GameState(current_player: Color = White,
                     board: Board = Board.startBoard,
                     history: List[MoveLog] = List.empty[MoveLog])
{

  /**
    * Execute move on board
    * @param move applying move
    * @return GameState with updated board and moves history or None if illegal move
    */
  def applyMove(move: Move): Option[GameState] = {
    val opt_board = board.applyLegalMove(move, current_player)
    if (opt_board.isEmpty) None
    else Some(GameState(current_player.opponent, opt_board.get._1, opt_board.get._2 +: history))
  }

  /**
    * Undo last move from history
    * @return GameState with undone move or None if error
    */
  def undo(): Option[GameState] = {
    if (history.isEmpty) None
    else Some(GameState(current_player.opponent, board.undo(history.head).get, history.tail))
  }


  lazy val Result: GameResult = {
    if (board.checkmateOf(White))
      BlackWin()
    else if (board.checkmateOf(Black))
      WhiteWin()
    else Turn(current_player)
  }

  def result(): GameResult = {
    Result
  }

  def possibleMoves() : List[Move] = {
    board.allPossibleMovesOf(current_player)
  }

  /**
    * Checks if game is ended
    * @return
    */
  def isTerminated: Boolean = {
    board.checkmateOf(current_player)
  }
}
