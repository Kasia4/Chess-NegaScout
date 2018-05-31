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

  def readPiecePosition(state: GameState): Point = {
    val pieces_pos = state.board.ofColor(col).keys.toSet
    def movable(pos: Point): Boolean = pieces_pos.contains(pos) && state.board.allPossibleMoves(pos).nonEmpty
    def display(): Unit = UI.displayGame(state, prompt = Some(ChoosePiece(col)))

    PlayerInput.readField(movable, display)
  }

  def readTargetPosition(gameState: GameState, from: Point): Point = {
    val possible_targets = gameState.board.allPossibleMoves(from)
    def display(): Unit = UI.displayGame(gameState,active_fiedls = possible_targets.toSet, prompt = Some(ChooseTarget(col)))

    PlayerInput.readField(possible_targets.contains, display)
  }
}
