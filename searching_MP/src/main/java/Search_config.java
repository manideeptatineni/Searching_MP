/**
 * Created by Bannu on 21-09-2015.
 */
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;


public class Search_config {

    public static String KEY_WORD = "bannu";

    public static void main(String[] args) throws IOException,
            InterruptedException, ClassNotFoundException {

        Path inputPath = new Path(args[0]);
        Path outputDir = new Path(args[1]);

// Create configuration
        Configuration conf = new Configuration(true);

        conf.set(KEY_WORD, args[2]);

// Create job
        Job job = new Job(conf, "Searching");
        job.setJarByClass(Search_MP_Mapper.class);

// Setup MapReduce
        job.setMapperClass(Search_MP_Mapper.class);
        job.setReducerClass(Seraching_MP_Reducer.class);
//job.setCombinerClass(SearchingReducer.class);
        job.setNumReduceTasks(1);

// Specify key / value
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

// Input
        FileInputFormat.addInputPath(job, inputPath);
        job.setInputFormatClass(TextInputFormat.class);

// Output
        FileOutputFormat.setOutputPath(job, outputDir);
        job.setOutputFormatClass(TextOutputFormat.class);

// Delete output if exists
        FileSystem hdfs = FileSystem.get(conf);
        if (hdfs.exists(outputDir))
            hdfs.delete(outputDir, true);

// Execute job
        int code = job.waitForCompletion(true) ? 0 : 1;
        System.exit(code);

    }
}
