package utils.readrers

import models.{HTMLElementModel, ListOfScraperRecipes, ScraperRecipe}
import org.scalatestplus.play.PlaySpec
import spray.json._
import utils.readers.ListOfScraperRecipesJsonProtocol._

class JsonReaderTests extends PlaySpec{
  val jsonMockObject: String =
    """
      |{
      |  "name": "air_force",
      |  "url": "xxx",
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
      |}""".stripMargin
  val jsonMockSingleObjectArray: String =
    """
      |[
      |  {
      |  "name": "air_force",
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
  val jsonMockDoubleObjectArray: String =
    """
      [
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
      |      "cssTag": "class",
      |      "cssValue": "product-card__price",
      |      "dataType": "text"
      |    }
      |  ]
      |  },
      |  {
      |    "name": "air_force_1",
      |    "url": "xxx",
      |    "tags": [
      |      {
      |        "cssTag": "class",
      |        "cssValue": "product-card__title",
      |        "dataType": "text"
      |      },
      |      {
      |        "cssTag": "class",
      |        "cssValue": "product-card__price",
      |        "dataType": "text"
      |      },
      |      {
      |        "cssTag": "class",
      |        "cssValue": "product-card__subtitle",
      |        "dataType": "text"
      |      }
      |    ]
      |  }
      |]""".stripMargin

  def pretestingDeclarations_SingleObject(shouldReturnSingle: Boolean): ListOfScraperRecipes[ScraperRecipe[HTMLElementModel]] = {
    val htmlModel = HTMLElementModel("class", "product-card__title","text")
    val htmlModel1 = HTMLElementModel("class", "product-card__price","text")
    val htmlModel2 = HTMLElementModel("class", "product-card__subtitle","text")
    val listOfHtmlModels_firstModel = List[HTMLElementModel](htmlModel,htmlModel1)
    val listOfHtmlModels_secondModel = List[HTMLElementModel](htmlModel,htmlModel1,htmlModel2)
    val scraperModel = ScraperRecipe[HTMLElementModel]("air_force", "xxx", listOfHtmlModels_firstModel)
    val scraperModel_1 = ScraperRecipe[HTMLElementModel]("air_force_1", "xxx", listOfHtmlModels_secondModel)
    val listOfScraperModels = List[ScraperRecipe[HTMLElementModel]](scraperModel, scraperModel_1)
    if (shouldReturnSingle) new ListOfScraperRecipes[ScraperRecipe[HTMLElementModel]](listOfScraperModels.slice(0,1))
    else new ListOfScraperRecipes[ScraperRecipe[HTMLElementModel]](listOfScraperModels)
  }

  "Json must be correctly parsed" must {
    "be a single object json" in {
      val jsonAstObject: JsValue = jsonMockObject.parseJson
      val listOfScraperModelsObject: ListOfScraperRecipes[ScraperRecipe[HTMLElementModel]] = jsonAstObject.convertTo[ListOfScraperRecipes[ScraperRecipe[HTMLElementModel]]]
      listOfScraperModelsObject mustBe pretestingDeclarations_SingleObject(true)
    }
    "be json with array containing one object" in {
      val jsonAstSingleArrayObject: JsValue = jsonMockSingleObjectArray.parseJson
      val listOfScraperModelsSingleArrayObject: ListOfScraperRecipes[ScraperRecipe[HTMLElementModel]] = jsonAstSingleArrayObject.convertTo[ListOfScraperRecipes[ScraperRecipe[HTMLElementModel]]]
      listOfScraperModelsSingleArrayObject mustBe pretestingDeclarations_SingleObject(true)
    }
    "be json with array containing multiple objects" in {
      val jsonAstDoubleArrayObject: JsValue = jsonMockDoubleObjectArray.parseJson
      val listOfScraperModelsDoubleArrayObject: ListOfScraperRecipes[ScraperRecipe[HTMLElementModel]] = jsonAstDoubleArrayObject.convertTo[ListOfScraperRecipes[ScraperRecipe[HTMLElementModel]]]
      listOfScraperModelsDoubleArrayObject mustBe pretestingDeclarations_SingleObject(false)
    }
  }

}
