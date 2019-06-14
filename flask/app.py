from flask import Flask, Response, request, jsonify, redirect, url_for, render_template,session, abort
import os
import pymongo
import json
from datetime import datetime


client = pymongo.MongoClient('db', 27017)
db = client['db1']
tmp = db['tmp']
levels = db['levels']
silo = db['silo']
app = Flask(__name__)
app.config["MONGO_URI"] = "mongodb://db:27017/db1"
app.config['SECRET_KEY'] = 'secret!'

#Sanity Check
@app.route("/ping")
def ping():
    app.logger.debug("Responded ping with pong")
    return "Pong"
@app.route("/")
def index():
    return "",401
@app.route("/upload/temperature/<float:temp>/<float:hum>")
def temp_upload(temp,hum):
    tmp.update_one({'time':datetime.now().hour},{'$set': {'temperature': temp, 'humidity': hum}}, upsert=False)
    return jsonify({'status': 'saved'})


@app.route("/get/all")
def all_get():
    all_data = list(tmp.find())
    temp=[x['temperature'] for x in all_data]
    temp[-1]=temp.find_one({'time':datetime.now().hour})['temperature']
    hum=[x['humidity'] for x in all_data]
    temp[-1]=temp.find_one({'time':datetime.now().hour})['humidity']
    all_data = list(levels.find())
    silo_all = list(silo.find())
    level_data=[x['level'] for x in all_data]
    level_data[-1]=levels.find_one({'time':datetime.now().hour})['level']
    silo_data=[x['silo_level'] for x in silo_all]
    silo_data[-1]=silo.find_one({'time':datetime.now().hour})['silo_level']

    return jsonify({'temperature': temp, 'humidity':hum, 'level': level_data, 'silo_level':silo_data})


@app.route("/get/temperature_humidity")
def temp_get():
    all_data = list(tmp.find())
    temp=[x['temperature'] for x in all_data]
    hum=[x['humidity'] for x in all_data]
    return jsonify({'temperature': temp, 'humidity':hum})


@app.route("/get/water_level")
def level_get():
    all_data = list(levels.find())
    level_data=[x['level'] for x in all_data]
    return jsonify({'level': level_data})

@app.route("/get/temperature/current")
def temp_get_current():
    data = tmp.find_one({'time':datetime.now().hour })
    return str(data['temperature'])

@app.route("/upload/water_level/<float:level>/")
def level_upload(level):
    levels.update_one({'time':datetime.now().hour},{'$set': {'level': level}}, upsert=False)
    return jsonify({'status': 'saved'})



if __name__ == "__main__":
    Flask.run(app, host='0.0.0.0', port=80, debug=True)
