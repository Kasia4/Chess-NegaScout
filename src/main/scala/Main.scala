import chess.board._
import ui._

object Main {
  def main(args: Array[String]): Unit = {
    val game = GameState()
    print(ui.Board.print(game.board, Some(Point(1,1))))
  }
}