from flask import Flask, request, jsonify
import numpy as np
import cv2
from tensorflow.keras.models import load_model
from tensorflow.keras.preprocessing import image
import os

app = Flask(__name__)

model = load_model('D:\PredictingPlant\model_files\model.h5')
model.load_weights('D:\PredictingPlant\model_files\my_model_weights.h5')

label_mapping = {0: 'Chrysanthemum', 1: 'Orchid', 2: 'Rose', 3: 'Sunflower'}
dir_path = 'E:/dataset2/test'

@app.route('/predict-plant/<path:image_path>', methods=['POST'])
def predict_plant(image_path):
    img = image.load_img(os.path.join(dir_path, image_path), target_size=(200, 200))
    img = image.img_to_array(img)
    img = np.expand_dims(img, axis=0)

    prediction = model.predict(img)
    
    predicted_class = np.argmax(prediction)
    predicted_label = label_mapping[predicted_class]
    print (predicted_label)
    
    return jsonify(predicted_label)

@app.route('/hello', methods=['GET'])
def hello():
    return jsonify({'message': 'Hello, this is a GET request!'})

if __name__ == '__main__':
    app.run(debug=True)
