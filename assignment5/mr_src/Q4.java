package assignment5.assignment5;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeMap;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
public class Q4 {
public static class Map1 extends Mapper<LongWritable, Text, Text, Text>{
		
		@Override
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			String[] mydata = value.toString().split("\\^");
			context.write(new Text(mydata[1]),new Text("rev"));
		}
	}
	
	public static class Map2 extends Mapper<LongWritable, Text, Text, Text>{
		
		@Override
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			String[] mydata = value.toString().split("\\^");
			context.write(new Text(mydata[0]),new Text("user^" + mydata[1] ));
			
		}
	}

	public static class Reduce extends Reducer<Text,Text,Text,Text> {
		private HashMap<String,Integer> reviewMap = new HashMap<String,Integer>();
		private HashMap<String,String> userMap = new HashMap<String,String>();

		@Override
		public void reduce(Text userID, Iterable<Text> values, Context context ) throws IOException, InterruptedException {
			int sum=0;
			for (Text val : values) {
				String[] value=val.toString().split("\\^");
				if(value[0].compareTo("user")==0){
					userMap.put(userID.toString(), value[1]);
				}
				if(value[0].compareTo("rev")==0){
					sum++;
				}
			}
			reviewMap.put(userID.toString(), sum);
		}		
		
		@Override
		protected void cleanup(Context context) throws IOException,	InterruptedException {
	        ValueComparator bvc =  new ValueComparator(reviewMap);
	        int count=0;
	        TreeMap<String,Integer> sorted_map = new TreeMap<String,Integer>(bvc);
	        sorted_map.putAll(reviewMap);
			for(String userID : sorted_map.keySet()) {
				if(userMap.containsKey(userID)){
					count++;
					if(count>10)
						break;
					String userName=userMap.get(userID);
					context.write(new Text(userID),new Text(userName + "\t\t" + reviewMap.get(userID)));
				}
			}
		}
	}
	
	public static class ValueComparator implements Comparator<String> {

		HashMap<String,Integer> base;

		public ValueComparator(HashMap<String,Integer> base) {
			this.base = base;
		}

		public int compare(String a, String b) {
			if (base.get(a) >= base.get(b)) {
				return -1;
			} else {
				return 1;
			}
		}
	}

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		if (otherArgs.length !=3 ) {
			System.err.println("Usage: Q4 <path_to_review.csv> <path_to_user.csv> <out>");
			System.exit(2);
		}
		Job job = Job.getInstance(conf, "Q4");
		job.setJarByClass(Q2.class);
		MultipleInputs.addInputPath(job, new Path(otherArgs[0]), TextInputFormat.class ,Map1.class );
		MultipleInputs.addInputPath(job, new Path(otherArgs[1]),TextInputFormat.class,Map2.class );
		job.setReducerClass(Reduce.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[2]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}

}
