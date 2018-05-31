package ai
import chess.{Color, GameState}

case class BasicHeuristic(color: Color) extends Heuristic {
  override def countStateValue(gameState: GameState): Double =
    countPiecesValues(gameState, color) - countPiecesValues(gameState, color.opponent)

  def countPiecesValues(gameState: GameState, color: Color): Double =
    gameState.board.ofColor(color).map(_._2.ptype.value).sum
}
