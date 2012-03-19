package com.agirei.metricsd
import com.agirei.metricsd._
import org.joda.time._

class TimerComposite {
  private val timerCollection = List(60, 900).map(t => new Timer(t))

  def timers = timerCollection
  def apply(i: Int) = timerCollection(i)

  def add(timing: Timer.Timing) {
    timers.foreach { _.add(timing) }
  }
}
