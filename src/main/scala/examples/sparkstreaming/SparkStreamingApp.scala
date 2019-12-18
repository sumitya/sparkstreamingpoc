package examples.sparkstreaming

import examples.sparkstreaming.filestream.TextFileStream
import examples.sparkstreaming.socketstream.SocketStream
import examples.sparkstreaming.utils.Contexts

object SparkStreamingApp extends App{

  private val userName = System.getProperty("user.name")

  val steamingContext = Contexts.SSC

  val socstream = new SocketStream

  socstream.socketStreamOperations(steamingContext,userName)

  val textFileStream = new TextFileStream

  //textFileStream.fileStreamOperations(steamingContext,userName)

  steamingContext.start()

  steamingContext.awaitTermination()


}
