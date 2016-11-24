// exec -param Fnamew=April -param Lname=S Question2.pig

A = LOAD 'review.csv' USING PigStorage('^') as (review_id:chararray, user_id:chararray, business_id:chararray, star:float);
C = LOAD 'user.csv' Using PigStorage('^') AS (user_id:chararray, name:chararray, url:chararray);
usersgp = GROUP A BY user_id;
userscnt = FOREACH usersgp GENERATE group, AVG(A.star) as countstars;
usersdetail =JOIN C BY user_id, userscnt BY group;
UserFilter = FILTER usersdetail BY (C::name MATCHES '.*$Fname\\s$Lname.*');
result = FOREACH UserFilter GENERATE C::user_id, C::name, userscnt::countstars;
DUMP result;
STORE result INTO 'Question2'; 

