package controllers;

import akka.japi.Pair;
import models.ScrapedDataModel;
import play.mvc.*;
import scala.Tuple2;
import scala.collection.immutable.List;
import utils.Downloader;
import utils.readers.JsonConfigReader;

import java.util.ArrayList;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        return ok(views.html.index.render());
    }

    public Result explore() {
        return ok(views.html.explore.render());
    }

    public Result tutorial() {
        return ok(views.html.tutorial.render());
    }

    public Result hello() {
        return ok(views.html.hello.render());
    }

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
