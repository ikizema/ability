package com.ability.imaging

import java.io.File
import com.ability.imaging.features2D.DescriptorComparator
import org.slf4j.LoggerFactory

/**
 * Created by ikizema on 15-08-25.
 */
object Tester {
  private val logger = LoggerFactory.getLogger(this.getClass)

  def main(args: Array[String]): Unit = {
    logger.info("Begin image search")
    val start: Long = System.currentTimeMillis

    val comparator = new DescriptorComparator("./DATA/avro/saq_vine_150902.parquet")
    logger.info("Loading data :"+(System.currentTimeMillis-start)+"ms.")

    val vine = comparator.findBestMatch(new File("./DATA/images/image1.jpeg"))
    logger.info("Image :"+vine.getReferenceURL.toString+" Find in :"+(System.currentTimeMillis-start)+"ms.")
  }
}
