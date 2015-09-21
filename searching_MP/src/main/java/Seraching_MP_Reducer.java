/**
 * Created by Bannu on 21-09-2015.
 */
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.io.IntWritable;

import java.io.IOException;

public class Seraching_MP_Reducer extends Reducer<Text, Text, Text, IntWritable>{
    private static String tosearchword = "";
    public void setup(Context context) throws InterruptedException, IOException {
        tosearchword = context.getConfiguration().get(Search_config.KEY_WORD);
    }
    public void reduce(Text keyIn, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {
        for (Text val : values) {
            String text = val.toString();
            if (text.contains(tosearchword)) {
                String[] contains = text.split(" , ");
                context.write(keyIn, new IntWritable(Integer.parseInt(contains[0])));
            }
        }
    }
}
