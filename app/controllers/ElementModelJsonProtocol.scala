package controllers

import spray.json.{DefaultJsonProtocol, DeserializationException, JsArray, JsObject, JsString, JsValue, RootJsonFormat}

import scala.collection.mutable.ListBuffer

object ElementModelJsonProtocol extends DefaultJsonProtocol {

  implicit object ModelJsonFormat extends RootJsonFormat[ElementModel] {

    override def read(json: JsValue): ElementModel = {
      json.asJsObject.getFields("cssTag", "cssValue", "dataType") match {
        case Seq(JsString(cssTag), JsString(cssValue), JsString(dataType)) =>
          new ElementModel(cssTag, cssValue, dataType)
        case _ => throw DeserializationException("ElementModel Expected")
      }
    }

    override def write(elem: ElementModel): JsValue = JsObject(
      "cssTag" -> JsString(elem.cssTag),
      "cssValue" -> JsString(elem.cssValue),
      "dataType" -> JsString(elem.dataType)
    )
  }

}

object ListOfElementModelJsonProtocol extends DefaultJsonProtocol {

  implicit object ListOfModelJsonFormat extends RootJsonFormat[ListOfElementModels[ElementModel]] {
    override def read(json: JsValue): ListOfElementModels[ElementModel] = json match {
      case jsObj: JsObject =>
        jsObj.getFields("name", "url", "tags") match {
          case Seq(JsString(name), JsString(url), JsArray(tags)) =>
            var elements = new ListBuffer[ElementModel]()
            tags.foreach(json => json.asJsObject.getFields("cssTag", "cssValue", "dataType") match {
              case Seq(JsString(cssTag), JsString(cssValue), JsString(dataType)) =>
                elements += new ElementModel(cssTag, cssValue, dataType)
              case _ => throw DeserializationException("ElementModel Expected")
            })
            new ListOfElementModels[ElementModel](name, url, elements.toList)
        }
    }

    override def write(obj: ListOfElementModels[ElementModel]): JsValue = JsObject(
      "name" -> JsString(obj.name),
      "url" -> JsString(obj.url),
      "tags" -> JsArray(obj.list.map(elem => ElementModelJsonProtocol.ModelJsonFormat.write(elem)).toVector)
    )
  }

}
object ListOfRootJsonProtocol extends DefaultJsonProtocol {

  implicit object ListOfModelJsonFormat extends RootJsonFormat[ListOfRootModels[ListOfElementModels[ElementModel]]] {
    override def read(json: JsValue): ListOfRootModels[ListOfElementModels[ElementModel]] = json match {
      case jsObj: JsObject =>
        val listOfElements = new ListBuffer[ListOfElementModels[ElementModel]]
        jsObj.getFields("name", "url", "tags") match {
          case Seq(JsString(name), JsString(url), JsArray(tags)) =>
            var elements = new ListBuffer[ElementModel]()
            tags.foreach(json => json.asJsObject.getFields("cssTag", "cssValue", "dataType") match {
              case Seq(JsString(cssTag), JsString(cssValue), JsString(dataType)) =>
                elements += new ElementModel(cssTag, cssValue, dataType)
              case _ => throw DeserializationException("ElementModel Expected")
            })
            listOfElements.append(new ListOfElementModels[ElementModel](name, url, elements.toList))
        }
        new ListOfRootModels[ListOfElementModels[ElementModel]](listOfElements.toList)
      case jsArr: JsArray =>
        val listOfElements = new ListBuffer[ListOfElementModels[ElementModel]]
        jsArr.elements.foreach(jsObj => {
          jsObj.asJsObject.getFields("name", "url", "tags") match {
            case Seq(JsString(name), JsString(url), JsArray(tags)) =>
              var elements = new ListBuffer[ElementModel]()
              tags.foreach(json => json.asJsObject.getFields("cssTag", "cssValue", "dataType") match {
                case Seq(JsString(cssTag), JsString(cssValue), JsString(dataType)) =>
                  elements += new ElementModel(cssTag, cssValue, dataType)
                case _ => throw DeserializationException("ElementModel Expected")
              })
              listOfElements.append(new ListOfElementModels[ElementModel](name, url, elements.toList))
          }
        })
        new ListOfRootModels[ListOfElementModels[ElementModel]](listOfElements.toList)
      case _ => throw DeserializationException("ListOfElementModel Expected")
        }

    override def write(obj: ListOfRootModels[ListOfElementModels[ElementModel]]): JsValue = ???
  }
}