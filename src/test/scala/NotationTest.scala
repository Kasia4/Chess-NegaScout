import chess._
import javax.management.Notification

class NotationTest extends org.scalatest.FunSuite {
  test("Column number is counted properly") {
    assert(Notation.columnValue(symbol = 'A').get == 0)
    assert(Notation.columnValue(symbol = 'H').get == 7)
  }

  test("Improper column causes failure value") {
    assert(Notation.columnValue(symbol = 'X').isFailure)
  }

  test("Row number is counted properly") {
    assert(Notation.rowValue(symbol = '1').get == 0)
    assert(Notation.rowValue(symbol = '8').get == 7)
  }

  test("Improper row causes failure value") {
    assert(Notation.rowValue(symbol = 'G').isFailure)
    assert(Notation.rowValue(symbol = '!').isFailure)
    assert(Notation.rowValue(symbol = '9').isFailure)
    assert(Notation.rowValue(symbol = '0').isFailure)
  }

  test("Field is parsing to proper Point") {
    assert(Notation.parseField(in = "B4").get == Point(1,3))
  }

  test("Field with improper length causes failure value") {
    assert(Notation.parseField(in = "B12").isFailure)
  }

  test("Field with improper values causes failure value") {
    assert(Notation.parseField(in = "K1").isFailure)
  }

}
