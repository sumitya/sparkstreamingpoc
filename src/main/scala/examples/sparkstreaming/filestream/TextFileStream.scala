package examples.sparkstreaming.filestream

import examples.sparkstreaming.utils.GetAllProperties
import org.apache.spark.streaming.StreamingContext

class TextFileStream {

  def fileStreamOperations(steamingContext:StreamingContext,userName:String) = {

    /*
 * All files must be in the same data format.
   A file is considered part of a time period based on its modification time, not its creation time.
   Once processed, changes to a file within the current window will not cause the file to be reread.
   That is: updates are ignored. The more files under a directory, the longer it will take to scan
   for changes — even if no files have been modified.
 * */

    //This is used for local development only.
    val source_dir = GetAllProperties.readPropertyFile getOrElse("SOURCE_DIR" ,"#") replace("<USER_NAME>", userName)

    val inputFile = steamingContext.textFileStream(source_dir)

    inputFile.foreachRDD(_.count())

  }

}
