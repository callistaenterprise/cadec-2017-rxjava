#!/usr/bin/env/python
import time

from data import weather_data
from flask import Flask, jsonify
app = Flask(__name__)

@app.route('/<place>')
def weather(place):
    time.sleep(4)
    coords = weather_data[place]['coords']
    return jsonify(coords)

if __name__ == "__main__":
    app.run(host='0.0.0.0', port=9081, debug=False, threaded=True)