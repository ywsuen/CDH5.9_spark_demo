package com.ywsuen.spark.kpi.util

import com.ywsuen.debugger.Debugger

class Configuration[T](val confFilePath: String)(implicit m: Manifest[T]) extends Debugger{
  private var jsonStrCache = ""
  private var resCache: Option[T] = None

  // 创建时, 必须要求可以创建成功, 否则抛出错误
  jsonStrCache = FileReaderUtil.readFile(confFilePath)
  resCache = Some(JsonParserUtil.parse2CaseClass[T](jsonStrCache).left.get)
  
  def getCfg: Option[T] = {
    val jsonStr = try{
      FileReaderUtil.readFile(confFilePath)
    }catch{
      case e: Exception =>
        writeError(e.toString)
        return None
    }

    val res = if(jsonStrCache == jsonStr){
      resCache
    }else{
      JsonParserUtil.parse2CaseClass[T](jsonStr) match {
        case Left(obj) =>
          jsonStrCache = jsonStr
          resCache = Some(obj)
          Some(obj)
        case Right(e) =>
          writeError(e.toString)
          return None
      }
    }
    res
  }

  private def apply() = {}
}

object Configuration{
  def createCfg[T](filePath: String)(implicit m: Manifest[T]): Configuration[T] = {
    new Configuration[T](filePath)
  }
}