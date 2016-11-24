A = LOAD 'review.csv' USING PigStorage('^') as (review_id:chararray, user_id:chararray, business_id:chararray, star:float);
B = LOAD 'business.csv' USING PigStorage('^') as (business_id:chararray, address:chararray,categories:chararray);
businessgp = GROUP A BY business_id;
businesscnt = FOREACH businessgp GENERATE group, COUNT(A.star) as countstars;
BusinessDetails = JOIN B BY business_id, businesscnt BY group;
distinctB = DISTINCT BusinessDetails;
TXB = FILTER distinctB BY (B::address MATCHES '.* TX .*');
result = FOREACH TXB GENERATE B::business_id,businesscnt::countstars;
DUMP result;
STORE result INTO 'Question5';

