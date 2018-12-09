import numpy as np
import tensorflow as tf
import cv2
import time
import imutils
import firebase_admin
from firebase_admin import db
from firebase_admin import credentials
cred = credentials.Certificate('C:/Users/LattePanda/Desktop/opencv/java1d-78754-firebase-adminsdk-tfjza-bbde21376c.json')
default_app = firebase_admin.initialize_app(cred, {'databaseURL' : "https://java1d-78754.firebaseio.com/"})
ref = db.reference('meeting_room1')

class DetectorAPI:
    def __init__(self, path_to_ckpt):
        self.path_to_ckpt = path_to_ckpt

        self.detection_graph = tf.Graph()
        with self.detection_graph.as_default():
            od_graph_def = tf.GraphDef()
            with tf.gfile.GFile(self.path_to_ckpt, 'rb') as fid:
                serialized_graph = fid.read()
                od_graph_def.ParseFromString(serialized_graph)
                tf.import_graph_def(od_graph_def, name='')

        self.default_graph = self.detection_graph.as_default()
        self.sess = tf.Session(graph=self.detection_graph)

        # Definite input and output Tensors for detection_graph
        self.image_tensor = self.detection_graph.get_tensor_by_name('image_tensor:0')
        # Each box represents a part of the image where a particular object was detected.
        self.detection_boxes = self.detection_graph.get_tensor_by_name('detection_boxes:0')
        # Each score represent how level of confidence for each of the objects.
        # Score is shown on the result image, together with the class label.
        self.detection_scores = self.detection_graph.get_tensor_by_name('detection_scores:0')
        self.detection_classes = self.detection_graph.get_tensor_by_name('detection_classes:0')
        self.num_detections = self.detection_graph.get_tensor_by_name('num_detections:0')

    def processFrame(self, image):
        # Expand dimensions since the trained_model expects images to have shape: [1, None, None, 3]
        image_np_expanded = np.expand_dims(image, axis=0)
        (boxes, scores, classes, num) = self.sess.run(
            [self.detection_boxes, self.detection_scores, self.detection_classes, self.num_detections],
            feed_dict={self.image_tensor: image_np_expanded})
        im_height, im_width,_ = image.shape
        boxes_list = [None for i in range(boxes.shape[1])]
        for i in range(boxes.shape[1]):
            boxes_list[i] = (int(boxes[0,i,0] * im_height),
                        int(boxes[0,i,1]*im_width),
                        int(boxes[0,i,2] * im_height),
                        int(boxes[0,i,3]*im_width))

        return boxes_list, scores[0].tolist(), [int(x) for x in classes[0].tolist()], int(num[0])

    def close(self):
        self.sess.close()
        self.default_graph.close()

if __name__ == "__main__":
    model_path = 'C:/Users/LattePanda/Desktop/opencv/faster_rcnn_inception_v2_coco_2018_01_28/frozen_inference_graph.pb'
    odapi = DetectorAPI(path_to_ckpt=model_path)
    threshold = 0.7

    video_capture = cv2.VideoCapture(0)
    start_time = time.time()
    while True:
        _, img = video_capture.read()
        end_time = time.time();
        if (((int(round(end_time - start_time)))%30) == 0):
            
            #img = cv2.imread("C:/Users/LattePanda/Desktop/opencv/pedestrians4.jpg")
            img = imutils.resize(img, width = 640, height = 480)
            #img2 = "pedestrians1.jpg"

            boxes, scores, classes, num = odapi.processFrame(img)
            #boxes, scores, classes, num = odapi.processFrame(img2)

            # Visualization of the results of a detection.

            count = 0
            for i in range(len(boxes)):
                # Class 1 represents human
                if classes[i] == 1 and scores[i] > threshold:
                    box = boxes[i]
                    count += 1
                    cv2.rectangle(img,(box[1],box[0]),(box[3],box[2]),(255,0,0),2)
                    
            localtime = time.asctime(time.localtime(time.time()))
            formatdate = localtime[4:10]
            formattime = localtime[11:-5]
            #findchild = users_ref.child('space_bar')
            ref.set({
                    formatdate: {
                        formattime:count
                        }
            })
            
            cv2.imshow("preview", img)
            #cv2.imshow("preview2", img2)
        key = cv2.waitKey(1)
        if (key & 0xFF == ord('q')):
            break
    video_capture.release()
    cv2.destroyAllWindows()

