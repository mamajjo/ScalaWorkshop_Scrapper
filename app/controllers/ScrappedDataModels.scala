package controllers

case class HTMLElementModel(cssTag: String, cssValue: String, dataType: String)

case class ScraperRecipe[A](name: String, url: String, list: List[A])

case class ListOfScraperRecipes[A](list: List[A])