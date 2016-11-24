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

val featsplit = featfile.map(line=>line.split(" ")).map(x=> (x(0), x.slice(1, x.length))).map(x=> (x._1, x._2.map(_.toDouble))).map(x=> (x._1, new DenseVector(x._2)));


val featnamefile = sc.textFile(featnamesfile).coalesce(20);

val filterforplaces = featnamefile.map( line => line.replaceFirst(" ", "-")).map(line => line.split("-")).filter(x=> x(1).contains("place:")).map(x => (x(0), x(1).replace("place:", "") ))

//filtering based on university
val filterforuniv = featnamefile.map( line => line.replaceFirst(" ", "-")).map(line => line.split("-")).filter(x=> x(1).contains("university:")).map(x => (x(0), x(1).replace("university:", "") ))


//reusing featsplit from above
val sum_feat = featsplit.map(x=>x._2).reduce((x,y) => (x+y))

println("Friends of inputted user in various locations are")
filterforplaces.collect().foreach( x=> {
		val d = sum_feat.valueAt(x._1.toInt)
		println( x._2 + " : " + d )
	})
