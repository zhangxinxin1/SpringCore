package app

import java.io.PrintWriter

import scala.io.Source
import scala.util.parsing.json.JSON

/**
  * Created by zxx on 18-4-2.
  */
object DataAnalysis {

  def main(args: Array[String]): Unit = {

    var file = Source.fromFile("./newFile.txt")
    val out = new PrintWriter("./res.txt")
    var idx = 0
    var idxMap = collection.mutable.Map[String, Int]()

    def parse(prefix: String, m: Map[String, Any], res: collection.mutable.Map[String, Any]) {

      m.foreach { case (k, v) =>

        if (v.isInstanceOf[Map[String, Any]]) {
          parse(k + prefix, v.asInstanceOf[Map[String, Any]], res)
        } else if (v.isInstanceOf[List[Any]]) {
          val list = v.asInstanceOf[List[Any]]
          if (list.length > 0 && list.head.isInstanceOf[Map[String, Any]]) {
            for (vv <- list)
              parse(k + prefix, vv.asInstanceOf[Map[String, Any]], res)

          } else
            res(prefix + k) = v
        } else {
          res(prefix + k) = v
        }
      }

    }
    var ystr = ""
    for (line <- file.getLines) {
      var s = line.split("【")(1).split("】")(0)
      val bs = JSON.parseFull(s)
      val m: Map[String, String] = bs match {
        // Matches if jsonStr is valid JSON and represents a Map of Strings to Any
        case Some(map: Map[String, String]) => map
        case None => null
      }

      val res = collection.mutable.Map[String, Any]()
      parse("", m, res)
      //try to parse info
      res.foreach {
        case (k, v) =>
          if (!idxMap.contains(k) &&
            (!v.isInstanceOf[List[Any]] || v.asInstanceOf[List[Any]].length > 0)) {
            idxMap.put(k, idx)
            println("res---->:" + k + "----vlaues------>:" + idx)
            if (k.contains("Content")) {
              var str = k.split("Content")
              println("str0-----:" + str(1) + "-------idx:" + idx)
              ystr = ystr + "," + str(1)
            } else {
              var strs = k.split("Info")
              println("str1---:" + strs(1) + "-------:idx" + idx)
              ystr = ystr + "," + strs(1)
            }

            idx += 1
          }
      }

    }


    //写入表头字段
    out.write(ystr.substring(1, ystr.length - 1) + "\n")


    file.close

    file = Source.fromFile("/home/weichuang/cs/gzPro/qoros_trip_trajectory_2postgis/newFile.txt")
    for (line <- file.getLines) {
      var s = line.split("【")(1).split("】")(0)

      val bs = JSON.parseFull(s)
      val m: Map[String, String] = bs match {
        // Matches if jsonStr is valid JSON and represents a Map of Strings to Any
        case Some(map: Map[String, String]) => map
        case None => null
      }
      val buf = Array.fill[Any](idxMap.size)("")
      val res = collection.mutable.Map[String, Any]()
      parse("", m, res)
      res.foreach {
        case (k, v) =>
          if (v.isInstanceOf[Map[String, String]]) {
            val value = v.asInstanceOf[Map[String, String]]
            buf(idxMap(k)) = value.values.head
          } else if (idxMap.contains(k)) {
            buf(idxMap(k)) = "\"" + v + "\""
          }
      }
      print(buf.mkString(",") + "\n")
      out.write(buf.mkString(",") + "\n")
    }
    out.close
  }




}

