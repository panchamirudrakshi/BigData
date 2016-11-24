import org.apache.spark.graphx._
import org.apache.spark.rdd.RDD
import scala.util.MurmurHash
import breeze.linalg._
import java.lang.Math.{pow, sqrt}

 def cosineSimilarity(dotProduct : Double, ratingNorm : Double, rating2Norm : Double) = {
    if(ratingNorm==0 || rating2Norm==0){
	0
    }
    else{
    	dotProduct / (ratingNorm * rating2Norm)
    }
  }

val user_input= "100521671383026672718";

val featnamesfile = "bigdata/bgdata/" + user_input +".featnames";
val featfileName = "bigdata/bgdata/" + user_input +".feat";

val featfile = sc.textFile(featfileName).coalesce(20);

val featsplit = featfile.map(line=>line.split(" ")).map(x=> (x(0), x.slice(1, x.length))).map(x=> (x._1, x._2.map(_.toDouble))).map(x=> (x._1, new DenseVector(x._2)));

val egofeatfile = sc.textFile(egofeatfileName).coalesce(20);

val egofeatsplit = egofeatfile.map(line=>line.split(" ")).map(x=> (x.slice(0, x.length))).map(x=> (x.map(_.toDouble)));

val getFirstRow = egofeatsplit.first

val append_featsplit = featsplit.map(x=> (x._1, x._2, new DenseVector(getFirstRow)))

val dotprod_featsplit = append_featsplit.map(x=> (x._1, x._2.reduceLeft(_+_) ,x._3.reduceLeft(_+_), x._2.dot(x._3)))

val cosineSim = dotprod_featsplit.map(x=> (x._1, cosineSimilarity(x._4, sqrt(x._2), sqrt(x._3)))).sortBy(_._2, ascending=false)

println("Top 5 most similar users to inputted user are ");
cosineSim.take(5)
