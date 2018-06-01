import chess.{Black, White}
import game.{AIPlayer, Game, HumanPlayer}

object Main {
  def main(args: Array[String]): Unit = {
    Game.start(HumanPlayer(White), AIPlayer(Black))
  }
}