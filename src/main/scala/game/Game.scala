package game

import chess.{Black, GameState, Move, White}

object Game {
  def start(white_player: Player, black_player: Player): Unit = {
    var game_state = GameState()
    while (!game_state.isTerminated) {
      val move: Move = game_state.current_player match {
        case White => white_player.executeMove(game_state)
        case Black => black_player.executeMove(game_state)
      }
      game_state = game_state.applyMove(move).get

    }
  }
}
