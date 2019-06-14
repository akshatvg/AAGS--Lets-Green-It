import Adafruit_DHT
import RPi.GPIO as GPIO
import time
import requests
GPIO.setmode(GPIO.BCM)
GPIO_TRIGGER = 23
GPIO_ECHO = 24
GPIO.setup(GPIO_TRIGGER, GPIO.OUT)
GPIO.setup(GPIO_ECHO, GPIO.IN)
 
def distance():
    GPIO.output(GPIO_TRIGGER, True)
    time.sleep(0.00001)
    GPIO.output(GPIO_TRIGGER, False)
 
    StartTime = time.time()
    StopTime = time.time()
 
    while GPIO.input(GPIO_ECHO) == 0:
        StartTime = time.time()
 
    while GPIO.input(GPIO_ECHO) == 1:
        StopTime = time.time()
 
    TimeElapsed = StopTime - StartTime
    distance = (TimeElapsed * 34300) / 2

    return distance
try:
    while True:
        humidity, temperature = Adafruit_DHT.read_retry(11, 4)
        temp_url='http://darsh.southindia.cloudapp.azure.com:8080/upload/temperature/'+str(temperature)+'/'+str(humidity)
        print(temp_url)
        print(requests.get(temp_url))
        print(requests.get('http://darsh.southindia.cloudapp.azure.com:8080/upload/water_level/'+str(distance())))
except KeyboardInterrupt:
    GPIO.cleanup()
    exit()

