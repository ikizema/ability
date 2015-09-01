package com.ability.parsing

import java.net.URL
import java.util
import org.apache.commons.lang3.StringEscapeUtils
import org.htmlcleaner.HtmlCleaner
import org.slf4j.LoggerFactory
import com.google.gson.Gson
import scala.collection.mutable.ListBuffer
import com.ability.model.Vine
import scalaj.http._

/**
 * Created by ikizema on 15-08-25.
 */
object Reader {
  private val logger = LoggerFactory.getLogger(this.getClass)

  def main(args: Array[String]): Unit = {
    val vin = Reader.getVineFromUrl("saq","http://www.saq.com/page/fr/saqcom/vin-rouge/14-hands-hot-to-trot-red-blend-2012/12245611")
//    persistData(new Vine())
//    logger.debug(vin.toString)
  }

  def getVineFromUrl(refClient:String, url: String) : Vine = {
    val vin = new Vine()
    vin.setReferenceURL(url)
    vin.setReferenceClient(refClient)
    var stories = new ListBuffer[String]
    val cleaner = new HtmlCleaner
    val props = cleaner.getProperties
    val rootNode = cleaner.clean(new URL(url))

    // Filter H1
    var elements = rootNode.getElementsByName("h1", true)
    for (elem <- elements) {
      val classType = elem.getAttributeByName("class")
      if (classType != null && classType.equalsIgnoreCase("product-description-title")) {
        val text = StringEscapeUtils.unescapeHtml4(elem.getText.toString)
        vin.setProductName(text.trim)
      }
    }

    // Filter DIV
    elements = rootNode.getElementsByName("div", true)
    for (elem <- elements) {
      val classType = elem.getAttributeByName("class")
      if (classType != null && classType.equalsIgnoreCase("product-description-title-type")) {
        val text = StringEscapeUtils.unescapeHtml4(elem.getText.toString)
        vin.setProductType(text.trim)
      }
      if (classType != null && classType.equalsIgnoreCase("product-description-row2")) {
        val text = StringEscapeUtils.unescapeHtml4(elem.getText.toString)
        for (line <- text.replaceAll("""\s""","").split("Code")) {
          if (line.size>0) {
            val entrie ="Code"+line
            if (entrie.contains("CodeSAQ")) {
              vin.setCodeSAQ(entrie.replaceAll("\\D+",""))
            }
            if (entrie.contains("CodeCUP")) {
              vin.setCodeCPU(entrie.replaceAll("\\D+",""))
            }
          }
        }
      }
      if (classType != null && classType.equalsIgnoreCase("product-page-subtitle")) {
        val text = StringEscapeUtils.unescapeHtml4(elem.getText.toString)
        vin.setProduceCountry(text.trim)
      }
      if (classType != null && classType.equalsIgnoreCase("product-description-region product-page-subtitle")) {
        val text = StringEscapeUtils.unescapeHtml4(elem.getText.toString)
        vin.setProduceRegion(text.trim)
      }
      if (classType != null && classType.equalsIgnoreCase("filet-bottom2 product-description-row3")) {
        val text = StringEscapeUtils.unescapeHtml4(elem.getText.toString)
        val informationArray = new util.ArrayList[String]
        for (line <- text.split("\n")) {
          if (line.replaceAll("""\s""","").size>0) {
            informationArray.add(line.trim)
          }
        }
        for (i <- 0 to informationArray.size()-1) {
          if (informationArray.get(i)=="Producteur") {
            vin.setProducer(informationArray.get(i+1))
          }
          if (informationArray.get(i)=="Degré d'alcool") {
            vin.setDegAlcool(informationArray.get(i+1))
          }
        }
      }
      // Setting Vine Image URL
      // Vine Image http://s7d9.scene7.com/is/image/SAQ/12245611-1?rect=0,0,533,800&scl=1.875&id=-Hgqe3
      // Change size settings : ?rect=0,0,1000,1500&scl=1&id=-Hgqe3
      vin.setImageURL("http://s7d9.scene7.com/is/image/SAQ/"+vin.getCodeSAQ+"-1?rect=0,0,1000,1500&scl=1&id=-Hgqe3")
    }

    persistData(vin)

    return vin
  }

  def persistData(vin:Vine) {
    val vinAsJson = new Gson().toJson(vin)
    val url = "http://localhost:8080/ability-web/rest/vines"
    val response: HttpResponse[String] = Http(url).header("Content-Type", "application/json")
      .postData(vinAsJson)
      .asString
    logger.info(response.body)
  }
}
