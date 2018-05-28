package ui

import chess.board.{Black, Color, Piece, White}

trait Field{
  val piece: Option[Piece]
  val color: Color
  def content: IndexedSeq[String]
}

case class InactiveField(override val color: Color,
                         override val piece: Option[Piece] = None) extends Field {
  override def content: IndexedSeq[String] = {
    val s = (if (piece.isEmpty) ' ' else piece.get.ptype.symbol).toString
    val bg = Field.Background(color).toString
    IndexedSeq(
      bg * 5,
      bg * 5,
      bg * 2 + s + bg * 2,
      bg * 5,
      bg * 5
    )
  }
}

case class ActiveField(override val color: Color,
                       override val piece: Option[Piece] = None) extends Field {
  override def content: IndexedSeq[String] = {
    val s = (if (piece.isEmpty) ' ' else piece.get.ptype.symbol).toString
    val bg = Field.Background(color).toString
    val f = Field.ActiveFrame.toString
    IndexedSeq(
      f * 5,
      f + bg * 3 + f,
      f + bg + s + bg + f,
      f + bg * 3 + f,
      f * 5
    )

  }
}

object Field {
  val Frame: Char = '+'
  val ActiveFrame: Char = 'x'
  val Background: Map[Color, Char] = List(White -> '.', Black -> ' ').toMap

  def mergeFields(fields: List[Field]): String = {
    val mid = for(i <- 0 to 4) yield
      Frame.toString + (for(f <- fields) yield f.content(i)).reduce(_+ Frame.toString +_) + Frame.toString
    mid.reduce(_ + "\n" + _) + "\n"
  }
}

