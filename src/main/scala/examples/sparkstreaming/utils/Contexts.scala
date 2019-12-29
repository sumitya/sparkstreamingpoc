package examples.sparkstreaming.utils

import java.io.File

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.{Seconds, StreamingContext}

object Contexts {

  val conf:SparkConf = new SparkConf().setMaster("local[3]").setAppName("SparkStreamingPoc")
  val SSC = new StreamingContext(conf,Seconds(10))

  // SPARK(or SPARKSESSION) is created as soon as it is declared here.
  val SPARK:SparkSession = SparkSession
    .builder
    .appName("SparkStreamingPOC")
    .master("local[3]")
    .config("spark.sql.warehouse",new File("spark-warehouse").getAbsolutePath)
    .getOrCreate()

  // Advantage of declare it def is, it will be executed only when called..lazy evaluation
  def stopContext:Unit = SPARK.stop()

}

