package chess.board

trait GameResult {
  def message(): String
}

trait Finished extends GameResult {}
trait Pending extends GameResult {
  val current: Color
}

trait Win extends Finished {
  val winner: Color
}

case class WhiteWin() extends Win {
  override val winner: Color = White
  override def message(): String = "White win!"
}

case class BlackWin() extends Win {
  override val winner: Color = Black
  override def message(): String = "Black win!"
}

case class Check(override val current: Color) extends Pending {
  override def message(): String = current.toString + "checked"
}

case class Draw() extends Finished {
  override def message(): String = "Draw"
}