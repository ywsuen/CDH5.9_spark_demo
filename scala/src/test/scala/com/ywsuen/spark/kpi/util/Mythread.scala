package com.ywsuen.spark.kpi.util

class Mythread extends Runnable{
  override def run(): Unit = {
    (0 to 1000).foreach{i=>
      val s = raw"(\d+)".r.findFirstMatchIn("1111111aaaa").get.group(1)
      println(i+":"+s)
    }
  }
}

object Mythread{
  def main(args: Array[String]) {
    (0 to 1000).foreach{i=>
      val thread = new Thread(new Mythread)
      thread.start
    }
  }
}