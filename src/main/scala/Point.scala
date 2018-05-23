case class Point(x: Int, y: Int)

def +(p1: Point, p2: Point): Point = {
  Point(p1.x + p2.x, p1.y + p2.y)
}

def -(p1: Point, p2: Point): Point = {
  Point(p1.x + p2.x, p1.y + p2.y)
}

