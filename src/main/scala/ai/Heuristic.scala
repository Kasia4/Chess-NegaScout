package ai

import chess.GameState

/**
  * Provides function which should be implemented by heuristic used in NegaScout algorithm
  */
trait Heuristic {
  def countStateValue(gameState: GameState): Double
}
