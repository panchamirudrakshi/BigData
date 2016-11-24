import org.apache.spark.graphx._
import org.apache.spark.rdd.RDD
import scala.util.MurmurHash
import breeze.linalg._
import java.lang.Math.{pow, sqrt}

//************************ Top 5 Followers using the Followers Input Files ***************
val followers_files = sc.wholeTextFiles("bigdata/bgdata/*.followers").coalesce(20);

val count_followers = followers_files.map(x=> (x._1.split("/")  , x._2.split("\n") )).map(x=> ((x._1(x._1.length-1)) , x._2.length))

val filter_filename= count_followers.map(x=> (x._1.split("\\."), x._2)).map(x=> ((x._1(0)), x._2)).sortBy(_._2, ascending=false)

filter_filename.take(5)

//********res0: Array[(String, Int)] = Array((116899029375914044550,9960), (101560853443212199687,9949), (108404515213153345305,9938), (113455290791279442483,9907), (116247667398036716276,9902))

