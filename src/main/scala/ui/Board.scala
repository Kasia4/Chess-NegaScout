package ui

import chess.board.{Black, Color, Point, White}

object Board {
  val Frame: Char = '+'

  def mergeFields(fields: IndexedSeq[Field], label: String): String = {
    val label_seq = IndexedSeq(" ", label.toString, " ", " ")
    val mid = for(i <- 0 to 3) yield {
      Frame.toString + (for(f <- fields) yield f.content(i)).reduce(_+ Frame.toString +_) + Frame.toString + " " +label_seq(i)
    }
    mid.reduce(_ + "\n" + _) + "\n"
  }

  def horizontalLabel(separator: Char, space: Char): String = {
    val labels = List("A", "B", "C", "D", "E", "F", "G", "H")
    val sep = separator.toString
    val sp = space.toString
    (for (label <- labels) yield sep + sp * 2 + label + sp * 2).reduce(_ + _) + sep + "\n"

  }
  def separatorLine(frame: Char, field_size: Int, fields_nb: Int): String = {
    frame.toString * (2 + (field_size + 1) * fields_nb)
  }

  def giveString(board: chess.board.Board, active: Set[Point] ): String = {
    val size = board.rect.size
    val sep = separatorLine(Frame, field_size = 5, fields_nb = size.x)
    val tab: IndexedSeq[String] =
      for (y <- 0 until size.y) yield
      {
        val row: IndexedSeq[Field] = for (x <- 0 until size.y) yield {
          val pos = Point(x,y)
          if (active.contains(pos))
            ActiveField(fieldColor(pos), board.getAt(pos))
          else
            InactiveField(fieldColor(pos), board.getAt(pos))
        }

        mergeFields(row, y.toString)
      }

    horizontalLabel(separator = '+',space = ' ') +
      sep + "\n" +  tab.reduce(_ + sep + "\n" +  _) + sep + "\n"
  }

  def fieldColor(pos: Point): Color = {
    if ((pos.x + pos.y)%2 == 0) White
    else Black
  }
}
