package org.example;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TemperatureMapper extends Mapper<Object, Text, Text, DoubleWritable> {

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] fields = line.split("\\t"); // Tách các cột theo ký tự tab

        // Kiểm tra dòng input hợp lệ
        if (fields.length != 3) {
            System.err.println("Invalid input line: " + line);
            return;
        }

        String city = fields[0].trim(); // Lấy tên thành phố
        String date = fields[1].trim(); // Lấy ngày
        double temperature;

        // Xử lý lỗi nhiệt độ không hợp lệ
        try {
            temperature = Double.parseDouble(fields[2].replace("°C", "").trim());
        } catch (NumberFormatException e) {
            System.err.println("Invalid temperature: " + fields[2]);
            return;
        }

        // Chuyển đổi date thành định dạng tháng
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM");
        try {
            Date parsedDate = inputFormat.parse(date);
            String month = outputFormat.format(parsedDate);

            // Gộp city và month thành key
            String cityMonthKey = city + "\t" + month; // Sử dụng ký tự tab làm phân tách
            context.write(new Text(cityMonthKey), new DoubleWritable(temperature));
        } catch (ParseException e) {
            System.err.println("Error parsing date: " + date);
            e.printStackTrace();
        }
    }
}
