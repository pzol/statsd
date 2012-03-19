package com.agirei.metricsd
import com.mongodb.casbah.Imports._
import com.agirei.metricsd._
import com.agirei.metricsd.Timer.Timing 
import org.scala_tools.time.Imports._
import org.joda.time.DateTime

class StatLogsReader(doc: DBObject) {
  val date = new DateTime(doc.get("date"))
  val providers = doc.as[DBObject]("hotel").as[BasicDBList]("providers").asInstanceOf[BasicDBList]
  val contract = doc.getAs[String]("contract").getOrElse("unknown")
  val tenant = contract.split("\\.").first
  val time_taken = doc.getAs[Long]("time_taken").getOrElse(0L).toInt
  val method = doc.getAs[String]("method").getOrElse("unknown")

  def read(): Seq[Timing] = {

    def bucket(prefix: String, provider: String) = "%s.%s.%s".format(prefix, provider, tenant).toLowerCase
    def get_time_taken(obj: DBObject) = obj.getAs[Long]("time_taken").getOrElse(0L).toInt

    val providerTimings = for {o <- providers
      providerObj = o.asInstanceOf[DBObject]
      provider = providerObj.as[String]("provider")
      time_taken = get_time_taken(providerObj)
    } yield Timing(date, bucket("provider", provider), time_taken)

    providerTimings :+ Timing(date, bucket("api", method), time_taken)
  }
}
