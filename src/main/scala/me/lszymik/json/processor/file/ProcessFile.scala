package me.lszymik.json.processor.file

import java.nio.file.{Files, Paths}

import akka.NotUsed
import akka.stream.scaladsl.{FileIO, Flow, Keep, RunnableGraph, Source}
import akka.stream.{IOResult, Materializer}
import akka.util.ByteString
import com.typesafe.scalalogging.LazyLogging
import de.knutwalker.akka.stream.support.CirceStreamSupport
import io.circe.Json
import me.lszymik.json.processor.exceptions.FileProcessingException

import scala.concurrent.{ExecutionContext, Future}


class ProcessFile(implicit executionContext: ExecutionContext, materializer: Materializer) extends LazyLogging {

  def process(inputFile: String, outputFile: String): Future[IOResult] = createGraph(inputFile, outputFile).run()

  private def createGraph(inputFile: String, outputFile: String): RunnableGraph[Future[IOResult]] = {
    getFileSource(inputFile)
      .via(framingDecoder)
      .via(CirceStreamSupport.decode[Json])
      .via(toByteString()).intersperse(ByteString("["), ByteString(","), ByteString("]"))
      .toMat(FileIO.toPath(Paths.get(outputFile)))(Keep.right)
  }

  val framingDecoder: Flow[ByteString, ByteString, NotUsed] =
    akka.stream.scaladsl.JsonFraming.objectScanner(8192)

  private def getFileSource(inputFile: String): Source[ByteString, Future[IOResult]] = {
    val file = Paths.get(inputFile)
    if (Files.exists(file)) {
      FileIO.fromPath(file)
    }
    else {
      throw FileProcessingException(s"File '$file' not found.")
    }
  }

  private def toByteString(): Flow[Json, ByteString, NotUsed] = {
    Flow[Json] map { json ⇒
      ByteString(json.toString)
    }
  }

  private def printDocument(): Flow[Json, Json, NotUsed] = {
    Flow[Json] map { chunk ⇒
      logger.info(s"Chunk: ${chunk.toString()}")
      chunk
    }
  }

}
