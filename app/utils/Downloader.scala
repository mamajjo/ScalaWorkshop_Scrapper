package utils

import models.{HTMLElementModel, ListOfScraperRecipes, ScrapedDataModel, ScraperRecipe}
import net.ruippeixotog.scalascraper.browser.{Browser, JsoupBrowser}

object Downloader {
  val browser: Browser = JsoupBrowser()
  def getHtmlDocumentsList(recipesList: ListOfScraperRecipes[ScraperRecipe[HTMLElementModel]]): List[ScrapedDataModel] = {
    recipesList.list.map(configuration => new ScrapedDataModel(configuration, browser.get(configuration.url)))
  }
}
