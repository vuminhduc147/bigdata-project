# Weather Analysis and Forecasting

This project aims to analyze and forecast weather conditions, focusing on temperature, humidity, rainfall, and wind speed in major cities of Vietnam. The project leverages Apache Hadoop for distributed data processing and machine learning models for weather prediction.

## Table of Contents

- [Overview](#overview)
- [Project Structure](#project-structure)
- [Data Collection](#data-collection)
- [Data Processing](#data-processing)
  - [Mapper](#mapper)
  - [Reducer](#reducer)
- [Weather Forecasting](#weather-forecasting)
- [Results](#results)
- [Usage](#usage)
- [Requirements](#requirements)
- [Contributing](#contributing)
- [License](#license)

## Overview

The goal of this project is to provide insights into weather patterns in Hanoi and Ho Chi Minh City, and to build a model that can predict future weather conditions. The data includes daily records of temperature, humidity, rainfall, and wind speed.

## Project Structure

The project directory is structured as follows:


## Data Collection

Data is collected from WeatherAPI, including daily temperature, humidity, rainfall, and wind speed for both Hanoi and Ho Chi Minh City. The data is formatted as follows:



## Data Processing

### Mapper

The `TemperatureMapper.java` reads the data and maps each record to a key-value pair, where the key is a combination of city and month, and the value includes temperature, humidity, rainfall, and wind speed.

### Reducer

The `TemperatureReducer.java` aggregates the data by key (city-month) and calculates the average temperature, humidity, rainfall, and wind speed for each month.

## Weather Forecasting

Using the processed data, machine learning models like Linear Regression, Random Forest, or LSTM can be applied to predict future weather conditions. This part of the project aims to forecast weather trends and provide valuable insights.

## Results

The processed data and forecasting results will be presented in the form of charts and graphs, providing a clear visualization of weather patterns and predictions.

## Usage

To run the project:

1. Compile the Java files:
   ```sh
   javac -classpath `hadoop classpath` -d . src/main/java/org/example/*.java

2. Create a JAR file:
   ```sh 
   jar cf weather-analysis.jar org/example/*.class
3. Run file
   ```sh 
   hadoop jar weather-analysis.jar org.example.TemperatureDriver <input path> <output path>

## Requirements
Java 8 or later

Apache Hadoop

Python 3.6 or later (for machine learning and data visualization)

Python libraries: pandas, matplotlib, scikit-learn, keras, tensorflow

## Contributing
Contributions are welcome! Please fork the repository and submit a pull request.

## License
This project is licensed under the MIT License - see the LICENSE file for details.




