package org.example;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TemperatureMapper extends Mapper<Object, Text, Text, Text> {

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] fields = line.split("\\t");

        if (fields.length == 6) { // city, date, temperature, humidity, rainfall, windspeed
            String city = fields[0];
            String date = fields[1];
            String temperature = fields[2].replace("°C", "").trim();
            String humidity = fields[3].replace("%", "").trim();
            String rainfall = fields[4].replace(" mm", "").trim();
            String windspeed = fields[5].replace(" kph", "").trim();

            // Chuyển đổi ngày sang định dạng năm
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy");
            try {
                Date parsedDate = inputFormat.parse(date);
                String year = outputFormat.format(parsedDate);

                // Key: city-year
                String cityYearKey = city + "-" + year;

                // Value: temperature, humidity, rainfall, windspeed
                String combinedValues = temperature + "\t" + humidity + "\t" + rainfall + "\t" + windspeed;

                context.write(new Text(cityYearKey), new Text(combinedValues));
            } catch (ParseException e) {
                System.err.println("Error parsing date: " + date);
                e.printStackTrace();
            }
        }
    }
}
