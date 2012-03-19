package com.agirei

import com.mongodb._
import com.mongodb.casbah.Imports._
import com.agirei.metricsd.MongoTest

object Main extends App {
  println("Statsd starting")

  val statsd = new Statsd()

  val mongoTest = new MongoTest()
  mongoTest.run()

}
