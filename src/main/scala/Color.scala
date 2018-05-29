package chess.board

sealed trait Color {
  val name: String
  val symbol: Char
  val direction: Point
  val opponent: Color
  val base: Char
}

case object White extends Color {
  val name: String = "White"
  val symbol: Char = 'W'
  val direction: Point = Point(0,1)
  val opponent: Color = Black
  val base: Char = '\u25A0'
}

case object Black extends Color {
  val name: String = "Black"
  val symbol: Char = 'B'
  val direction: Point = Point(0,-1)
  val opponent: Color = White
  val base: Char = '\u25A1'
}