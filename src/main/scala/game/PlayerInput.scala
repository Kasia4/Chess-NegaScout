package game

import chess.{Notation, Point}

object PlayerInput {
  /**
    * Reads field position that fill given condition
    * @param cond condition for field
    * @param displayer method to display game
    * @return read field position
    */
  def readField(cond: Point => Boolean, displayer: () => Unit): Point = {
    var point: Option[Point] = None
    do {
      displayer()
      val input = Notation.parseField(scala.io.StdIn.readLine())
      if(input.isSuccess)
        point = Some(input.get)
    } while (point.isEmpty || !cond(point.get))
    point.get
  }
}
