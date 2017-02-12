package com.ywsuen.spark.kpi.util

import com.ywsuen.spark.kpi.bean.ExtractConf

object ConfigurationTest {
  def main(args: Array[String]) {
    configurationTest()
  }

  def configurationTest() = {
    val cfg = new Configuration[ExtractConf]("F:\\yw\\GitHub\\Spark2.0Demo\\scala\\src\\test\\ExtractConf.json")
    println(cfg.getCfg.get)
  }
}
