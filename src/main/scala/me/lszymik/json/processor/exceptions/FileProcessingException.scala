package me.lszymik.json.processor.exceptions

/**
  * Exception indication that file could not be processed.
  *
  * @param message with error description
  */
case class FileProcessingException(message: String) extends RuntimeException(message)