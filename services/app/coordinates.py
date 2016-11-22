#!/usr/bin/env/python
from flask import Flask
app = Flask(__name__)

@app.route("/")
def coordinates():
    return "Coordinantes!"

if __name__ == "__main__":
    app.run(host='0.0.0.0', port=9080, debug=False)