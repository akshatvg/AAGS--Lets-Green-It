from pymongo import MongoClient
import json
import random 
client = MongoClient('localhost', 27017)
db = client['db1']
tmp = db['tmp']
levels = db['levels']
silo = db['sillo']
for x in range(24):
    tmp.insert_one({'time':x, 'temperature': random.randint(24,28), 'humidity': random.randint(30,80)})

for x in range(24):
    levels.insert_one({'time':x, 'level': 100-x*4})
for x in range(24):
    silo.insert_one({'time':x, 'silo_level': 87})


