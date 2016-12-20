#!/usr/bin/env/python
# coding=utf-8
import time

from flask import Flask, jsonify
from data import personnummer_data

app = Flask(__name__)


@app.route("/<pnr>")
def coordinate(pnr):
    time.sleep(1)
    return jsonify(personnummer_data[pnr.lower()])

if __name__ == "__main__":
    app.run(host='0.0.0.0', port=9082, debug=False)