#!/bin/bash
latlon=$1
javac -cp json-20200518.jar WeatherMain.java
echo $latlon
java WeatherMain $latlon