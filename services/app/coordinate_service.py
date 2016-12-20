#!/usr/bin/env/python
# coding=utf-8
import time

from flask import Flask, jsonify
from data import coordinate_data

app = Flask(__name__)


@app.route("/<city>")
def coordinate(city):
    time.sleep(1)
    return jsonify(coordinate_data[city.lower()]['coords'])

if __name__ == "__main__":
    app.run(host='0.0.0.0', port=9080, debug=False)