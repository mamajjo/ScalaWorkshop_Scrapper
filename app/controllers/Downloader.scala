package controllers

import net.ruippeixotog.scalascraper.browser.{Browser, JsoupBrowser}
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.Document

class Downloader (config: ListOfScraperRecipes[ScraperRecipe[HTMLElementModel]]) {
  val browser: Browser = JsoupBrowser()
  val _htmlDocument: List[browser.DocumentType] = config.list.map(configuration => browser.get(configuration.url))

  def htmlString: List[Document] = _htmlDocument

  def printScrappedProducts(): Unit = {
    config.list.foreach(elem => {
      elem.list.foreach(e => {
        e.cssTag match {
          case "class" =>
            val tagValue = "." + e.cssValue
            println(htmlString >> text(tagValue))
          case _ => new Exception("Blad")
        }
      })
    })
  }

}

