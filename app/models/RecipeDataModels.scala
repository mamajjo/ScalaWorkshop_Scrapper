package models

import net.ruippeixotog.scalascraper.model.Document
import spray.json.DeserializationException
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._

import scala.collection.mutable.ListBuffer

case class HTMLElementModel(cssTag: String, cssValue: String, dataType: String)

case class ScraperRecipe[A](name: String, url: String, list: List[A])

case class ListOfScraperRecipes[A](list: List[A])

class ScrapedDataModel(val recipeList: ScraperRecipe[HTMLElementModel], val downloadedDocument: Document) {
  def getPrettyStringMap: List[(String, String)] = {
    val list = ListBuffer[(String, String)]()
    recipeList.list.foreach(tags => {
      tags match {
        case HTMLElementModel(cssTag, cssValue, dataType) if cssTag=="class" && dataType == "text" =>
          val tagValue = "." + tags.cssValue
          val extracted = (downloadedDocument >> texts(tagValue)).toList
          for (i <- extracted.indices) {
            if (i == list.length)
              list.append((i+1).toString -> extracted(i))
            else if (i < list.length )
              list.update(i, (i+1).toString -> (list(i)._2 + "|" + extracted(i)))
          }
        case HTMLElementModel(cssTag, cssValue, dataType) if cssTag=="span" && dataType == "text" =>
          val tagValue = "." + tags.cssValue
          val extracted = (downloadedDocument >> texts(tagValue)).toList
          for (i <- extracted.indices) {
            if (i == list.length)
              list.append((i+1).toString -> extracted(i))
            else if (i < list.length )
              list.update(i, (i+1).toString -> (list(i)._2 + "|" + extracted(i)))
          }
        case _ => throw DeserializationException("Unmet cssTag or dataType to extract in config JSON")
      }
    })
    list.toList
  }
}