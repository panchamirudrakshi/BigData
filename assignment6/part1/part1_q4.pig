
A = LOAD 'review.csv' USING PigStorage('^') as (review_id:chararray, user_id:chararray, business_id:chararray, star:float);
C = LOAD 'user.csv' Using PigStorage('^') AS (user_id:chararray, name:chararray, url:chararray);
usersgp = GROUP A BY user_id;
UsersCounts = FOREACH usersgp GENERATE group, COUNT(A.star) as countstars;
usersdetail =JOIN C BY user_id, UsersCounts BY group;
sortCounts = ORDER usersdetail BY UsersCounts::countstars DESC;
limitResults = LIMIT sortCounts 10;
result = FOREACH limitResults GENERATE C::user_id, C::name, UsersCounts::countstars;
DUMP result;
STORE result INTO 'Question4'; 

