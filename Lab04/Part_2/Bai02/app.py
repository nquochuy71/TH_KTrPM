from flask import Flask

app = Flask(__name__)

@app.route('/')
def hello_docker():
    return "Hello, Docker Flask!"

if __name__ == '__main__':
    # Chạy ứng dụng trên tất cả các interface (0.0.0.0) và cổng 5000
    app.run(host='0.0.0.0', port=5000)