package assignment5.assignment5;
import java.io.IOException;
import java.util.HashMap;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
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

public class Q3 {
public static class Map1 extends Mapper<LongWritable, Text, Text, Text>{
		
		@Override
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			String[] mydata = value.toString().split("\\^");
			context.write(new Text(mydata[2]),new Text("rev^"+mydata[1]+"^"+mydata[3]));
		}
	}
	
	public static class Map2 extends Mapper<LongWritable, Text, Text, Text>{
		
		@Override
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			String[] mydata = value.toString().split("\\^");
			if(mydata[1].toLowerCase().contains("Stanford".toLowerCase()))
				context.write(new Text(mydata[0]),new Text("bus^"));
			
		}
	}
	
	public static class Reduce extends Reducer<Text,Text,Text,Text> {
		private HashMap<String,String> reviewMap = new HashMap<String,String>();
		private HashMap<String,String> businessMap = new HashMap<String,String>();

		@Override
		public void reduce(Text businessID, Iterable<Text> values, Context context ) throws IOException, InterruptedException {
			String rating;
			String userID;
			for (Text val : values) {
				String[] value=val.toString().split("\\^");
				if(value[0].compareTo("bus")==0){
					businessMap.put(businessID.toString(), " ");
				}
				if(value[0].compareTo("rev")==0){
					userID = value[1];
					rating=value[2];
					reviewMap.put(businessID.toString(), userID + "^" + rating);
				}
			}
		}		
		
		@Override
		protected void cleanup(Context context) throws IOException,	InterruptedException {
			for(String businessID : reviewMap.keySet()) {
				if(businessMap.containsKey(businessID)){
					String userIDAndRating=reviewMap.get(businessID);
					String data[] = userIDAndRating.split("\\^");
					String userID = data[0];
					String rating = data[1];
					context.write(new Text(userID),new Text(rating));
				}
			}
		}
	}
	

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(new Configuration());
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		if (otherArgs.length !=3 ) {
			System.err.println("Usage: Q3 <path_to_review.csv> <path_to_business.csv> <out>");
			System.exit(2);
		}
		Job job = Job.getInstance(conf, "Q3");
		job.setJarByClass(Q3.class);
		MultipleInputs.addInputPath(job, new Path(otherArgs[0]), TextInputFormat.class, Map1.class );
		MultipleInputs.addInputPath(job, new Path(otherArgs[1]),TextInputFormat.class, Map2.class );
		job.setReducerClass(Reduce.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		// true stands for recursively deleting the folder you gave
		fs.delete(new Path(otherArgs[2]), true);
		
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[2]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);		
	}

}
