package examples.sparkstreaming

import examples.sparkstreaming.filestream.TextFileStream
import examples.sparkstreaming.filestream.structuredapi.TextFileStreamDataFrame
import examples.sparkstreaming.socketstream.SocketStream
import examples.sparkstreaming.utils.{Contexts, GetAllProperties}
import org.apache.log4j.{Level, Logger}

object SparkStreamingApp extends App {

  Logger.getLogger("org").setLevel(Level.OFF)
  Logger.getLogger("com").setLevel(Level.OFF)

  private val userName = System.getProperty("user.name")

  val apiType = args(0)

  apiType match {

    case "DSTREAM_API" =>

      // StreamingContext is used as a entry point till spark1.x
      val steamingContext = Contexts.SSC

      val socstream = new SocketStream

      socstream.socketStreamOperations(steamingContext, userName)

      val textFileStream = new TextFileStream

      //textFileStream.fileStreamOperations(steamingContext,userName)

      steamingContext.start()

      steamingContext.awaitTermination()

    case "STRUCTURE_API" =>

      val spark = Contexts.SPARK

      new TextFileStreamDataFrame(spark, userName)

  }


  Thread.sleep(100000)


}
