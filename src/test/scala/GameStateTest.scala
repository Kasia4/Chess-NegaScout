import chess.board._

class GameStateTest extends org.scalatest.FunSuite {
  test("When move is legal, applyMove returns new GameState") {
    assert(GameState().applyMove(Move(Point(1,1), Point(1,3))).isDefined)
  }
  test("Current player is changed after applyMove") {
    assert(GameState().applyMove(Move(Point(1,1), Point(1,3))).get.current_player == Black)
  }
  test("When move is illegal, applyMove returns None") {
    assert(GameState().applyMove(Move(Point(1,1), Point(1,4))).isEmpty)
  }
}
