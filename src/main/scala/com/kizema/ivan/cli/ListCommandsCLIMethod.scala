package com.kizema.ivan.cli

/**
 * Created by Ivan Kizema on 15-07-27.
 */

import org.slf4j.LoggerFactory

object ListCommandsCLIMethod {
  private val logger = LoggerFactory.getLogger(this.getClass)
  private var inputFileName = new String()
  private var outputFileName = new String()


  // --file
  def file(name:String) {
    this.inputFileName = name
    logger.info("--file : "+this.inputFileName)
  }

  // --out
  def output(name:String) {
    this.outputFileName = name
    logger.info("--output : "+this.outputFileName)
  }

  // --execute
  def execute() {
    logger.info("--execute")

  }
}
