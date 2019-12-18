package examples.sparkstreaming.parser

case class LogRecord( ipAddress: String, timestamp:String )

class ParseLogLine extends Serializable {

  def parse(logLine: String): LogRecord = {

    val PATTERNIP = s"""(?:[0-9]{1,3}\\.){3}[0-9]{1,3}""".r
    val PATTERNTM = s"""[0-9]{1,2}\\/[a-zA-Z]{3}\\/[0-9]{4}:[0-9]{2}:[0-9]{2}:[0-9]{2} (\\+|-)[0-1][0-9]{3}""".r

    val ip = PATTERNIP.findFirstIn(logLine)
    val timestamp = PATTERNTM.findFirstIn(logLine)

    if (ip.isEmpty) {
      println("Rejected Log Line: ")
      LogRecord("-", "-")
    }
    else {

      LogRecord(ip.get, timestamp.get)
    }
  }

}