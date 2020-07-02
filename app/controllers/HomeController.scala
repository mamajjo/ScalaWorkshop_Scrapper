package controllers

import java.util

import javax.inject.{Inject, Singleton}
import models.ScrapedDataModel
import play.api.mvc._
import utils.Downloader
import utils.readers.JsonConfigReader

import scala.collection.immutable.List
import scala.collection.mutable.ListBuffer;

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def scraper(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val configReader: JsonConfigReader = new JsonConfigReader("nike_scrapper.json")
    configReader.getConfig()
    val scrappedData: List[ScrapedDataModel] = Downloader.getHtmlDocumentsList(configReader.MultipleConfigurations)
    val scrapedString: ListBuffer[(String, String)] = ListBuffer[(String, String)]()
    for (i <- scrappedData.indices) {
      val temp: List[(String, String)] = scrappedData.apply(i).getPrettyStringMap
      for (j <- temp.indices) {
        scrapedString.append(temp.apply(j))
      }
    }
    Ok(views.html.scraperResults.render(scrapedString.toList))
  }
}