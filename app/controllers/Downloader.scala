package controllers

import net.ruippeixotog.scalascraper.browser.{Browser, JsoupBrowser}
import net.ruippeixotog.scalascraper.model.Document
import spray.json._

class Downloader (addressURL: String = "") {
  val jsonString =
    """
      {
        "nike-air-force": [{
            "cssTag": "class",
            "cssValue": "product-card__title",
            "dataType": "text"
        }, {
            "cssTag": "class",
            "cssValue": "product-card__price",
            "dataType": "text"
        }]
      }
    """
  val browser: Browser = JsoupBrowser()
  val _htmlDocument: browser.DocumentType = browser.get(addressURL)

  def htmlString: Document = _htmlDocument

  def printConfig() = {
    val jsonAst = jsonString.parseJson
    val string = jsonAst.prettyPrint


  }
}

