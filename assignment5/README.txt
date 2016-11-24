Commands to run:

Question 1:

hadoop jar yelp.jar Q1 <path_to_review.csv> <path_to_business.csv> <out>

hadoop jar yelp.jar assignment5.assignment5.Q1 /yelpdatafall/review/review.csv /yelpdatafall/business/business.csv /user/pgr150030/mrq1
hdfs dfs -ls /user/pgr150030/mrq1
hdfs dfs -get /user/pgr150030/mrq1/part-r-00000
cat part-r-00000 > mrq1opt.txt
---------------------------------------------------------------------
Question 2:
hadoop jar yelp.jar Q2 <path_to_review.csv> <path_to_user.csv> <out> "<NameOfUser>"

hadoop jar yelp.jar assignment5.assignment5.Q2 /yelpdatafall/review/review.csv /yelpdatafall/user/user.csv /user/pgr150030/mrq2 "Shannon"
hdfs dfs -ls /user/pgr150030/mrq2
hdfs dfs -get /user/pgr150030/mrq2/part-r-00000
cat part-r-00000 > mrq2opt.txt
---------------------------------------------------------------------
Question 3:
hadoop jar yelp.jar Q3 <path_to_review.csv> <path_to_business.csv> <out>

hadoop jar yelp.jar assignment5.assignment5.Q3 /yelpdatafall/review/review.csv /yelpdatafall/business/business.csv /user/pgr150030/mrq3
hdfs dfs -ls /user/pgr150030/mrq3
hdfs dfs -get /user/pgr150030/mrq3/part-r-00000
cat part-r-00000 > mrq3opt.txt
---------------------------------------------------------------------
Question 4:
hadoop jar yelp.jar Q4 <path_to_review.csv> <path_to_user.csv> <out>

hadoop jar yelp.jar assignment5.assignment5.Q4 /yelpdatafall/review/review.csv /yelpdatafall/user/user.csv /user/pgr150030/mrq4
hdfs dfs -ls /user/pgr150030/mrq4
hdfs dfs -get /user/pgr150030/mrq4/part-r-00000
cat part-r-00000 > mrq4opt.txt
---------------------------------------------------------------------
Question 5:
hadoop jar yelp.jar Q5 <path_to_review.csv> <path_to_business.csv> <out>

hadoop jar yelp.jar assignment5.assignment5.Q5 /yelpdatafall/review/review.csv /yelpdatafall/business/business.csv /user/pgr150030/mrq4
hdfs dfs -ls /user/pgr150030/mrq5
hdfs dfs -get /user/pgr150030/mrq5/part-r-00000
cat part-r-00000 > mrq5opt.txt
