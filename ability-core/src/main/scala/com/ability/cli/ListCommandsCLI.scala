package com.ability.cli

import org.rogach.scallop._

/**
 * Created by Ivan Kizema on 15-07-27.
 */

// List of commands used
class ListCommandsCLI(arguments: Seq[String]) extends ScallopConf(arguments) {

  val file = opt[String](
    "file",
    argName = "name",
    noshort = true,
    descr = "Specify the input filename"
  );

  val output = opt[String](
    "output",
    argName = "name",
    noshort = true,
    descr = "Specify the output filename"
  );

  val execute  = opt[Boolean](
    "execute",
    noshort = true,
    descr = "Run execute"
  );

  val help = opt[Int](
    "help",
    short = 'h',
    descr = "Show help message"
  );
}