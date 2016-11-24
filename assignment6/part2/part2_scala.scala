//Part 2 (Scala)

    val begin=System.nanoTime
	val file1 = sc.textFile("shakespeare")
	val file2 = sc.textFile("bible")
    val words1 = file1.flatMap(line => line.split(" ")).filter(x => x.length==8)
    val words2 = file2.flatMap(line => line.split(" ")).filter(x => x.length==8)
	val wordCounts1 = words1.map(x => (x, 1)).reduceByKey(_ + _)
	val wordCounts2 = words2.map(x => (x, 1)).reduceByKey(_ + _)
	val combinewordcounts = wordCounts1.join(wordCounts2)
	val Totalwordcounts=combinewordcounts.map(a=>(a._1,a._2._1+a._2._2)).collect 
	val Top10words = Totalwordcounts.sortWith(_._2 >_._2).take(10)
	val Top10wordresult = sc.parallelize(Top10words).collect
	val end=System.nanoTime
	val duration=(end-begin)/1000000000.0

