package utils.readers

import java.nio.file.{NoSuchFileException, Path, Paths}

import models.{HTMLElementModel, ListOfScraperRecipes, ScraperRecipe}
import spray.json._
import utils.Control._
import utils.readers.ListOfScraperRecipesJsonProtocol._

import scala.io.Source


class JsonConfigReader(path: String) extends IConfigReader {
  var listOfElementModels = new ScraperRecipe[HTMLElementModel]("", url="", List[HTMLElementModel]())
  var listOfRootModels =  new ListOfScraperRecipes[ScraperRecipe[HTMLElementModel]](List(new ScraperRecipe[HTMLElementModel]("", url="", List[HTMLElementModel]())))

  override def loadConfig(): Option[String] = {
    val relative: Path = Paths.get(path)
    try {
      val configFileContents = using(Source.fromFile(relative.toUri)) { source => {
        source.mkString
      }}
      Some(configFileContents)
    } catch {
      case e: NoSuchFileException => None
    }
  }

  override def getConfig(): Unit = {
    loadConfig() match {
      case Some(configFileContents) =>
        val jsonAst = configFileContents.parseJson
        listOfRootModels = jsonAst.convertTo[ListOfScraperRecipes[ScraperRecipe[HTMLElementModel]]]
      case None => println("could not read the file")
    }
  }
  def Configurations: ScraperRecipe[HTMLElementModel] = listOfElementModels

  def MultipleConfigurations: ListOfScraperRecipes[ScraperRecipe[HTMLElementModel]] = listOfRootModels
}
