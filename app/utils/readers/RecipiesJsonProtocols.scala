package utils.readers

import models.{HTMLElementModel, ListOfScraperRecipes, ScraperRecipe}
import spray.json.{DefaultJsonProtocol, DeserializationException, JsArray, JsObject, JsString, JsValue, RootJsonFormat}

import scala.collection.mutable.ListBuffer

object HTMLElementModelJsonProtocol extends DefaultJsonProtocol {

  implicit object HTMLElementModelJsonFormat extends RootJsonFormat[HTMLElementModel] {

    override def read(json: JsValue): HTMLElementModel = {
      json.asJsObject.getFields("cssTag", "cssValue", "dataType") match {
        case Seq(JsString(cssTag), JsString(cssValue), JsString(dataType)) =>
          HTMLElementModel(cssTag, cssValue, dataType)
        case _ => throw DeserializationException("ElementModel Expected")
      }
    }

    override def write(elem: HTMLElementModel): JsValue = JsObject(
      "cssTag" -> JsString(elem.cssTag),
      "cssValue" -> JsString(elem.cssValue),
      "dataType" -> JsString(elem.dataType)
    )
  }

}

object ScraperRecipeJsonProtocol extends DefaultJsonProtocol {

  implicit object ScraperRecipeJsonFormat extends RootJsonFormat[ScraperRecipe[HTMLElementModel]] {
    override def read(json: JsValue): ScraperRecipe[HTMLElementModel] = json match {
      case jsObj: JsObject =>
        jsObj.getFields("name", "url", "tags") match {
          case Seq(JsString(name), JsString(url), JsArray(tags)) =>
            val tagsForHTMLElem = tags.map(tag => HTMLElementModelJsonProtocol.HTMLElementModelJsonFormat.read(tag)).toList
            new ScraperRecipe[HTMLElementModel](name, url, tagsForHTMLElem)
          case _ => throw DeserializationException("Invalid ScraperRecipe")
        }
      case _ => throw DeserializationException("Invalid ScraperRecipe")
    }

    override def write(obj: ScraperRecipe[HTMLElementModel]): JsValue = JsObject(
      "name" -> JsString(obj.name),
      "url" -> JsString(obj.url),
      "tags" -> JsArray(obj.list.map(elem => HTMLElementModelJsonProtocol.HTMLElementModelJsonFormat.write(elem)).toVector)
    )
  }

}
object ListOfScraperRecipesJsonProtocol extends DefaultJsonProtocol {

  implicit object ListOfScraperRecipesJsonFormat extends RootJsonFormat[ListOfScraperRecipes[ScraperRecipe[HTMLElementModel]]] {
    override def read(json: JsValue): ListOfScraperRecipes[ScraperRecipe[HTMLElementModel]] = json match {
      case jsObj: JsObject =>
        val oneRecipe: List[ScraperRecipe[HTMLElementModel]] = List[ScraperRecipe[HTMLElementModel]](ScraperRecipeJsonProtocol.ScraperRecipeJsonFormat.read(jsObj))
        new ListOfScraperRecipes[ScraperRecipe[HTMLElementModel]](oneRecipe)
      case jsArr: JsArray =>
        val listOfRecipes = new ListBuffer[ScraperRecipe[HTMLElementModel]]
        jsArr.elements.foreach(jsObj => listOfRecipes.append(ScraperRecipeJsonProtocol.ScraperRecipeJsonFormat.read(jsObj)) )
        new ListOfScraperRecipes[ScraperRecipe[HTMLElementModel]](listOfRecipes.toList)
      case _ => throw DeserializationException("JsObject or JsArray Expected")
        }

    override def write(obj: ListOfScraperRecipes[ScraperRecipe[HTMLElementModel]]): JsValue = JsArray(
      obj.list.map(objToSerialize => ScraperRecipeJsonProtocol.ScraperRecipeJsonFormat.write(objToSerialize)).toVector
    )
  }
}