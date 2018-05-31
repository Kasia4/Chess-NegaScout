package game.ui

import chess.{GameState, Point}
import game.Prompt

object UI {
  def displayGame(game_state: GameState, prompt: Option[Prompt] = None) :Unit = {
    print(BoardUI.giveString(game_state.board, Set(Point(1,1), Point(1,3), Point(3, 3))))
    println(game_state.result().message())
    if (prompt.isDefined) {
      println(prompt.get.content + ": ")
    }

  }
}
