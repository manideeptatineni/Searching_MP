/**
 * Created by Bannu on 21-09-2015.
 */
import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class Search_MP_Mapper extends Mapper<Object,Text, Text, Text> {

    private String local;
    private Text outkey = new Text();
    private static int linenum = 0;

    public void setup(Context context) throws InterruptedException, IOException {
        super.setup(context);
        local = ((FileSplit) context.getInputSplit()).getPath().toString();
    }

    public void map(Object key, Text value, Context context)
            throws IOException, InterruptedException {
        StringTokenizer st = new StringTokenizer(value.toString());
        linenum++;
        while (st.hasMoreTokens()) {
            outkey.set(local);
            context.write(outkey, new Text(linenum + "," + st.nextToken()));
        }
    }
}