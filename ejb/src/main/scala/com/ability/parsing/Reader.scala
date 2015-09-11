package com.ability.parsing

import java.net.URL
import java.util
import org.apache.commons.lang3.StringEscapeUtils
import org.htmlcleaner.HtmlCleaner
import org.opencv.features2d.{DescriptorExtractor, FeatureDetector}
import org.slf4j.LoggerFactory
import com.google.gson.Gson
import scala.collection.mutable.ListBuffer
import com.ability.model.Vine
import scalaj.http._
import com.ability.parquet.model._
import com.ability.parquet.persistance._
import com.ability.imaging.features2D.ImageFeatures2D

/**
 * Created by ikizema on 15-08-25.
 */
class Reader(fileName:String) {
  private val logger = LoggerFactory.getLogger(this.getClass)
  private val parquetWriter = new ParquetWriter[VineParquet](fileName, VineParquet.getClassSchema)

  def main(args: Array[String]): Unit = {
    val reader = new Reader("saq_vine_150902")
    val vin = reader.getVineFromUrl("saq","http://www.saq.com/page/fr/saqcom/vin-rouge/14-hands-hot-to-trot-red-blend-2012/12245611")

    // -- Write-Parquet
    val parquetWriter = new ParquetWriter[VineParquet](this.fileName, VineParquet.getClassSchema)
    parquetWriter.persistUnitary(toVineParquet(vin))
    parquetWriter.close()

    // --show-parquet
//    val parquetInput = new ParquetLoader("/home/ikizema/DEV/PROJECTS/personal/ability/DATA/avro/Vine_150902.parquet")
//    parquetInput.showSchema()
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

    // Persist Data to the Database
//    persistDataDB(vin)

    // Persist Data to Parquet
    persistDataParquet(vin)

    return vin
  }

  def persistDataDB(vin:Vine) {
    val vinAsJson = new Gson().toJson(vin)
    val url = "http://localhost:8080/ability-web/rest/vines"
    val response: HttpResponse[String] = Http(url).header("Content-Type", "application/json")
      .postData(vinAsJson)
      .asString
    logger.info(response.body)
  }

  def persistDataParquet(vin:Vine) {
    val vineParquet = toVineParquet(vin)
    parquetWriter.persistUnitary(vineParquet)
  }

  def persistanceClose(): Unit = {
    parquetWriter.close()
  }

  def toVineParquet(vin:Vine) : VineParquet = {
    val newFeature2D: ImageFeatures2D = new ImageFeatures2D(new URL(vin.getImageURL), true, FeatureDetector.DYNAMIC_ORB, DescriptorExtractor.BRIEF);
    val descriptor = Descriptor.newBuilder()
      .setDescriptorType(FeatureDetector.DYNAMIC_ORB+"_"+DescriptorExtractor.BRISK)
      .setDescriptorHeight(newFeature2D.getEncoder.getHeight)
      .setDescriptorWidth(newFeature2D.getEncoder.getWidth)
      .setDescriptorChannels(newFeature2D.getEncoder.getChannels)
      .setDescriptorData(newFeature2D.getEncoder.getEncodedString)
      .build()
    val vineParquet = VineParquet.newBuilder()
      .setReferenceClient(vin.getReferenceClient)
      .setReferenceURL(vin.getReferenceURL)
      .setCodeCPU(vin.getCodeCPU)
      .setCodeSAQ(vin.getCodeSAQ)
      .setDegAlcool(vin.getDegAlcool)
      .setImageURL(vin.getImageURL)
      .setProduceCountry(vin.getProduceCountry)
      .setProducer(vin.getProducer)
      .setProduceRegion(vin.getProduceRegion)
      .setProductName(vin.getProductName)
      .setProductType(vin.getProductType)
      .setDescriptor(descriptor)
      .build()
    return vineParquet
  }
}
