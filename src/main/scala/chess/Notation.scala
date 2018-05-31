package chess

import scala.util.Try

object Notation {
  val columnValues: Map[Char, Int] = Map(
    'A' -> 0,
    'B' -> 1,
    'C' -> 2,
    'D' -> 3,
    'E' -> 4,
    'F' -> 5,
    'G' -> 6,
    'H' -> 7
  )

  def columnValue(symbol: Char): Try[Int] = Try(columnValues(symbol))


}
