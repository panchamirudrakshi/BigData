ASSIGNMENT 2

READ ME FILE:
Twitter Search API is used to download tweets about “INDIA”.
The tweets are downloaded for each day from 01/24/2016 to 01/29/2016.
The tweet count is set to 100 for each file so as to get a medium sized file.
Each tweet file is of approximately 10kb size. 
File 1: 10,897  bytes
File 2: 11,400  bytes
File 3: 6,796  bytes
File 4: 11,501  bytes
File 5: 10,623  bytes
File 6: 13,464  bytes

The output file (part-r-00000) is named outfile.txt

Files that are turned in:
1)	App.java
This contains code for downloading tweets from Twitter search API using Query- India. 6 files are generated at different timelines using setSince function. These files are: File1.txt, File2.txt, File3.txt, File4.txt, File5.txt, File6.txt
2)	Wordcount.java
This file contains code to count words starting with "#" and emit key value pair for them. 
3)	Outfile.txt
This file contains content of part-r-00000
Commands used:
// create a new directory
hdfs dfs -mkdir inassignment

// checks content 
hdfs dfs -ls

// copy tweet file from local to hdfs
hdfs dfs -copyFromLocal file1.txt /user/pgr150030/inassignment
hdfs dfs -copyFromLocal file2.txt /user/pgr150030/inassignment
hdfs dfs -copyFromLocal file3.txt /user/pgr150030/inassignment
hdfs dfs -copyFromLocal file4.txt /user/pgr150030/inassignment
hdfs dfs -copyFromLocal file5.txt /user/pgr150030/inassignment
hdfs dfs -copyFromLocal file6.txt /user/pgr150030/inassignment

// checks content of input directory
hdfs dfs -ls inassignment

//removes existing output files
rm part-r-00000

// running word count on the input file
hadoop jar assignment2-0.0.1-SNAPSHOT.jar assignment2.assignment2.WordCount /user/pgr150030/inassignment /user/pgr150030/outassignment

// gets the content to local
hdfs dfs -get /user/pgr150030/outassignment/part-r-00000

// to copy the content to a text file
cat part-r-00000>outfile.txt
