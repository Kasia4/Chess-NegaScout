package ai

import chess.GameState

trait Heuristic {
  def countStateValue(gameState: GameState): Double
}
