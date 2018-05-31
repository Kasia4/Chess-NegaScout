package game

import chess.GameState

trait Player {
  def executeMove(gameState: GameState): GameState
}
