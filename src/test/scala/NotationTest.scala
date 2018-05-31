import chess._

class NotationTest extends org.scalatest.FunSuite {
  test("Column number is counted properly") {
    assert(Notation.columnValue(symbol = 'A').get == 0)
    assert(Notation.columnValue(symbol = 'H').get == 7)
  }

  test("Improper column causes fail value") {
    assert(Notation.columnValue(symbol = 'X').isFailure)
  }


}
