package game.ui

import chess.{Black, Board, Color, Point, White}

object BoardUI {
  val Frame: Char = '+'

  /**
    * Merge fields' string representations
    * @param fields Sequence of fields
    * @param label side label
    * @return
    */
  def mergeFields(fields: IndexedSeq[Field], label: String): String = {
    val label_seq = IndexedSeq(" ", label.toString, " ", " ")
    val mid = for(i <- 0 to 3) yield {
      Frame.toString + (for(f <- fields) yield f.content(i)).reduce(_+ Frame.toString +_) + Frame.toString + " " +label_seq(i)
    }
    mid.reduce(_ + "\n" + _) + "\n"
  }

  /**
    * Generate horizontal label
    * @param separator separator symbol
    * @param space space symbol
    * @return
    */
  def horizontalLabel(separator: Char, space: Char): String = {
    val labels = List("A", "B", "C", "D", "E", "F", "G", "H")
    val sep = separator.toString
    val sp = space.toString
    (for (label <- labels) yield sep + sp * 2 + label + sp * 2).reduce(_ + _) + sep + "\n"

  }

  /**
    * Generate separator line
    * @param frame frame symbol
    * @param field_size field size
    * @param fields_nb count of field
    * @return separator line string
    */
  def separatorLine(frame: Char, field_size: Int, fields_nb: Int): String = {
    frame.toString * (2 + (field_size + 1) * fields_nb)
  }

  /**
    * Generates board's visual representation
    * @param board board
    * @param active list of active fields
    * @return
    */
  def giveString(board: Board, active: Set[Point] ): String = {
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

        mergeFields(row, (y+1).toString)
      }

    horizontalLabel(separator = '+',space = ' ') +
      sep + "\n" +  tab.reduce(_ + sep + "\n" +  _) + sep + "\n"
  }

  def fieldColor(pos: Point): Color = {
    if ((pos.x + pos.y)%2 == 1) White
    else Black
  }
}
