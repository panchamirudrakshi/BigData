import org.apache.spark.graphx._
import org.apache.spark.rdd.RDD
import scala.util.MurmurHash
import breeze.linalg._
import java.lang.Math.{pow, sqrt}

val user_input= "100521671383026672718";

val featnamesfile = "bigdata/bgdata/" + user_input +".featnames";
val featfileName = "bigdata/bgdata/" + user_input +".feat";
val egofeatfileName = "bigdata/bgdata/" + user_input +".egofeat";

val featfile = sc.textFile(featfileName).coalesce(20);

val gendersplit = featfile.map(line=>line.split(" ")).map(x=> (x(0), x.slice(1, x.length))).map(x=> (x._1, x._2.map(_.toInt))).map(x=> ((x._2(0),x._2(1),x._2(2))))

val countgenders = gendersplit.reduce( (a,b) => (a._1+b._1, a._2+b._2, a._3+b._3) )

println("The gender split for the inputted user is: \n Male    Female    Not Specified\n "+ countgenders._1 +"     "+ countgenders._2 + "      " + countgenders._3 );


