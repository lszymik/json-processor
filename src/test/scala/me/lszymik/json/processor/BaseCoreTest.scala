package me.lszymik.json.processor

import akka.actor.ActorSystem
import akka.testkit.TestKit

class BaseCoreTest
  extends TestKit(ActorSystem("test-system"))
  with BaseTest {

  override protected def afterAll() {
    system.terminate()
  }
}
