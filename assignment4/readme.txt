README file:

BIG DATA Assignment 4: 
=================================================

jar file: C:\Users\Dell\workspace\assignment4\target\assignment4-0.0.1-SNAPSHOT.jar

source files are present in "C:\Users\Dell\workspace\assignment4\src\main\java\assignment4\assignment4" directory.

There are the following files in the above directoty. 
1. question1.java -> File to create in-mapper combiner and compare time with traditional Wordcount method.
2. recrnce.java and pairedtext.java-> Program to implement Stripes approach

Question 1:
-----------
Command:
		
//to download books to hadoop
hadoop jar assignment-0.0.1-SNAPSHOT.jar assignment.assignment.textdownload hdfs://cshadoop1/user/pgr150030

books folder contains all the 6 books in txt format

Example:

start=$SECONDS
hadoop jar assignment4-0.0.1-SNAPSHOT.jar assignment4.assignment4.WordCount /user/pgr150030/books /user/pgr150030/optwr
duration=$((SECONDS - start))
echo $duration

start=$SECONDS
hadoop jar assignment4-0.0.1-SNAPSHOT.jar assignment4.assignment4.inmappercl /user/pgr150030/books /user/pgr150030/optinmpr
duration=$((SECONDS - start))
echo $duration


Time taken for traditional Wordcount to run: 27 seconds
Time taken for in-mapper combiner Wordcount to run: 18 seconds

The output word count for traditional approach is in "optwr.txt"
The output word count for in-mapper combiner approach is in "optinmpr.txt"

------------------------------------------------------------------------------------------------------------------

Question 2:
-----------
Command:
hadoop jar assignment4-0.0.1-SNAPSHOT.jar assignment4.assignment4.recrnce /user/pgr150030/books /user/pgr150030/output2 -skip stop_words.txt


// gets the content to local
hdfs dfs -get /user/pgr150030/output2/part-00000

// to copy the content to a text file
cat part-00000>optinmpr.txt

This program taken 6 text files as input and outputs stripes of words.
It takes only words with lengh 6 while creating stripes.
It also takes stop_words.txt file to remove stop words.

Output is located at "optinmpr.txt"