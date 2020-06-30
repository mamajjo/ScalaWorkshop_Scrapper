package controllers;

import akka.japi.Pair;
import models.ScrapedDataModel;
import play.mvc.*;
import scala.Tuple2;
import scala.collection.immutable.List;
import utils.Downloader;
import utils.readers.JsonConfigReader;

import java.util.ArrayList;

public class HomeController extends Controller {
    public Result scraper() {
        var configReader = new JsonConfigReader("nike_scrapper.json");
        configReader.getConfig();
        List<ScrapedDataModel> scrappedData = Downloader.getHtmlDocumentsList(configReader.MultipleConfigurations());
        ArrayList<Tuple2<String, String>> scrapedString = new ArrayList<>();
        for (int i = 0; i < scrappedData.length(); i++ ) {
            var temp = scrappedData.apply(i).getPrettyStringMap();
            for (int j = 0; j < temp.length(); j++ ) {
                scrapedString.add(temp.apply(j));
            }
        }
        return ok(views.html.scraperResults.render(scrapedString));
    }
}
