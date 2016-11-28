package me.lszymik.json.processor.config

import com.typesafe.config.ConfigFactory
import me.lszymik.json.processor.BuildInfo

/**
 * Application's main configuration, read from application.conf.
 */
trait AppConfig {

  private lazy val config = ConfigFactory.load()

  lazy val appName: String = BuildInfo.name
  lazy val appVersion: String = BuildInfo.version
}
