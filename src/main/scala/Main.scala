import chess.{Black, GameState, White}
import game.HumanPlayer
import game.Game

object Main {
  def main(args: Array[String]): Unit = {
    val player = HumanPlayer(White)
    Game.start(HumanPlayer(White), HumanPlayer(Black))
  }
}