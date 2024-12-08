package org.example;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class TemperatureReducer extends Reducer<Text, Text, Text, Text> {
    @Override
    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        double sumTemperature = 0.0;
        double sumHumidity = 0.0;
        double sumRainfall = 0.0;
        double sumWindspeed = 0.0;
        int count = 0;

        for (Text val : values) {
            String[] fields = val.toString().split("\\t");
            double temperature = Double.parseDouble(fields[0]);
            double humidity = Double.parseDouble(fields[1]);
            double rainfall = Double.parseDouble(fields[2]);
            double windspeed = Double.parseDouble(fields[3]);

            sumTemperature += temperature;
            sumHumidity += humidity;
            sumRainfall += rainfall;
            sumWindspeed += windspeed;
            count++;
        }

        double avgTemperature = sumTemperature / count;
        double avgHumidity = sumHumidity / count;
        double avgRainfall = sumRainfall / count;
        double avgWindspeed = sumWindspeed / count;

        String result = String.format("%.2f°C\t%.2f%%\t%.2fmm\t%.2fkph",
                                      avgTemperature, avgHumidity, avgRainfall, avgWindspeed);
        context.write(key, new Text(result)); // Key: city-year, Value: averages
    }
}
