package chess.board

sealed trait Color {
  val name: String
  val symbol: Char
  val direction: Point
}

case object White extends Color {
  val name: String = "White"
  val symbol: Char = 'W'
  val direction: Point = Point(0,1)
}

case object Black extends Color {
  val name: String = "Black"
  val symbol: Char = 'B'
  val direction: Point = Point(0,-1)
}