import scala.util.control.Breaks._
val ab: ArrayBuffer[Int] = new ArrayBuffer[Int]()
ab.append(0, 12, 23, 20, 10, 0, 0, 20, 30, 0, 35, 20)
var splits = 0
val index0 = new ArrayBuffer[Int]
for (i <- ab.indices) {
  val speed = ab(i)
  if (speed == 0 && ab(i + 1) > 0) {
    breakable {
      for (j <- i until ab.length - 1) {
        if (ab(j) > ab(j + 1)) {
          //当a等于3时跳出breakable块
          println("index0---"+i+"j---" + ab(j))
          splits += 1
          break
        }
      }
    }
  }
}
