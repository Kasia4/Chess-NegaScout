package game

import chess.{Color, GameState, Move}
import ai.{NegaScout, BasicHeuristic}

case class AIPlayer(col: Color) extends Player {
  override def color: Color = col
  def negaScoutAlg= NegaScout(BasicHeuristic(color))
  override def executeMove(gameState: GameState): Move = negaScoutAlg.negaScout(gameState)._2.get
}
