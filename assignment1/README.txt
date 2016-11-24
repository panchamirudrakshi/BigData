README 
ASSIGNMETN 1
----------------------

Method of execution :
---------------------

The program first downloads the textbooksinto the HDFS location specified by the user through command line. The urls are hardcoded.
Then the downloaded files which are in compressed format are decompressed and saved in the same location.

Command :
---------
//to download to hadoop
hadoop jar assignment1-0.0.1-SNAPSHOT.jar assignment.assignment.textdownload hdfs://cshadoop1/user/pgr150030
//to view the files
hdfs dfs -ls
//to view the file content
hdfs dfs -cat filename.txt

The compressed and decompressed files are saved in the same location (hdfs://cshadoop1/user/pgr150030)
