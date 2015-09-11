package com.ability.spark

import org.apache.spark.{SparkContext, SparkConf}
import org.slf4j.LoggerFactory

/**
 * Created by ikizema on 15-07-03.
 */
object AppContext {
  val conf = new SparkConf().setAppName("Ability").setMaster("local[1]")
  val sc = new SparkContext(conf)
  var logger = LoggerFactory.getLogger(this.getClass)
  logger.info("Load AppContext")
}
