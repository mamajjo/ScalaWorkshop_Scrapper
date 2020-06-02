package controllers

trait IConfigReader {
  def loadConfig():Option[String]
  def getConfig():Unit
}
