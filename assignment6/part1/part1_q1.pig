A = LOAD 'review.csv' USING PigStorage('^') as (review_id:chararray, user_id:chararray, business_id:chararray, star:float);
B = LOAD 'business.csv' USING PigStorage('^') as (business_id:chararray, address:chararray,categories:chararray);
bg = GROUP A BY business_id;
ratingbg = FOREACH bg GENERATE group, AVG(A.star) as averagestars;
detailbus = JOIN B BY business_id, ratingbg BY group;
distinctB = DISTINCT detailbus;
starsort = ORDER distinctB BY ratingbg::averagestars DESC;
limitResults = LIMIT starsort 10;
result = FOREACH limitResults GENERATE B::business_id,B::address,B::categories,ratingbg::averagestars;
DUMP result;
STORE result INTO 'Question1';


