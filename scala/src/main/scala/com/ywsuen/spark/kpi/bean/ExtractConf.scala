package com.ywsuen.spark.kpi.bean


case class ExtractConf(baseConf: BaseConf, topics: Map[String, TopicConf])

case class TopicConf(topicName: String, bizName: String, threadNum: Int, sourceType: Map[String, SourceType])

case class SourceType(regs: Map[String, Reg], mis: List[Mi])

case class Reg(field: String, regType : String, reg: String, regIndex: Int)

case class Mi(mi: String, save: Int, interval: Int)

