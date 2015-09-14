package com.ability.imaging

import com.ability.parquet.model.VineParquet
import com.ability.parquet.persistance.{ParquetLoader, ParquetWriter}
import org.slf4j.LoggerFactory
import scala.collection.JavaConverters._

/**
 * Created by ikizema on 15-09-11.
 */
class Copmarator(fileName:String) {
  private val logger = LoggerFactory.getLogger(this.getClass)
  private val parquetLoader = new ParquetLoader[VineParquet](fileName)
  private val data = parquetLoader.loadData()

  def getData() : java.util.List[VineParquet] = {
    return this.data.toList.asJava
  }

//  def getBestMatch(file: File): VineParquet =  {
//    return getBestMatch(ImageIO.read(file));
//  }
//
//  def getBestMatch(image: BufferedImage) : VineParquet = {
//    val imageFeatures2D = new ImageFeatures2D(image, false)
//    var minDistance : Float = 1000
//    var bestMatch = new VineParquet()
//    for (matrix <- data) {
//      val matches: MatOfDMatch = new MatOfDMatch()
//      val matcher: DescriptorMatcher = DescriptorMatcher.create(2)
//      val decoder = new Encoder(matrix.getDescriptor.getDescriptorHeight.toInt, matrix.getDescriptor.getDescriptorWidth.toInt, matrix.getDescriptor.getDescriptorChannels.toInt, matrix.getDescriptor.getDescriptorData.toString)
//      val matrixDescriptor : Mat = decoder.EncoderToMat()
//      matcher.`match`(imageFeatures2D.getImageDescriptor, matrixDescriptor, matches)
//      val matchesAnalyse: MatchAnalysis = new MatchAnalysis(matches)
//      if (matchesAnalyse.getMin < minDistance) {
//        minDistance=matchesAnalyse.getMin
//        bestMatch=matrix
//      }
//    }
//    return bestMatch
//  }
}
