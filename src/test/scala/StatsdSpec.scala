package com.agirei.metricsd.test

import scala.io.Source
import org.scalatest.Spec
import org.scalatest.BeforeAndAfter
import org.scalatest.matchers.ShouldMatchers
import com.mongodb.util.JSON
import com.mongodb.casbah.Imports._
import org.joda.time._
import scala.util.matching.Regex
import com.agirei.metricsd._
import com.agirei.metricsd.Timer.Timing 

class StatsdSpec extends Spec with BeforeAndAfter with ShouldMatchers {
  var timer: TimerComposite = _
  before {
    val source = Source.fromURL(getClass.getResource("/stat_logs.json"))
    timer = new TimerComposite()
    var regex = new Regex("""\{ "\$date" : (\d+) \}""", "date")

    source.getLines().foreach { json =>
      val fixed = regex replaceAllIn (json, m => "\"" + (new DateTime(m.group("date").toLong)) + "\"" )
      val doc = JSON.parse(fixed).asInstanceOf[DBObject]
      val reader = new StatLogsReader(doc)
      val timings = reader.read      
      timings.foreach { timer.add(_) }
    }
  }

  describe("Statsd") {

    it("should work") {
      timer.timers.foreach { timer =>
        println("Timer %d" format timer.retention)
        timer.timeBuckets.foreach { tuple =>
          println("%s: %d" format(tuple._1, tuple._2.length))
        } 
      }
    }
  }
}
