import chess.{GameState, White}
import game.HumanPlayer

object Main {
  def main(args: Array[String]): Unit = {
    val player = HumanPlayer(White)
    val game = GameState()

    player.executeMove(game)
//    print(game.ui.Board.print(game.board, Some(Point(1,1))))
  }
}