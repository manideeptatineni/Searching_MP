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
public class Sort_config {

    public class ConfigSorting {

        public  void main(String[] args) throws IOException,
                InterruptedException, ClassNotFoundException {

            Path inputPath = new Path(args[0]);
            Path outputDir = new Path(args[1]);

            // Create configuration
            Configuration conf = new Configuration(true);

            // conf.set("friend", "10");

            // Create job
            Job job = new Job(conf, "SortingData_MP");
            job.setJarByClass(Sort_config.class);

            // Setup MapReduce
            job.setMapperClass(Sorting_MP_Mapper.class);
            job.setReducerClass(Sorting_MP_Reducer.class);
            job.setCombinerClass(Sorting_MP_Reducer.class);
            job.setNumReduceTasks(1);

            // Specify key / value
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(IntWritable.class);

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
}
