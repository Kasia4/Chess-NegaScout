package chess

import scala.util.{Failure, Success, Try}

object Notation {
  private val columnValues: Map[Char, Int] = Map(
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

  def rowValue(symbol: Char): Try[Int] = {
    val sym_int = symbol.asDigit
    if (sym_int >= 1 && sym_int <= 8)
      Success(sym_int - 1)
    else
      Failure(new IllegalArgumentException)
  }

  def parseField(in: String): Try[Point] = {
    if (in.length() != 2) Failure(new IllegalArgumentException)
    else {
      val x = columnValue(in.charAt(0))
      val y = rowValue(in.charAt(1))
      if (x.isFailure || y.isFailure) Failure(new IllegalArgumentException)
      else Success(Point(x.get, y.get))
    }
  }

}
