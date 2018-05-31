package game

import chess._
import game.ui.UI

case class HumanPlayer(col: Color) extends Player {
  override def color: Color = col
  override def executeMove(gameState: GameState): Move = {
    val from = readPiecePosition(gameState)
    val to = readTargetPosition(gameState, from)
    Move(from, to)
  }

  def readPiecePosition(gameState: GameState): Point = {
    val pieces_pos = gameState.board.ofColor(col).keys.toSet
    var from: Point = Point(-1,-1)
    do {
      UI.displayGame(gameState, prompt = Some(ChoosePiece(color)))
      val input = Notation.parseField(scala.io.StdIn.readLine())
      if (input.isSuccess)
        from = input.get
    } while (!pieces_pos.contains(from))
    from
  }

  def readTargetPosition(gameState: GameState, from: Point): Point = {
    val possible_targets = gameState.board.possibleMoves(from) ::: gameState.board.possibleCaptures(from)
    var to: Point = Point(-1, -1)
    do {
      UI.displayGame(gameState, active_fiedls = possible_targets.toSet, prompt = Some(ChooseTarget(color)))
      val input = Notation.parseField(scala.io.StdIn.readLine())
      if (input.isSuccess)
        to = input.get
    } while(!possible_targets.contains(to))
    to
  }
}
