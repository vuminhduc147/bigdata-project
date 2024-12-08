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

        if (fields.length == 6) { // Đảm bảo dòng có đúng 6 cột: city, date, temperature, humidity, rainfall, windspeed
            String city = fields[0]; // Lấy tên thành phố
            String date = fields[1]; // Lấy ngày
            String temperature = fields[2].replace("°C", "").trim(); // Lấy nhiệt độ và loại bỏ ký tự '°C'
            String humidity = fields[3].replace("%", "").trim(); // Lấy độ ẩm và loại bỏ ký tự '%'
            String rainfall = fields[4].replace(" mm", "").trim(); // Lấy lượng mưa và loại bỏ ký tự 'mm'
            String windspeed = fields[5].replace(" kph", "").trim(); // Lấy tốc độ gió và loại bỏ ký tự 'kph'

            // Chuyển đổi date thành định dạng tháng-năm
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat outputFormat = new SimpleDateFormat("MM/yyyy");
            try {
                Date parsedDate = inputFormat.parse(date);
                String month = outputFormat.format(parsedDate);

                // Gộp city và month thành key
                String cityMonthKey = city + "-" + month;

                // Gộp các giá trị lại thành chuỗi để truyền cho Reducer
                String combinedValues = temperature + "\t" + humidity + "\t" + rainfall + "\t" + windspeed;

                context.write(new Text(cityMonthKey), new Text(combinedValues));
            } catch (ParseException e) {
                System.err.println("Error parsing date: " + date);
                e.printStackTrace();
            }
        }
    }
}
