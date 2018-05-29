package ui

import chess.board.{Black, Color, Point, White}

object Board {
  val Frame: Char = '+'

  def mergeFields(fields: IndexedSeq[Field]): String = {
    val mid = for(i <- 0 to 4) yield
      Frame.toString + (for(f <- fields) yield f.content(i)).reduce(_+ Frame.toString +_) + Frame.toString
    mid.reduce(_ + "\n" + _) + "\n"
  }
  def separatorLine(frame: Char, field_size: Int, fields_nb: Int): String = {
    frame.toString * (1 + (field_size + 1) * fields_nb)
  }
  def print(board: chess.board.Board, active: Option[Point] = None ): String = {
    val size = board.rect.size
    val sep = separatorLine(Frame, field_size = 5, fields_nb = size.x)
    val tab: IndexedSeq[String] =
      for (y <- 0 until size.y) yield
      {
        val row: IndexedSeq[Field] = for (x <- 0 until size.y) yield {
          val pos = Point(x,y)
          if (pos == active.getOrElse(Point(-1,-1)))
            ActiveField(fieldColor(pos), board.getAt(pos))
          else
            InactiveField(fieldColor(pos), board.getAt(pos))
        }

        mergeFields(row)
      }

    sep + "\n" +  tab.reduce(_ + sep + "\n" +  _) + sep
  }

  def fieldColor(pos: Point): Color = {
    if ((pos.x + pos.y)%2 == 0) White
    else Black
  }
}
