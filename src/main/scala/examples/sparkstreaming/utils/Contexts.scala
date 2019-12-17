package examples.sparkstreaming.utils

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object Contexts {

  val conf:SparkConf = new SparkConf().setMaster("local[2]").setAppName("SparkStreamingPoc")
  val SSC = new StreamingContext(conf,Seconds(15))

}

