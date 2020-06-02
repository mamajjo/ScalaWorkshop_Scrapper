package controllers

class ElementModel(val cssTag: String, val cssValue: String, val dataType: String)

case class ListOfElementModels[A](val url: String, val list: List[A])
object ListOfElementModels