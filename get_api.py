import requests
from datetime import datetime, timedelta

# WeatherAPI key (thay thế API_KEY bằng key thực tế của bạn)
api_key = 'API_KEY'

# Hàm lấy dữ liệu thời tiết từ API cho một ngày cụ thể
def fetch_weather_data(location, date):
    date_str = date.strftime("%Y-%m-%d")
    url = f'http://api.weatherapi.com/v1/history.json?key={api_key}&q={location}&dt={date_str}'
    response = requests.get(url)
    
    if response.status_code == 200:
        return response.json()
    else:
        print(f"Error fetching data for {location} on {date_str}: {response.status_code}")
        return None

# Hàm ghi dữ liệu thời tiết vào file
def write_to_file(data, filename):
    with open(filename, 'w') as file:
        # Ghi tiêu đề cột
        file.write("city\tday/month/year\ttemperature\thumidity\trainfall\twind_speed\n")
        # Ghi từng dòng dữ liệu
        for entry in data:
            file.write(f"{entry['city']}\t{entry['date']}\t{entry['temp']:.2f} °C\t"
                       f"{entry['humidity']:.1f}%\t{entry['rainfall']:.1f} mm\t"
                       f"{entry['wind_speed']:.1f} kph\n")
    print(f"Weather data has been written to {filename}")

# Hàm thu thập dữ liệu thời tiết từ nhiều thành phố trong khoảng thời gian cụ thể
def collect_weather_data(cities, start_date, end_date):
    current_date = start_date
    weather_data = []
    
    while current_date <= end_date:
        for city in cities:
            # Lấy dữ liệu từ API
            data = fetch_weather_data(city, current_date)
            if data:
                forecast = data['forecast']['forecastday'][0]['day']
                
                # Lấy các chỉ số cần thiết
                temp = forecast['avgtemp_c']
                humidity = forecast['avghumidity']
                rainfall = forecast['totalprecip_mm']
                wind_speed = forecast['maxwind_kph']
                
                # Thêm dữ liệu vào danh sách
                weather_data.append({
                    'city': city,
                    'date': current_date.strftime("%d/%m/%Y"),
                    'temp': temp,
                    'humidity': humidity,
                    'rainfall': rainfall,
                    'wind_speed': wind_speed
                })
        # Tăng ngày hiện tại lên 1
        current_date += timedelta(days=1)
    
    return weather_data

# Nhập dữ liệu từ người dùng
if __name__ == "__main__":
    # Lấy danh sách thành phố và ngày từ người dùng
    cities = input("Enter the locations separated by commas (e.g., Hanoi, Ho Chi Minh City): ").split(", ")
    start_date_str = input("Enter the start date (dd/mm/yyyy): ")
    end_date_str = input("Enter the end date (dd/mm/yyyy): ")
    
    # Chuyển đổi ngày sang định dạng datetime
    start_date = datetime.strptime(start_date_str, "%d/%m/%Y")
    end_date = datetime.strptime(end_date_str, "%d/%m/%Y")
    
    # Thu thập dữ liệu thời tiết
    weather_data = collect_weather_data(cities, start_date, end_date)
    
    # Ghi dữ liệu vào file
    write_to_file(weather_data, 'south_test.txt')
