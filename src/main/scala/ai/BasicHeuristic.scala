package ai
import chess.{Color, GameState}

/**
  * Basic implementation of heuristic function used by NegaScout algorithm
  *
  * @param color
  */
case class BasicHeuristic(color: Color) extends Heuristic {
  /**
    * Value of heuristic depends on amount of specific pieces
    * It is count from AI perspective which color is determined by tuple
    *
    * @param gameState
    * @return
    */
  override def countStateValue(gameState: GameState): Double =
    countPiecesValues(gameState, color) - countPiecesValues(gameState, color.opponent)

  /**
    * Helper function for counting sum of pieces values of given color
    * @param gameState
    * @param color
    * @return
    */
  def countPiecesValues(gameState: GameState, color: Color): Double =
    gameState.board.ofColor(color).map(_._2.ptype.value).sum
}
