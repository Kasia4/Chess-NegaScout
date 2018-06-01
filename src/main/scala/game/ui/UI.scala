package game.ui

import chess.{GameState, Point}
import game.Prompt

object UI {
  /**
    * Print board and prompt for player
    * @param game_state
    * @param active_fiedls
    * @param prompt
    */
  def displayGame(game_state: GameState,
                  active_fiedls: Set[Point] = Set.empty[Point],
                  prompt: Option[Prompt] = None) :Unit = {
    print(BoardUI.giveString(game_state.board, active_fiedls))
    println(game_state.result().message())
    if (prompt.isDefined) {
      println(prompt.get.content + ": ")
    }

  }
}
