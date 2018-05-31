package ai

import chess.{GameState, Move}

class NegaScout(heuristic: Heuristic, maxDepth: Int = 10) {
  def negaScoutIter(state: GameState, alpha: Double, beta: Double, depth: Int): (Double, Option[Move]) = {
    if(state.isTerminated() || depth >= maxDepth)
      (heuristic.countStateValue(state), None)

    val firstMove = state.possibleMoves().head

    val bestMove = state.possibleMoves().map(makeMoveStatePair(_,state))
      .foldLeft(alpha, beta, None: Option[Move]) { (currentAlgParams, curr) => {
        if(currentAlgParams._1 >= currentAlgParams._2) currentAlgParams

        else if(curr._1 == firstMove){
          val score = - negaScoutIter(curr._2, -currentAlgParams._2, -currentAlgParams._1, depth+1)._1

          if(currentAlgParams._1 < score) {
            (score, currentAlgParams._2, Some(curr._1))
          }

          else
            currentAlgParams
        }

        else {
          val newAlpha = math.max(currentAlgParams._1, - negaScoutIter(curr._2, -currentAlgParams._1 - 1, -currentAlgParams._1, depth+1)._1)

          if(currentAlgParams._1 < newAlpha &&  newAlpha < currentAlgParams._2) {
              val score = - negaScoutIter(curr._2, -currentAlgParams._2, -newAlpha, depth+1)._1
              (score, currentAlgParams._2, Some(curr._1))
            }

            else currentAlgParams
        }
      }
    }
    (bestMove._1, bestMove._3)
  }

  def negaScout(gameState: GameState): (Double, Option[Move]) =
    negaScoutIter(gameState, Double.NegativeInfinity, Double.PositiveInfinity, 0)

  private def makeMoveStatePair(move: Move, initialState: GameState): (Move, GameState) = (move, initialState.applyMove(move).get)

}
