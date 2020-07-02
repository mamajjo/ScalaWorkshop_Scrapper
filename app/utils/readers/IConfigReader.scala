package utils.readers

trait IConfigReader {
  def loadConfig():Option[String]
  def getConfig():Unit
}
