package game

import chess.Color

trait Prompt {
  def content: String
}

case class ChoosePiece(color: Color) extends Prompt{
  override def content: String = color.name + ", choose piece to move"
}

case class ChooseTarget(color: Color) extends Prompt{
  override def content: String = color.name + ", choose target field"
}

