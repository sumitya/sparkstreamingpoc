# sparkstreamingpoc
sparkstreamingpoc
Contains basic Examples of Spark Streaming with different stream data sources. It contains the DStream and Structured API implementation.

How to generate Apache Server Logs locally:-
1. Generate Apache Server Logs -> copy the logs file sample_apache_logs.log to bin directory.
2. run the log gen script `bash apache_log_gen.sh`
3. Run the progressive logs reader in another terminal `bash progressive_logs.sh`