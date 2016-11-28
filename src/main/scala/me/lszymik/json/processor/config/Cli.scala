package me.lszymik.json.processor.config

/**
 * Functions for command line interface.
 */
trait Cli extends AppConfig {

  private val parser = new scopt.OptionParser[CliConfig](appName) {
    head(appName, appVersion, "- Json Parser")

    opt[String]("input")
      .action((cl, cfg) => cfg.copy(input = cl))
      .text("Input file")
      .required()

    opt[String]("output")
      .action((cl, cfg) => cfg.copy(output = cl))
      .text("Output file")
      .required()

    help("help").text("prints this usage text")

    note("")
  }

  def readCliConfig(args: Array[String]): Option[CliConfig] = {
    parser.parse(args, CliConfig())
  }

}
