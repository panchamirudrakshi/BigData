import org.apache.spark.graphx._
import org.apache.spark.rdd.RDD
import scala.util.MurmurHash

def cosineSimilarity(dotProduct : Double, ratingNorm : Double, rating2Norm : Double) = {
  if(ratingNorm==0 || rating2Norm==0){
	0
  }
  else{
  dotProduct / (ratingNorm * rating2Norm)
  }
}

def jaccardSimilarity(usersInCommon : Double, totalUsers1 : Double, totalUsers2 : Double) = {
  val union = totalUsers1 + totalUsers2 - usersInCommon
  usersInCommon / union
}

val wiki= sc.textFile("bigdata/gplus_combined.txt").coalesce(20);

val file = wiki.map(line => (line.split(" ")(0),line.split(" ")(1)))
val edgesRDD: RDD[(VertexId,VertexId)] = file.map(line =>(MurmurHash.stringHash(line._1.toString), MurmurHash.stringHash(line._2.toString)))

val verticesRDD = file.flatMap(x => Iterable(x._1, x._2)).distinct().map(line=>(MurmurHash.stringHash(line.toString).toLong, line))

val graph = Graph.fromEdgeTuples(edgesRDD, 1)

val vertexId = verticesRDD.filter(x=>x._2=="110213253066194075593").take(1)

val vertexKey = vertexId(0)._1

val thisSet = graph.edges.filter(x=>x.srcId==(vertexKey)).map(x=>x.dstId).toArray.toSet

//Finding jaccardSimilarity
val intersectVal = graph.edges.filter(x=>x.srcId!=(vertexKey)).map(x=>(x.srcId,x.dstId)).groupByKey().map(x=>(x._1,x._2.size, x._2.toSet.intersect(thisSet).size))

val jaccarDist = intersectVal.map(x=> (x._1, 1-jaccardSimilarity(x._3.toDouble, x._2.toDouble, thisSet.size.toDouble)))
jaccarDist.join(verticesRDD).sortBy(_._2._1, ascending=true).take(5)

//Finding cosineSimilarity
val cosDist = intersectVal.map(x=> (x._1, 1-cosineSimilarity(x._3.toDouble, Math.sqrt(x._2.toDouble), Math.sqrt(thisSet.size.toDouble))))
cosDist.join(verticesRDD).sortBy(_._2._1, ascending=true).take(5)


