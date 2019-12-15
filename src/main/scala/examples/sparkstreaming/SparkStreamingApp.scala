package examples.sparkstreaming

import examples.sparkstreaming.utils.{Contexts, GetAllProperties}

object SparkStreamingApp extends App{

  val steamingContext = Contexts.SSC

  //steamingContext.socketTextStream("localhost",7777).map{_.toUpperCase}.print()



  /*
  * All files must be in the same data format.
    A file is considered part of a time period based on its modification time, not its creation time.
    Once processed, changes to a file within the current window will not cause the file to be reread.
    That is: updates are ignored. The more files under a directory, the longer it will take to scan
    for changes — even if no files have been modified.
  * */


  private val userName = System.getProperty("user.name")

  //This is used for local development only.
  val source_dir = GetAllProperties.readPropertyFile getOrElse("SOURCE_DIR" ,"#") replace("<USER_NAME>", userName)

  val inputFile = steamingContext.textFileStream(source_dir)

  inputFile.foreachRDD(_.count())



  steamingContext.start()

  steamingContext.awaitTermination()


}
