package org.example;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class TemperatureReducer extends Reducer<Text, DoubleWritable, Text, Text> {
    public void reduce(Text key, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException {
        // Tách City và Month/Year từ key
        String[] cityMonth = key.toString().split("-");
        String city = cityMonth[0];
        String monthYear = cityMonth[1];

        // Tính tổng nhiệt độ và số lượng giá trị
        double sum = 0.0;
        int count = 0;

        for (DoubleWritable val : values) {
            sum += val.get();
            count++;
        }

        // Tính trung bình nhiệt độ
        double averageTemperature = sum / count;

        // Định dạng kết quả
        String result = monthYear + "\t" + String.format("%.2f", averageTemperature) + "°C";

        // Xuất kết quả với định dạng: City    Month/Year    AverageTemperature
        context.write(new Text(city), new Text(result));
    }
}
