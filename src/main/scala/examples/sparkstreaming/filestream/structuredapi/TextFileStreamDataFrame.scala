package examples.sparkstreaming.filestream.structuredapi

import examples.sparkstreaming.utils.{Contexts, GetAllProperties}
import org.apache.spark.sql.SparkSession

class TextFileStreamDataFrame(spark:SparkSession, userName:String){
  // SparkSession is used as a entry point for streaming apps in spark 2.x

  //This is used for local development only.
  val activity_data = GetAllProperties.readPropertyFile getOrElse("ACTIVITY_DATA" ,"#") replace("<USER_NAME>", userName)

  val dataSchema = spark.read
    .json(activity_data)
    .schema

  // Here readStream -> DataStreamReader

  val streaming = spark.readStream.schema(dataSchema).option("maxFilesPerTrigger",1).json(activity_data)

  val activityCounts = streaming.groupBy("gt").count()

  import org.apache.spark.sql.functions.expr

  // Selections and Filtering in streaming

  streaming.withColumn("stairs",expr("gt like '%stairs%'"))
    .where("stairs")
    .where("gt is not null")
    .select("gt","model","arrival_time")
    .writeStream
    .queryName("simple_transform")
    .format("console")  // -> //.format("memory"), this allows select * from simple_transform query from in-memory table.
    .outputMode("append")
    .start()


  // start is an ACTION here and
  // we will specify an output destination, or output sink for our result of this query.
  // Lets write to a memory sink which keeps an in-memory table of the results.

  val activityQuery = activityCounts
    .writeStream
    .queryName("activityCounts")
    .format("memory")
    .outputMode("complete")
    .start

  //Joins in Streaming joining batch data with streaming data.

  val staticData = spark.read
    .json(activity_data)

  val historicalAgg = streaming.groupBy("gt","model").avg()

 /* streaming.drop("Arrival_Time", "Creation_Time", "Index")
      .cube("gt","model").avg()
      .join(historicalAgg,Seq("gt","model"))
      .writeStream
      .queryName("device_counts")
      .format("memory")
      .outputMode("append")
      .start
*/

  //Spark lists this stream, and other active ones, under the active streams in our SparkSession.
  println(Contexts.SPARK.streams.active)

  activityQuery.explain()

  // The query object is a handle to that active streaming query,
  // and we must specify that we would like to wait for the termination
  // of the query using activityQuery.awaitTermination() to prevent the driver process
  // from exiting while the query is active

  activityQuery.awaitTermination()



}
