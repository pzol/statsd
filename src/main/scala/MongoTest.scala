package com.agirei.metricsd

import com.mongodb._
import com.mongodb.casbah.Imports._
import com.agirei.metricsd._
import com.agirei.metricsd.Timer.Timing 

class MongoTest {

  def run() {

    val mongo = MongoConnection()
    val coll = mongo("stat_logs")("log")  

    val query = MongoDBObject()

    var i = 0
    val timers = new TimerComposite()
    coll.find(query).foreach { doc =>
      val reader = new StatLogsReader(doc)
      val timings = reader.read
      
      timings.foreach { timing: Timing => 
        println(timing) 
        timers.add(timing)
        i += 1
      }
    }
    println("count %d" format i)
    
  }

}
