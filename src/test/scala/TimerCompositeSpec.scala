package com.agirei.metricsd.test
import org.scalatest.Spec
import org.scalatest.BeforeAndAfter
import org.scalatest.matchers.ShouldMatchers
import org.joda.time._
import com.agirei.metricsd._

class TimerCompositeSpec extends Spec with BeforeAndAfter with ShouldMatchers {
  var timer: TimerComposite = _
  before {
    timer = new TimerComposite()
  }

  describe("TimerComposite") {

    it("should allow to add timings and put them into time buckets") {
      val bucket = "category.variable.tenant"

      timer.add(new Timer.Timing(new DateTime(2012, 3, 12, 14, 49, 31, 0), bucket, 100))
      timer.add(new Timer.Timing(new DateTime(2012, 3, 12, 14, 49, 49, 0), bucket, 200))
      timer.add(new Timer.Timing(new DateTime(2012, 3, 12, 14, 50, 01, 0), bucket, 300))      
      timer.add(new Timer.Timing(new DateTime(2012, 3, 12, 14, 50, 59, 0), bucket, 400))
      timer.add(new Timer.Timing(new DateTime(2012, 3, 12, 15, 10, 59, 0), bucket, 500))
      timer.timers(0).timeBuckets.keySet should be === Set(new DateTime("2012-03-12T14:50:00.000+01:00"), 
                                                 new DateTime("2012-03-12T14:49:00.000+01:00"),
                                                 new DateTime("2012-03-12T15:10:00.000+01:00"))
      timer.timers(1).timeBuckets.keySet should be === Set(new DateTime("2012-03-12T14:45:00.000+01:00"), 
                                                 new DateTime("2012-03-12T15:00:00.000+01:00"))
    }
  }
}
