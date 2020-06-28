package controllers

class ElementModel(val cssTag: String, val cssValue: String, val dataType: String)

case class ListOfElementModels[A](val name: String, val url: String, val list: List[A])

case class ListOfRootModels[A](val list: List[A])
object ListOfElementModels