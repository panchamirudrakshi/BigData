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

public class Q1 {
public static class Map1 extends Mapper<LongWritable, Text, Text, Text>{
		
		@Override
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			String[] mydata = value.toString().split("\\^");
			context.write(new Text(mydata[2]),new Text("rev^"+mydata[3]));
		}
	}
	
	public static class Map2 extends Mapper<LongWritable, Text, Text, Text>{
		
		@Override
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			String[] mydata = value.toString().split("\\^");
			context.write(new Text(mydata[0]),new Text("bus^"+mydata[1]+ "^" +mydata[2]));
		}
	}

	public static class Reduce extends Reducer<Text,Text,Text,Text> {
		private HashMap<String,Double> reviewMap = new HashMap<String,Double>();
		private HashMap<String,String> businessMap = new HashMap<String,String>();

		@Override
		public void reduce(Text key, Iterable<Text> values, Context context ) throws IOException, InterruptedException {
			int count=0;
			int sum=0;
			for (Text val : values) {
				String[] value=val.toString().split("\\^");
				if(value[0].compareTo("bus")==0){
					businessMap.put(key.toString(), value[1] + "^" + value[2]);
				}
				if(value[0].compareTo("rev")==0){
					sum+=Double.parseDouble(value[1]);
					count++;
					reviewMap.put(key.toString(), (double)sum/count);
				}
			}
		}		
		
		@Override
		protected void cleanup(Context context) throws IOException,	InterruptedException {
	        ValueComparator bvc =  new ValueComparator(reviewMap);
	        TreeMap<String,Double> sorted_map = new TreeMap<String,Double>(bvc);
	        sorted_map.putAll(reviewMap);
	        int count=0;
			for(String businessID : sorted_map.keySet()) {
				if(businessMap.containsKey(businessID)){
					String addressAndCategoriesData=businessMap.get(businessID);
					count++;
					String data[] = addressAndCategoriesData.split("\\^");
					if(count>10)
						break;
					String address = String.format("%-50s", data[0]);
					String category = String.format("%-50s", data[1]);
					context.write(new Text(businessID),new Text(address + category +"\t" + reviewMap.get(businessID).toString()));
				}
			}
		}
	}
	
	public static class ValueComparator implements Comparator<String> {

		HashMap<String,Double> base;

		public ValueComparator(HashMap<String,Double> base) {
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
			System.err.println("Usage: Q1 <path_to_review.csv> <path_to_business.csv> <out>");
			System.exit(2);
		}
		Job job = Job.getInstance(conf, "Q1");
		job.setJarByClass(Q1.class);
		MultipleInputs.addInputPath(job, new Path(otherArgs[0]), TextInputFormat.class ,Map1.class );
		MultipleInputs.addInputPath(job, new Path(otherArgs[1]),TextInputFormat.class,Map2.class );
		job.setReducerClass(Reduce.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[2]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}

}
