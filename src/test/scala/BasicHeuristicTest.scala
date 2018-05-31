import ai.BasicHeuristic
import chess._

class BasicHeuristicTest extends org.scalatest.FunSuite {
  test(testName = "When the game begins heuristic is 0 for white player") {
    val initialState = GameState(White)
    assert(BasicHeuristic(White).countStateValue(initialState) == 0 )
  }

  test(testName = "When the game begins heuristic is 0 for black player") {
    val initialState = GameState(Black)
    assert(BasicHeuristic(White).countStateValue(initialState) == 0 )
  }

  test(testName = "When only black player has pieces") {
    val testBoard = Board().add(
      List(
        Point(1, 1) -> Piece(Knight, Black),
        Point(1, 2) -> Piece(Pawn, Black),
        Point(3, 2) -> Piece(Rook, Black)
      ).toMap).get

    val initialState = GameState(White, testBoard)
    assert(BasicHeuristic(White).countStateValue(initialState) == -10 )
  }

  test(testName = "When only white player has pieces") {
    val testBoard = Board().add(
      List(
        Point(1, 1) -> Piece(Knight, White),
        Point(1, 2) -> Piece(Pawn, White),
        Point(3, 2) -> Piece(Rook, White)
      ).toMap).get

    val initialState = GameState(White, testBoard)
    assert(BasicHeuristic(White).countStateValue(initialState) == 10 )
  }

  test(testName = "When both players have pieces and ai is white") {
    val testBoard = Board().add(
      List(
        Point(1, 1) -> Piece(Bishop, White),
        Point(1, 2) -> Piece(Pawn, White),
        Point(3, 2) -> Piece(Rook, Black),
        Point(7, 2) -> Piece(Queen, Black)
      ).toMap).get

    val initialState = GameState(White, testBoard)
    assert(BasicHeuristic(White).countStateValue(initialState) == -8.5 )
  }

  test(testName = "When both players have pieces and ai is black") {
    val testBoard = Board().add(
      List(
        Point(1, 1) -> Piece(Bishop, White),
        Point(1, 2) -> Piece(Pawn, White),
        Point(3, 2) -> Piece(Rook, Black),
        Point(7, 2) -> Piece(Queen, Black)
      ).toMap).get

    val initialState = GameState(White, testBoard)
    assert(BasicHeuristic(Black).countStateValue(initialState) == 8.5 )
  }

}
