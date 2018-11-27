import cv2
import imutils
import numpy as np
from imutils import object_detection

cascadeHead = cv2.CascadeClassifier("cascadeH5.xml")
cascadeBody = cv2.CascadeClassifier("cascadG.xml")
hog = cv2.HOGDescriptor()
hog.setSVMDetector(cv2.HOGDescriptor_getDefaultPeopleDetector())

 
def detect(gray, frame):
    body = cascadeBody.detectMultiScale(gray,1.1,1,0)
    head = cascadeHead.detectMultiScale(gray,1.1,1,0) 
    for (x, y, w, h) in head:
        cv2.rectangle(frame, (x, y), (x+w, y+h), (0, 255, 0), 2)
        roi_gray = gray[y:y+h, x:x+w]
    for (bx, by, bw, bh) in body:
        cv2.rectangle(frame, (bx, by), (bx+bw, by+bh), (255, 0, 0), 2)
    return frame

def detecthog(gray, frame):
    (rects, weights) = hog.detectMultiScale(gray, winStride=(4,4), padding=(16,16), scale=1.15)
    for (x, y, w, h) in rects:
        cv2.rectangle(frame,(x,y),(x+w,y+ h),(0,0,255),2)
    rects = np.array([[x, y, x + w, y + h] for (x, y, w, h) in rects])
    pick = object_detection.non_max_suppression(rects, probs=None, overlapThresh=0.65)
    for (xA, yA, xB, yB) in pick:
        cv2.rectangle(frame, (xA, yA), (xB, yB), (0, 255, 0), 2)

    return frame


	
video_capture = cv2.VideoCapture(0)

while True:
    _, frame = video_capture.read()
    gray = cv2.cvtColor(frame,cv2.COLOR_BGR2GRAY)
    gray = imutils.resize(gray, width=min(400, gray.shape[1]))
    frame = imutils.resize(frame, width=min(400, frame.shape[1]))
    canvas = detecthog(gray, frame)
    cv2.imshow('Video', canvas)
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break


video_capture.release()
cv2.destroyAllWindows() 
