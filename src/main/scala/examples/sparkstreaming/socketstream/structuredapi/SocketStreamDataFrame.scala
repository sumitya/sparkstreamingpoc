package examples.sparkstreaming.socketstream.structuredapi

import org.apache.spark.sql.SparkSession

class SocketStreamDataFrame(spark:SparkSession, userName:String){
  // SparkSession is used as a entry point for streaming apps in spark 2.x

  val socketDF = spark.readStream.format("socket")
    .option("host", "localhost").option("port", 7777).load()



}
