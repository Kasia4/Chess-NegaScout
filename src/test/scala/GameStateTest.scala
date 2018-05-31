import chess._

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

  test("After move, history contains executed move") {
    val move = Move(Point(1,1), Point(1,3))
    assert(
      GameState()
        .applyMove(move).get.history.contains(MoveLog(move))
    )
  }

  test("After undo board equals original board") {
    val move = Move(Point(1,1), Point(1,3))
    val original = GameState()
    assert(
      original
        .applyMove(move).get
        .undo().get == original
    )
  }
}
