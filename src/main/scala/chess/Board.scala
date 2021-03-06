package chess

case class Board (pieces: Map[Point, Piece] = Map(), rect: Rectangle = Rectangle(Point(8,8))) {

  /**
    * Add piece at given position
    *
    * @param pos   field position
    * @param piece adding piece
    * @return new board with added piece or none if error
    */
  def add(pos: Point, piece: Piece): Option[Board] = {
    if (isEmptyAt(pos))
      Some(Board(pieces + (pos -> piece)))
    else None
  }

  /**
    * Add pieces map to board
    *
    * @param new_pieces adding pieces map
    * @return new board with added pieces or none if error
    */
  def add(new_pieces: Map[Point, Piece]): Option[Board] = {
    if (!new_pieces.keys.forall(isEmptyAt)) None
    else Some(Board(new_pieces ++ pieces))
  }

  /**
    * Replace piece at given position with given piece
    *
    * @param pos   field position
    * @param piece new piece
    * @return new board with replace piece or none if error
    */
  def replace(pos: Point, piece: Piece): Option[Board] = {
    if (rect.contains(pos))
      Some(Board(pieces + (pos -> piece)))
    else None
  }

  /**
    * Create board with undone move
    *
    * @param move_log contains Move and option with captured piece
    * @return new board with undone move
    */
  def undo(move_log: MoveLog): Option[Board] = {
    val move = move_log.move
    if (isEmptyAt(move.to) || isOccupiedAt(move.from)) None
    else if (move_log.captured.isEmpty)
      Some(Board(pieces + (move.from -> getAt(move.to).get) - move_log.move.to))
    else
      Some(Board(pieces + (move.from -> getAt(move.to).get, move.to -> move_log.captured.get)))
  }

  /**
    * Remove piece at given position
    *
    * @param pos field position
    * @return Board without piece at given position
    */
  def remove(pos: Point) = Board(pieces - pos)

  /**
    * Checks if given position is empty
    *
    * @param pos field position
    * @return
    */
  def isEmptyAt(pos: Point): Boolean = pieces.get(pos).isEmpty && rect.contains(pos)

  /**
    * Checks if given position is occupied
    *
    * @param pos field position
    * @return
    */
  def isOccupiedAt(pos: Point): Boolean = pieces.get(pos).isDefined

  /**
    *
    * @param pos field position
    * @return Option with piece from given field or None if field was empty
    */
  def getAt(pos: Point): Option[Piece] = pieces.get(pos)

  /**
    *
    * @param pos_opt field position's option
    * @return Option with piece from given field or None if field was empty or position was None
    */
  def getAt(pos_opt: Option[Point]): Option[Piece] = if (pos_opt.isEmpty) None else getAt(pos_opt.get)

  /**
    * Finds map of all pieces of given color
    *
    * @param color pieces' color
    * @return map with positions and pieces
    */
  def ofColor(color: Color): Map[Point, Piece] = pieces.filter(p => p._2.color == color)

  /**
    * Finds map of all pieces of given type
    *
    * @param pieceType pieces' type
    * @return map with positions and pieces
    */
  def ofType(pieceType: PieceType): Map[Point, Piece] = pieces.filter(p => p._2.ptype == pieceType)

  /**
    * Apply move and checks if move is according to rules
    *
    * @param move  applying move
    * @param color player color
    * @return Pair with new board and log of executed move or None if move was illegal"
    */
  def applyLegalMove(move: Move, color: Color): Option[(Board, MoveLog)] = {
    if (getAt(move.from).isEmpty || getAt(move.from).get.color != color) None
    else {
      if (possibleShifts(move.from).contains(move.to)) {
        val after = applyShift(move).get
        if (after.checkOf(color)) None
        else Some(after, MoveLog(move))
      }
      else if (possibleCaptures(move.from).contains(move.to)) {
        val after = applyCapture(move).get
        if (after.checkOf(color)) None
        else Some(after, MoveLog(move, getAt(move.to)))
      }
      else None
    }
  }


  /**
    * Apply move from field with piece to empty field
    *
    * @param move applying move
    * @return Board with applied move or None if error
    */
  def applyShift(move: Move): Option[Board] = {
    if (isOccupiedAt(move.from) && isEmptyAt(move.to)) {
      val moved = getAt(move.from).get
      this.remove(move.from).add(move.to, moved)
    }
    else None
  }

  /**
    * Apply move from field with piece to field with opponent piece
    *
    * @param move field position
    * @return Board with applied capture or None if error
    */
  def applyCapture(move: Move): Option[Board] = {
    if (isOccupiedAt(move.from) && isOccupiedAt(move.to)) {
      if (getAt(move.from).get.color == getAt(move.to).get.color)
        None
      else {
        val moved = getAt(move.from).get
        this.remove(move.from).replace(move.to, moved)
      }
    }
    else None
  }

  def applyMove(move: Move): Option[Board] = {
    if (isOccupiedAt(move.to)) applyCapture(move)
    else applyShift(move)
  }
  /**
    * Finds moves can be executed from given field
    *
    * @param pos field position
    * @return List of possible target fields
    */
  def possibleShifts(pos: Point): List[Point] = {
    if (isEmptyAt(pos)) List.empty[Point]
    else {
      val piece = getAt(pos).get
      piece.ptype match {
        case Pawn =>
          val dist = if (piece.moved) 1 else 2
          val dir = piece.color.direction
          pos.path(dir, dist).toList.span(isEmptyAt)._1
        case Knight => pos.lNeighbors()
        case King => pos.neighbors()
        case dir_piece: DirectionalType => scanDirs(dir_piece.dirs, pos)
      }
    }.filter(isEmptyAt)
  }

  /**
    * Finds captures can be executed from given field
    *
    * @param pos field position
    * @return List of possible target fields
    */
  def possibleCaptures(pos: Point): List[Point] = {
    if (isEmptyAt(pos)) List.empty[Point]
    else {
      val piece = getAt(pos).get
      piece.ptype match {
        case Pawn => {
          val dir = piece.color.direction
          List(Right.vec + dir + pos, Left.vec + dir + pos)
        }.filter(isOpponent(_, piece.color))
        case Knight => pos.lNeighbors().filter(isOpponent(_, piece.color))
        case King => pos.neighbors().filter(isOpponent(_, piece.color))
        case dir_piece: DirectionalType => scanOpponent(dir_piece.dirs, pos, piece.color)
      }
    }
  }

  /**
    * Finds moves and captures can be executed from given field
    *
    * @param pos field position
    * @return List of possible target fields
    */
  def allPossibleMoves(pos: Point): List[Point] = {
    val moves = possibleShifts(pos) ::: possibleCaptures(pos)
    if (moves.nonEmpty) {
      val color = getAt(pos).get.color
      moves.map(Move(pos, _)).filter(!checkOfAfterMove(color, _)).map(_.to)
    } else List.empty[Point]

  }

  def scanDirs(dirs: List[Direction], start: Point): List[Point] = {
    (for (dir <- dirs) yield
      rect.pathToBorder(start, dir).span(isEmptyAt)._1).flatten
  }

  /**
    * Finds fields with opponent's pieces at paths from start field in given directions
    *
    * @param dirs  searching directions list
    * @param start start field position
    * @param color player color
    * @return list of found fields
    */
  def scanOpponent(dirs: List[Direction], start: Point, color: Color): List[Point] = {
    val opts = for (dir <- dirs) yield {
      rect.findInDirection(start, dir, isOccupiedAt)
    }
    for (opt <- opts if opt.isDefined && isOpponent(opt.get, color)) yield opt.get

  }

  /**
    * Check if given field contains opponent piece
    *
    * @param pos   field position
    * @param color player color
    * @return True if given field contains
    */
  def isOpponent(pos: Point, color: Color): Boolean = isOccupiedAt(pos) && getAt(pos).get.color == color.opponent


  override def toString: String = {
    (for (y <- 0 to 7) yield
      (for (x <- 0 to 7) yield {
        val opt = getAt(Point(x, y))
        if (opt.isEmpty) ' '
        else opt.get.color match
        {
          case White => opt.get.ptype.symbol.toUpper
          case Black => opt.get.ptype.symbol.toLower
        }
      }).mkString + '\n').mkString
  }

  lazy val king_position: Map[Color, Point] = {
    pieces.collect {
      case (pos, Piece(King, color, _)) => color -> pos
    }
  }

  /**
    *
    * @param color king's color
    * @return Given color king's position
    */
  def kingPosition(color: Color) = king_position(color)

  /**
    * Finds all captures that can be executed by given player
    *
    * @param color player's color
    * @return
    */
  def possibleCapturesOf(color: Color): List[Move] = {
    (for (piece <- ofColor(color)) yield {
      possibleCaptures(piece._1).map(Move(piece._1, _))
    }).flatten.toList
  }

  /**
    * Finds all moves that can be executed by given player
    *
    * @param color player's color
    * @return
    */
  def possibleShiftsOf(color: Color): List[Move] = {
    (for (piece <- ofColor(color)) yield {
      possibleShifts(piece._1).map(Move(piece._1, _))
    }).flatten.toList
  }

  /**
    * Finds all captures that can be executed from given position
    *
    * @param pos field position
    * @return
    */
  def possibleCapturesOf(pos: Point): List[Move] = {
    val target_piece = getAt(pos)
    if (target_piece.isEmpty) List.empty[Move]
    else {
      possibleCapturesOf(target_piece.get.color.opponent)
        .filter(_.to == pos)
    }
  }

  def allPossibleMovesOf(color: Color): List[Move] = {
    (for (piece <- ofColor(color)) yield {
      allPossibleMoves(piece._1).map(Move(piece._1, _))
    }).flatten.toList
  }

  /**
    * Checks if piece on given field can be captured
    *
    * @param pos field position
    * @return
    */
  def canBeCaptured(pos: Point): Boolean = {
    if (isEmptyAt(pos)) false
    else possibleCapturesOf(getAt(pos).get.color.opponent).exists(_.to == pos)
  }

  def movesTo(pos: Point, color: Color): List[Move] = {
    possibleShiftsOf(color).filter(_.to == pos)
  }


  def checkOfAfterMove(color: Color, move: Move): Boolean = {
    val after = applyMove(move)
    if (after.isEmpty) false
    else after.get.checkOf(color)
  }
  /**
    * Checks if given player have check
    *
    * @param color player's color
    * @return
    */
  def checkOf(color: Color): Boolean = canBeCaptured(kingPosition(color))


  def kingCanEscape(color: Color): Boolean = {
    val kingpos = kingPosition(color)
    possibleShifts(kingpos)
      .map(Move(kingpos, _))
      .map(applyShift)
      .map(_.get)
      .exists(!_.checkOf(color))
  }

  def kingAttackerCanBeCaptured(color: Color, attackers_pos: List[Point]): Boolean = {
    attackers_pos
      .flatMap(possibleCapturesOf)
      .map(applyCapture)
      .map(_.get)
      .exists(!_.checkOf(color))
  }

  def kingAttackerCanBeBlocked(color: Color, attackers_pos: List[Point]): Boolean = {
    if (attackers_pos.size > 1) true
    else attackers_pos.head
      .pointsBetween(kingPosition(color))
      .flatMap(movesTo(_, color))
      .map(applyShift)
      .map(_.get).exists(!_.checkOf(color))
  }

  /**
    * Check if given player have checkmate
    *
    * @param color player's color
    * @return
    */
  def checkmateOf(color: Color): Boolean = {
    if (!checkOf(color)) false
    else {
      val kingpos = kingPosition(color)
      if (kingCanEscape(color)) false
      else {
        val attackers_pos = possibleCapturesOf(kingpos)
          .map(_.from)
        if (kingAttackerCanBeCaptured(color, attackers_pos)) false
        else !kingAttackerCanBeBlocked(color, attackers_pos)
      }
    }
  }
}

object Board {
  val startBoard: Board = {
    val types = IndexedSeq(Rook, Knight, Bishop, Queen, King, Bishop, Knight, Rook)
    val w_pawn = Piece(Pawn, White, moved = false)
    val b_pawn = Piece(Pawn, Black, moved = false)
    val row = Point(-1, 0).path(Right.vec, length = 8).toIndexedSeq
    val pieces =
      (for (i <- 0 to 7) yield List(
        row(i) -> Piece(types(i), White),
        row(i) + Point(0, 1) -> w_pawn,
        row(i) + Point(0, 6) -> b_pawn,
        row(i) + Point(0, 7) -> Piece(types(i), Black)
      )).flatten.toMap[Point, Piece]
    Board(pieces)
  }
}