package ai

import chess.{GameState, Move}

class NegaScout(heuristic: Heuristic, maxDepth: Int = 10) {
  def negaScoutIter(state: GameState, depth: Int, alpha: Int, beta: Int): (Move,Int)= {
    if(state.isTerminated() || depth >= maxDepth) {
      return heuristic(state)
    }
    val moves = state.possibleMoves()
    var a = alpha
    var b = beta
    //var bestMove
    for(m <- moves) {
      val child = state.applyMove(m)
      val score = -negaScoutIter(child.get, depth + 1, -b, -a)._2
      if (score > a && score < b/*check if first*/ ) {
        a = -negaScoutIter(child.get, depth + 1, -b, -score)._2
      }
      state.undo()
      a = if (a > score) a else score
      if (a >= beta) {
        return (m, a)
      }
      b = a + 1 // bestMove
    }
    return a
  }
}
