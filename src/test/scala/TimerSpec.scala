package com.agirei.metricsd.test
import org.scalatest.Spec
import org.scalatest.BeforeAndAfter
import org.scalatest.matchers.ShouldMatchers
import org.joda.time._
import com.agirei.metricsd.Timer 

class TimerSpec extends Spec with BeforeAndAfter with ShouldMatchers {
  var timer: Timer = _
  before {
    timer = new Timer(60)
  }

  describe("Timer") {

    it("should accept retention in seconds in the default constructor") {
      timer.retention should be === 60
    }

    it("should allow to add timings and put them into time buckets") {
      val bucket = "category.variable.tenant"

      timer.add(new Timer.Timing(new DateTime(2012, 3, 12, 14, 49, 31, 0), bucket, 360))
      timer.add(new Timer.Timing(new DateTime(2012, 3, 12, 14, 49, 49, 0), bucket, 360))
      timer.add(new Timer.Timing(new DateTime(2012, 3, 12, 14, 50, 01, 0), bucket, 360))      
      timer.add(new Timer.Timing(new DateTime(2012, 3, 12, 14, 50, 59, 0), bucket, 360))      
      timer.timeBuckets.keySet should be === Set(new DateTime("2012-03-12T14:50:00.000+01:00"), 
                                                 new DateTime("2012-03-12T14:49:00.000+01:00"))
      timer.timeBuckets(new DateTime("2012-03-12T14:50:00.000+01:00")).length should be === 2
      timer.timeBuckets(new DateTime("2012-03-12T14:49:00.000+01:00")).length should be === 2
    }
  }

  it("should return Timing formatted nicely as a String") {
    val timing = new Timer.Timing(new DateTime(2012, 3, 12, 14, 49, 31, 0), "one.two.three", 360)
    timing.toString should be === "2012-03-12 14:49:31:    360 one.two.three"
  }
}
