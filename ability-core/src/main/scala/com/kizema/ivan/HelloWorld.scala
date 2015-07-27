package com.kizema.ivan

import org.slf4j.LoggerFactory

/**
 * Created by Ivan Kizema on 15-07-27.
 */
object HelloWorld {
  private val logger = LoggerFactory.getLogger(this.getClass)

  def sayHello() {
    logger.info("Hello World !")
  }
}
