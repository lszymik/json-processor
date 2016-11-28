package me.lszymik.json.processor

import org.scalamock.scalatest.MockFactory
import org.scalatest._
import org.scalatest.concurrent.ScalaFutures

import scala.util.Random

trait BaseTest
  extends WordSpecLike
  with MustMatchers
  with ScalaFutures
  with EitherValues
  with OptionValues
  with MockFactory
  with BeforeAndAfterAll {

  def randomName: String = Random.alphanumeric take 10 mkString
}
