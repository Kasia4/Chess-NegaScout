package ui

import chess.board.{GameState, Point, Prompt}

object UI {
  def displayGame(game_state: GameState, prompt: Option[Prompt] = None) :Unit = {
    print(ui.Board.giveString(game_state.board, Set(Point(1,1), Point(1,3), Point(3, 3))))
    println(game_state.result().message())
    if (prompt.isDefined) {
      println(prompt.get.content + ": ")
    }

  }
}
