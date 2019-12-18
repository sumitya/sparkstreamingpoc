package examples.sparkstreaming.socketstream

import examples.sparkstreaming.parser.ParseLogLine
import examples.sparkstreaming.utils.GetAllProperties
import org.apache.spark.streaming.{Seconds, StreamingContext}

class SocketStream {

  def socketStreamOperations(steamingContext:StreamingContext,userName:String) = {

    val stream = steamingContext.socketTextStream("localhost",7777)

    val parseLogLine = new ParseLogLine

    //res: can be reused multiple time
    val res = stream.map{parseLogLine.parse}.map( logrecord => (logrecord.ipAddress,logrecord.timestamp))

    //stateless transformation i.e. single batch in DStream will be processed
    val countsOfUniqueIps = res.map(rec => (rec._1,1)).reduceByKey( _+_ )

    println("Printing the transformation for stateless")
    countsOfUniqueIps.print()

    //stateful transformation i.e take a window of DStreams
    val resWindow = res.window(Seconds(30), Seconds(20))

    val countsOfUniqueIpsinWindow = resWindow.map(rec => (rec._1,1)).reduceByKey( _+_ )

    println("Printing the transformation for stateful")
    countsOfUniqueIpsinWindow.print()

    //ReduceByeKeyAndWindow -> does the transformation on window
    val checkpoint_dir = GetAllProperties.readPropertyFile getOrElse("CHECKPOINT_DIR" ,"#") replace("<USER_NAME>", userName)

    steamingContext.checkpoint(checkpoint_dir)

    val countsOfUniqueIpsWithWindowFunc = res.map(rec => (rec._1,1)).reduceByKeyAndWindow(
      {(x,y) => x + y},
      {(x, y) => x - y},
      Seconds(30),
      Seconds(20)
    )

    countsOfUniqueIpsWithWindowFunc.print()

    // save  DStream to external locations.
    countsOfUniqueIpsWithWindowFunc.foreachRDD{
      rdd =>
        rdd.foreachPartition{
          //  // Open connection to storage system (e.g. a database connection)

          item =>   // Use connection to push item to system
        }
        //close the connection
    }

    //updateStateByKey() -> “infinitely growing” count since the beginning of the program.

    def updateRunningSum(values: Seq[String], state: Option[Int]) = {
      Some(state.getOrElse(0) + values.size)
    }

    // TODO: Need to implement the updateStateByKey
    //res.map(rec => (rec._1,1)).updateStateByKey[(String,Int)](updateRunningSum _ )

    // do the calculations like 1. particular ip address started surfing the website from time1 to time2.

  }

}
