package me.lszymik.json.processor.config

import me.lszymik.json.processor.BaseTest

class CliTest extends BaseTest with Cli {

  "Cli" should {

    "read input and output files" in {

      val args = Array("--input", "input file", "--output", "output file")

      readCliConfig(args).value mustBe CliConfig("input file", "output file")
    }

    "validate environment" in {

      val args = Array("--input", "input file")

      readCliConfig(args) mustBe None
    }
  }
}
