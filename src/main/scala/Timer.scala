package com.agirei.metricsd
import org.joda.time._
import scala.collection.mutable.HashMap
import scala.collection.mutable.ListBuffer


object Timer {
  case class Timing(dateTime: DateTime, bucket: String, value: Int) {
    override def toString = "%s:%7s %s" format(dateTime.toString("yyyy-MM-dd HH:mm:ss"), value, bucket)
  }
}

class Timer(retention: Int) {
  var timeBuckets = new HashMap[DateTime, ListBuffer[Timer.Timing]]

  def retention(): Int = retention

  def add(timing: Timer.Timing) {
    val bucketTime = calcBucketTime(timing.dateTime)
    if(!timeBuckets.contains(bucketTime))
      timeBuckets += bucketTime -> ListBuffer()
    timeBuckets(bucketTime) += timing
  }

  private def calcBucketTime(dateTime: DateTime): DateTime = new DateTime((dateTime.getMillis / 1000 / retention) * 1000 * retention)
}
