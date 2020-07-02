package utils.readrers

import models.{HTMLElementModel, ListOfScraperRecipes, ScraperRecipe}
import org.scalatestplus.play.PlaySpec
import spray.json.JsonParser.ParsingException
import spray.json._
import utils.readers.ListOfScraperRecipesJsonProtocol._


class JsonReaderExceptionTests extends PlaySpec{

  val jsonHTMLElementMockInvalid: String =
    """
      |[
      |  {
      |  "name": "air_force",
      |  "url": "xxx",
      |  "tags": [
      |    {
      |      "cssTag": "class",
      |      "cssValue": "product-card__title",
      |      "dataType": "text"
      |    },
      |    {
      |      "cssTag": "class"
      |    }
      |  ]
      |  }
      |]""".stripMargin

  val jsonScraperRecipeMockInvalid: String =
    """
      |[
      |  {
      | "url": "xxx",
      |  "tags": [
      |    {
      |      "cssTag": "class",
      |      "cssValue": "product-card__title",
      |      "dataType": "text"
      |    },
      |    {
      |      "cssTag": "class",
      |      "cssValue": "product-card__price",
      |      "dataType": "text"
      |    }
      |  ]
      |  }
      |]""".stripMargin
  val jsonInvalid: String =
    """
      |[
      |  {
      | "url": xxx",
      |  "tags": [
      |    {
      |      "cssTag": "class",
      |      "cssValue": "product-card__title",
      |      "dataType": "text"
      |    },
      |    {
      |      "cssTag": "class",
      |      "cssValue": "product-card__price",
      |      "dataType": "text"
      |    }
      |  ]
      |  }
      |]""".stripMargin

  "Throwing exception when json is invalid" must {
    "throw DeserializationException -> HTMLElement invalid" in {
      val jsonAstInvalid: JsValue = jsonHTMLElementMockInvalid.parseJson
      a[DeserializationException] must be thrownBy{
        jsonAstInvalid.convertTo[ListOfScraperRecipes[ScraperRecipe[HTMLElementModel]]]
      }
    }
    "throw DeserializationException -> ScraperRecipe invalid " in {
      val jsonAstInvalid: JsValue = jsonScraperRecipeMockInvalid.parseJson
      a[DeserializationException] must be thrownBy{
        jsonAstInvalid.convertTo[ListOfScraperRecipes[ScraperRecipe[HTMLElementModel]]]
      }
    }
    "throw DeserializationException -> Json invalid" in {
      a[ParsingException] must be thrownBy{
        jsonInvalid.parseJson
      }
    }
  }
}
