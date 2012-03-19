package com.agirei.metricsd.test
import org.scalatest.Spec
import org.scalatest.BeforeAndAfter
import org.scalatest.matchers.ShouldMatchers
import com.mongodb.util.JSON
import com.mongodb.casbah.Imports._
import com.agirei.metricsd._
import com.agirei.metricsd.Timer.Timing 
import org.scala_tools.time.Imports._
import org.joda.time.DateTime

class StatLogsReaderSpec extends Spec with BeforeAndAfter with ShouldMatchers {
  describe("StatLogsReaderSpec") {

    it("should") {
      val json = """{ "_id" : { "$oid" : "4f1963c9cf79522550a4a5be"} , "date" : { "$date" : "2012-01-20T12:53:29Z"} , "request_id" : "4903725968590133015" , "environment" : "acceptance" , "server_ip" : "194.213.22.199" , "contract" : "test" , "product" : "HOTEL" , "country_code" : "ES" , "city_code" : [ "PMI"] , "date_from" : { "$date" : "2012-05-31T22:00:00Z"} , "date_to" : { "$date" : "2012-06-03T22:00:00Z"} , "method" : "hotels" , "facility" : "resfinity.api" , "time_taken" : 22678 , "partial_time_taken" : [ { "facility" : "anixe.Xenia.MasterCodeDataSource" , "time_taken" : 38 , "count" : 11} , { "facility" : "anixe.TravelObjects.Engine" , "time_taken" : 22547} , { "facility" : "MasterHotelsBuilder" , "time_taken" : 1 , "count" : 8} , { "facility" : "remaining" , "time_taken" : 92}] , "hotel" : { "providers" : [ { "provider" : "TOURICO" , "time_taken" : 3238 , "currency" : "EUR" , "provider_city_code" : [ "PMI"] , "cat4" : { "min_price" : 299.25 , "max_price" : 348.51 , "no_hotels" : 5} , "cat5" : { "min_price" : 598.47 , "max_price" : 598.47 , "no_hotels" : 1} , "transactions" : { "search_hotels" : { "sent" : 2 , "received" : 2}}} , { "provider" : "KUONI" , "time_taken" : 1177 , "currency" : "EUR" , "provider_city_code" : [ "376"] , "cat4" : { "min_price" : 408.0 , "max_price" : 408.0 , "no_hotels" : 1} , "cat3" : { "min_price" : 294.0 , "max_price" : 294.0 , "no_hotels" : 1} , "transactions" : { "availability" : { "sent" : 1 , "received" : 1}}} , { "provider" : "HOTELBEDS" , "time_taken" : 3202 , "errors" : [ "C01-01-007-FO, You are not allowed to access the system., You are not allowed to access the system." , "0AVL"] , "transactions" : { "availability_request" : { "sent" : 2 , "received" : 2}}} , { "provider" : "GTA" , "time_taken" : 3199 , "errors" : [ "0AVL"] , "transactions" : { "search_hotel_price_request" : { "sent" : 1 , "received" : 1}}} , { "provider" : "MIKI" , "time_taken" : 862 , "errors" : [ "3350, We dont have prices for this criteria.  Please choose alternative criteria." , "0AVL"] , "transactions" : { "city_search_request" : { "sent" : 1 , "received" : 1}}} , { "provider" : "VALADIS" , "time_taken" : 12103 , "errors" : [ "The maximum number of connections has been exceeded" , "0AVL"] , "transactions" : { "availability" : { "sent" : 5 , "received" : 5}}} , { "provider" : "JACTRAVEL" , "time_taken" : 3604 , "errors" : [ "0AVL"] , "transactions" : { "service_search_request" : { "sent" : 2 , "received" : 2}}} , { "provider" : "TRAVCO" , "time_taken" : 3878 , "errors" : [ "Information ( Your Selection is currently not featured. Please go back and select other items. Error No:HAXSD3029)" , "0AVL"] , "transactions" : { "availability" : { "sent" : 1 , "received" : 1}}} , { "provider" : "MASTERHOTELS" , "time_taken" : 1985 , "transactions" : { "master_hotels" : { "sent" : 1 , "received" : 1}}} , { "provider" : "SIDETOURS" , "time_taken" : 12012 , "errors" : [ "0AVL"] , "transactions" : { "availability" : { "sent" : 1 , "received" : 1}}} , { "provider" : "OHG" , "time_taken" : 22436 , "errors" : [ "Timeout occured."] , "transactions" : { "availability_search" : { "sent" : 1 , "received" : 0}}}]}} """
      val doc = JSON.parse(json).asInstanceOf[DBObject]
      val reader = new StatLogsReader(doc)     
      reader.read should be === 
        Seq(Timing(new DateTime("2012-01-20T13:53:29.000+01:00"), "provider.tourico.test", 3238), 
            Timing(new DateTime("2012-01-20T13:53:29.000+01:00"), "provider.kuoni.test", 1177), 
            Timing(new DateTime("2012-01-20T13:53:29.000+01:00"), "provider.hotelbeds.test", 3202), 
            Timing(new DateTime("2012-01-20T13:53:29.000+01:00"), "provider.gta.test", 3199), 
            Timing(new DateTime("2012-01-20T13:53:29.000+01:00"), "provider.miki.test", 862),
            Timing(new DateTime("2012-01-20T13:53:29.000+01:00"), "provider.valadis.test", 12103),
            Timing(new DateTime("2012-01-20T13:53:29.000+01:00"), "provider.jactravel.test", 3604), 
            Timing(new DateTime("2012-01-20T13:53:29.000+01:00"), "provider.travco.test", 3878), 
            Timing(new DateTime("2012-01-20T13:53:29.000+01:00"), "provider.masterhotels.test", 1985), 
            Timing(new DateTime("2012-01-20T13:53:29.000+01:00"), "provider.sidetours.test", 12012), 
            Timing(new DateTime("2012-01-20T13:53:29.000+01:00"), "provider.ohg.test", 22436),
            Timing(new DateTime("2012-01-20T13:53:29.000+01:00"), "api.hotels.test", 22678))
    }
  }
}
