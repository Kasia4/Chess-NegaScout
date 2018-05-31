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
    def display(): Unit = UI.displayGame(state, prompt = Some(ChoosePiece(col)))

    PlayerInput.readField(pieces_pos.contains, display)
  }

  def readTargetPosition(gameState: GameState, from: Point): Point = {
    val possible_targets = gameState.board.possibleMoves(from) ::: gameState.board.possibleCaptures(from)
    def display(): Unit = UI.displayGame(gameState,active_fiedls = possible_targets.toSet, prompt = Some(ChoosePiece(col)))

    PlayerInput.readField(possible_targets.contains, display)
  }
}
