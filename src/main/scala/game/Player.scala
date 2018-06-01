package game

import chess.{Color, GameState, Move}

trait Player {
  def color: Color

  /**
    * Asks player for move
    * @param gameState current state of game
    * @return chosen move
    */
  def executeMove(gameState: GameState): Move
}