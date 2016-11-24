
A = LOAD 'review.csv' USING PigStorage('^') as (review_id:chararray, user_id:chararray, business_id:chararray, star:float);
B = LOAD 'business.csv' USING PigStorage('^') as (business_id:chararray, address:chararray,categories:chararray);
starbusiness = JOIN B BY business_id, A BY business_id;
stanfordbusiness = FILTER starbusiness BY (B::address MATCHES '.* Stanford .*');
distinctB = DISTINCT stanfordbusiness;
result = FOREACH distinctB GENERATE A::user_id, A::star;
DUMP result;
STORE result INTO 'Question3';

