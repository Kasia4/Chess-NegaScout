package game.ui

import chess.{Black, Color, Piece, White}

trait Field{
  val piece: Option[Piece]
  val color: Color
  def content: IndexedSeq[String]
}

case class InactiveField(override val color: Color,
                         override val piece: Option[Piece] = None) extends Field {
  override def content: IndexedSeq[String] = {
    val bg = Field.Background(color).toString
    val s = Field.pieceSymbol(color, piece)
    val b = Field.baseSymbol(color, piece)

    IndexedSeq(
      bg * 5,
      bg * 2 + s + bg * 2,
      bg * 2 + b + bg * 2,
      bg * 5
    )
  }
}

case class ActiveField(override val color: Color,
                       override val piece: Option[Piece] = None) extends Field {
  override def content: IndexedSeq[String] = {
    val bg = Field.Background(color).toString
    val s = Field.pieceSymbol(color, piece)
    val b = Field.baseSymbol(color, piece)

    val f = Field.ActiveFrame.toString
    IndexedSeq(
      f * 5,
      f + bg + s + bg + f,
      f + bg + b + bg + f,
      f * 5
    )

  }
}

object Field {
  val ActiveFrame: Char = 'x'
  val Background: Map[Color, Char] = List(White -> '.', Black -> ' ').toMap

  def background(color: Color) = Background(color).toString
  def pieceSymbol(field_color: Color, piece: Option[Piece]): String =
    (if (piece.isEmpty) background(field_color) else piece.get.ptype.symbol).toString
  def baseSymbol(field_color: Color, piece: Option[Piece]): String =
    (if (piece.isEmpty) background(field_color) else piece.get.color.base).toString
}

