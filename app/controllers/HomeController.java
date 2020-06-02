package controllers;

import play.api.Configuration;
import play.mvc.*;

import javax.inject.Inject;

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

    public Result airForce() {
        var downloader = new Downloader( "https://www.nike.com/pl/w/mezczyzni-air-force-1-buty-5sj3yznik1zy7ok");
        downloader.printConfig();
        var configReader = new JsonConfigReader("nike_scrapper.json");
        configReader.getConfig();
        return ok(downloader.htmlString().title());
    }
}
