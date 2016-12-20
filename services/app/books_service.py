#!/usr/bin/env/python
import time

from data import books_data
from flask import Flask, jsonify, request

app = Flask(__name__)


@app.route('/books')
def books():
    time.sleep(1)

    the_book = books_data['books']

    title = request.args.get('title')
    if not title is None:
        the_book = filter(lambda book: title.lower() in book['title'].lower(), the_book)

    author = request.args.get('author')
    if not author is None:
        the_book = filter(lambda book: author.lower() in book['author'].lower(), the_book)

    return jsonify(the_book)


if __name__ == "__main__":
    app.run(host='0.0.0.0', port=9081, debug=False, threaded=True)
