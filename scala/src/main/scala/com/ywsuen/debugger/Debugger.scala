package com.ywsuen.debugger

trait Debugger {
  def writeDebug(log: String) = println("Debug: "+log)
  def writeInfo(log: String) = println("INFO: "+log)
  def writeWarn(log: String) = println("WATN: "+log)
  def writeError(log: String) = println("Error: "+log)
}
