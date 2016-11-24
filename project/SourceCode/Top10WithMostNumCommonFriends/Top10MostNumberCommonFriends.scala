import org.apache.spark.graphx._
import org.apache.spark.rdd.RDD
import scala.util.MurmurHash

//file location
val wiki= sc.textFile("bigdata/gplus_combined.txt").coalesce(20);

//Create Edges and Vertices RDDs for the Graph
val file = wiki.map(line => (line.split(" ")(0),line.split(" ")(1)))
//edgesRDD has a list of edges(with vertex id converted to a hashvalue)
val edgesRDD: RDD[(VertexId,VertexId)] = file.map(line =>(MurmurHash.stringHash(line._1.toString), MurmurHash.stringHash(line._2.toString)))

val verticesRDD = file.flatMap(x => Iterable(x._1, x._2)).distinct().map(line=>(MurmurHash.stringHash(line.toString).toLong, line))

//Creating the Graph
val graph = Graph.fromEdgeTuples(edgesRDD, 1)

//We filter for only one user so that our systems don't get overloaded
val vertexId = verticesRDD.filter(x=>x._2=="110213253066194075593").take(1)

val vertexKey = vertexId(0)._1

//Create  a set from this array
val thisSet = graph.edges.filter(x=>x.srcId==(vertexKey)).map(x=>x.dstId).toArray.toSet

//Get the top 10 people with most common Friends
val commonFriends = graph.edges.filter(x=>x.srcId!=(vertexKey)).map(x=>(x.srcId,x.dstId)).groupByKey().map(x=>(x._1, x._2.toSet.intersect(thisSet).size))
val mostCommonFriends = commonFriends.join(verticesRDD).sortBy(_._2._1, ascending=false)

mostCommonFriends.take(10)
