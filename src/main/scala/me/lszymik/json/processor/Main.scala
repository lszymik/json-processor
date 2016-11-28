package me.lszymik.json.processor

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.typesafe.scalalogging.LazyLogging
import me.lszymik.json.processor.config.{AppConfig, Cli}
import me.lszymik.json.processor.file.ProcessFile

import scala.util.{Failure, Success}

/**
  * Main class of backup tool.
  */
object Main extends App with Cli with AppConfig with LazyLogging {

  private def run(): Unit = {
    readCliConfig(args) match {
      case Some(cliConfig) ⇒ processFile(cliConfig.input, cliConfig.output)

      case None ⇒

    }
  }

  private def processFile(inputFile: String, outputFile: String) = {
    implicit val system = ActorSystem("dbr")
    implicit val materializer = ActorMaterializer()

    implicit val executionContext = system.dispatcher

    new ProcessFile().process(inputFile, outputFile) onComplete {
      case Success(_) =>
        logger.info("Processing done successfully")
        system.terminate()

      case Failure(ex) =>
        logger.error("Processing failed with error: " + ex.getMessage)
        system.terminate()
    }
  }

  run()
}