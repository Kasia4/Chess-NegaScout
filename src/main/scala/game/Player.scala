package game

import chess.{Color, GameState, Move}

trait Player {
  def color: Color
  def executeMove(gameState: GameState): Move
}