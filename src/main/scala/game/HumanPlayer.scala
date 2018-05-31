package game

import chess._
import game.ui.UI

case class HumanPlayer(col: Color) extends Player {
  override def color: Color = col
  override def executeMove(gameState: GameState): Move = {
    UI.displayGame(gameState, Some(ChoosePiece(color)))
    val from = scala.io.StdIn.readLine()
    Move(Point(1,1), Point(1,3))
  }

  def readPiecePosition(gameState: GameState): Point = {
    val pieces_pos = gameState.board.ofColor(col).keys.toSet
    var from: Point = Point(-1,-1)
    do {
      UI.displayGame(gameState, Some(ChoosePiece(color)))
      val input = Notation.parseField(scala.io.StdIn.readLine())
      if (input.isSuccess)
        from = input.get
    } while (!pieces_pos.contains(from))
    from
  }

  def readTargetPosition(gameState: GameState, from: Point): Point = {

  }
}
