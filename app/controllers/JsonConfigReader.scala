package controllers

import java.nio.file.{Path, Paths}

import controllers.Control._
import controllers.ListOfRootJsonProtocol._
import spray.json._

import scala.io.Source


class JsonConfigReader(path: String) extends IConfigReader {
  var listOfElementModels = new ListOfElementModels[ElementModel]("", url="", List[ElementModel]())
  var listOfRootModels =  new ListOfRootModels[ListOfElementModels[ElementModel]](List(new ListOfElementModels[ElementModel]("", url="", List[ElementModel]())))

  override def loadConfig(): Option[String] = {
    val relative: Path = Paths.get(path)
    try {
      val configFileContents = using(Source.fromFile(relative.toUri)) { source => {
        source.mkString
      }}
      Some(configFileContents)
    } catch {
      case e: Exception => None
    }
  }

  override def getConfig(): Unit = {
    loadConfig() match {
      case Some(configFileContents) =>
        val jsonAst = configFileContents.parseJson
        listOfRootModels = jsonAst.convertTo[ListOfRootModels[ListOfElementModels[ElementModel]]]
      case None => println("could not read the file")
    }
  }
  def Configurations: ListOfElementModels[ElementModel] = listOfElementModels

  def MultipleConfigurations: ListOfRootModels[ListOfElementModels[ElementModel]] = listOfRootModels
}
