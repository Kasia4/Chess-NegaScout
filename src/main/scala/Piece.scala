package chess.board

sealed trait PieceType {
  val name: String
  val symbol: Char
  val promotable: Boolean
}

case object Pawn extends PieceType {
  val name = "Pawn"
  val symbol = 'p'
  val promotable = false
}

case object Knight extends PieceType {
  val name = "Knight"
  val symbol = 'N'
  val promotable = true
}

case object Bishop extends PieceType {
  val name = "Bishop"
  val symbol = 'B'
  val promotable = true
  val dirs: List[Direction] = Direction.diagonalDir
}

case object Rook extends PieceType {
  val name = "Rook"
  val symbol = 'R'
  val promotable = true
  val dirs: List[Direction] = Direction.lineDir
}

case object Queen extends PieceType {
  val name = "Queen"
  val symbol = 'Q'
  val promotable = true
  val dirs: List[Direction] = Direction.allDir
}

case object King extends PieceType {
  val name = "King"
  val symbol = 'K'
  val promotable = false
}

case class Piece(ptype: PieceType, color: Color, moved: Boolean = true) {
  def canMove(pos: Point, to: Point): Boolean = ptype match {
    case Pawn => {
      val maxDist: Int = if (moved) 1 else 2
      if (color == White) pos.below(to) && pos.distX(to) == 0 && pos.distY(to) <= maxDist
      else pos.above(to) && pos.distX(to) == 0 && pos.distY(to) <= maxDist
    }
    case Knight => pos.isLNeighbor(to)
    case Bishop => pos.onSameDiagonal(to)
    case Rook => pos.onSameLine(to)
    case Queen => pos.onSameDiagonal(to) || pos.onSameLine(to)
    case King => pos.adjoin(to)
  }

  def canAttack(pos: Point, to: Point): Boolean = ptype match {
    case Pawn => {
      if (color == White) pos.below(to) && pos.distX(to) == 1 && pos.distY(to) == 1
      else pos.above(to) && pos.distX(to) == 1 && pos.distY(to) == 1
    }
    case _ => canMove(pos, to)
  }
}