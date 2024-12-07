import requests
import json
from datetime import datetime, timedelta

# Your WeatherAPI key
api_key = 'API_KEY'

# Function to fetch weather data for a specific date
def fetch_weather_data(location, date):
    date_str = date.strftime("%Y-%m-%d")
    url = f'http://api.weatherapi.com/v1/history.json?key={api_key}&q={location}&dt={date_str}'
    response = requests.get(url)
    if response.status_code == 200:
        return response.json()
    else:
        print(f'Error fetching data for {date_str} in {location}:', response.status_code)
        return None

# Function to write weather data to a file
def write_to_file(data, filename):
    with open(filename, 'w') as file:
        file.write("city\tday/month/year\ttemperature\n")
        for entry in data:
            file.write(f"{entry['city']}\t{entry['date']}\t{entry['temp']:.2f} °C\n")
    print(f'Weather data has been written to {filename}')

# Function to collect weather data over a range of dates for multiple cities
def collect_weather_data(cities, start_date, end_date):
    current_date = start_date
    weather_data = []
    while current_date <= end_date:
        for city in cities:
            data = fetch_weather_data(city, current_date)
            if data:
                date_str = current_date.strftime("%d/%m/%Y")
                temp = data['forecast']['forecastday'][0]['day']['avgtemp_c']
                weather_data.append({'city': city, 'date': date_str, 'temp': temp})
        current_date += timedelta(days=1)
    return weather_data

# Get cities and date range input from user
cities = input("Enter the locations separated by commas (e.g., Hanoi, Ho Chi Minh City): ").split(", ")
start_date_str = input("Enter the start date (dd/mm/yyyy): ")
end_date_str = input("Enter the end date (dd/mm/yyyy): ")

# Convert input dates to datetime objects
start_date = datetime.strptime(start_date_str, "%d/%m/%Y")
end_date = datetime.strptime(end_date_str, "%d/%m/%Y")

# Fetch data and write to file
weather_data = collect_weather_data(cities, start_date, end_date)
write_to_file(weather_data, 'temperature.txt')
