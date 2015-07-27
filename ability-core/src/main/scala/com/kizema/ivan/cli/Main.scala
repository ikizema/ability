package com.kizema.ivan.cli

import org.slf4j.LoggerFactory

/**
 * Created by Ivan Kizema on 15-07-27.
 */
object Main {
  private val logger = LoggerFactory.getLogger(this.getClass)

  def main(args: Array[String]): Unit = {
    var argumentString = "arguments : "
    args.foreach(argument => argumentString = argumentString + argument + " ")
    this.logger.info("Begin with " + argumentString)
    val conf: ListCommandsCLI = new ListCommandsCLI(args)
    conf.args.foreach(
      arg => {
        if (arg.charAt(0)=='-') {
          arg match {
            case "--file" => ListCommandsCLIMethod.file(conf.file())
            case "--out" => ListCommandsCLIMethod.output(conf.output())
            case "--execute" => ListCommandsCLIMethod.execute()
            case _ => println(conf.help())
          }
        }
      })
  }
}
