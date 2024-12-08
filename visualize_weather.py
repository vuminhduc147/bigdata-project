import pandas as pd
import matplotlib.pyplot as plt

# Đọc dữ liệu từ file Hadoop output
data = pd.read_csv("output.txt", sep="\t", names=["City-Year", "Temperature", "Humidity", "Rainfall", "Windspeed"])

# Tách "City-Year" thành hai cột "City" và "Year"
data[['City', 'Year']] = data["City-Year"].str.split("-", expand=True)

# Loại bỏ ký hiệu đơn vị khỏi các cột số liệu
data["Temperature"] = data["Temperature"].str.replace("°C", "").astype(float)
data["Humidity"] = data["Humidity"].str.replace("%", "").astype(float)
data["Rainfall"] = data["Rainfall"].str.replace("mm", "").astype(float)
data["Windspeed"] = data["Windspeed"].str.replace("kph", "").astype(float)
data["Year"] = data["Year"].astype(int)  # Chuyển đổi Year thành kiểu số

# Kiểm tra dữ liệu sau khi xử lý
print(data.head())

# Vẽ biểu đồ nhiệt độ trung bình theo năm cho từng thành phố
plt.figure(figsize=(10, 6))
for city in data["City"].unique():
    city_data = data[data["City"] == city]
    plt.plot(city_data["Year"], city_data["Temperature"], marker='o', label=city)

# Định dạng biểu đồ
plt.title("Average Temperature per Year by City", fontsize=16)
plt.xlabel("Year", fontsize=12)
plt.ylabel("Average Temperature (°C)", fontsize=12)
plt.legend(title="City", fontsize=10)
plt.grid(True)
plt.tight_layout()

# Hiển thị biểu đồ
plt.show()
