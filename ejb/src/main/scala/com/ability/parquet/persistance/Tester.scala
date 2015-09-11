package com.ability.parquet.persistance

import com.ability.parquet.model.VineParquet
import org.slf4j.LoggerFactory

/**
 * Created by ikizema on 15-08-25.
 */
object Tester {
  private val logger = LoggerFactory.getLogger(this.getClass)
  val parquetInput = new ParquetLoader[VineParquet]("/home/ikizema/DEV/PROJECTS/personal/ability/DATA/avro/saq_vine_150902.parquet")

  def main(args: Array[String]): Unit = {
    // --show-parquet
    var data = parquetInput.loadData()
    for (vine <- data) {
      logger.info(vine.toString)
    }
  }
}
