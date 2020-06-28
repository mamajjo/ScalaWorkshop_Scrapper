package controllers

import net.ruippeixotog.scalascraper.browser.{Browser, JsoupBrowser}
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.Document

class Downloader (config: ListOfElementModels[ElementModel]) {
  val browser: Browser = JsoupBrowser()
  val _htmlDocument: browser.DocumentType = browser.get(config.url)

  def htmlString: Document = _htmlDocument

  def printScrappedProducts(): Unit = {
    config.list.foreach(elem => {
      elem.cssTag match {
        case "class" =>
          elem.dataType
          val tagValue = "." + elem.cssValue
          println(htmlString >> text(tagValue))
        case _ => new Exception("Blad")
      }
    })
  }

}

