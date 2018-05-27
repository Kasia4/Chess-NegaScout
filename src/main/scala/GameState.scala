package chess.board
case class GameState(current_player: Color = White, board: Board = Board.startBoard) {

  def applyMove(move: Move): Option[GameState] = {
    val opt_board = board.applyLegalMove(move, current_player)
    if (opt_board.isEmpty) None
    else Some(GameState(current_player.opponent, opt_board.get))
  }

}
