#!/bin/bash
latlon=$1
echo $latlon
javac -cp json-20200518.jar:. WeatherMain.java
java -cp json-20200518.jar;. WeatherMain $latlon