package chess.board

trait GameResult

trait Finished extends GameResult {}

trait Win extends Finished {
  val winner: Color
}

case class WhiteWin() extends Win {
  override val winner: Color = White
}

case class BlackWin() extends Win {
  override val winner: Color = Black
}

case class Pending() extends GameResult

case class Resignation(override val winner: Color) extends Win

case class Draw() extends Finished