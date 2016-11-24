Readme
-----------
PART-1( The files review.csv,user.csv,business.csv are placed locally )

--> Pig files are part1_q1.pig,part1_q2.pig,part1_q3.pig,part1_q4.pig,part1_q5.pig
    
Steps to execute Part 1 -Pig
-----------------------------
-->exec Question1.pig 
-->exec -param Fnamew=April -param Lname=S Question2.pig
-->exec Question3.pig
-->exec Question4.pig
-->exec Question5.pig

Outputs : part1_q1_result,part1_q2_result,part1_q3_result,part1_q4_result,part1_q5_result
===============================================================

PART-2
---------------
Input files are placed locally 

pig 

exec part2_pig.pig

Scala :

spark-shell -i part2_scala.scala


Ouput files for 5 letter words , 6 letter words, 7 letter words , 8 letter words are with time durations attached to the folder.


