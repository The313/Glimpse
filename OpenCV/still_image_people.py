import cv2
import imutils
import numpy as np
from imutils import object_detection

cascadeHead = cv2.CascadeClassifier("haarcascade_fullbody.xml")
cascadeBody = cv2.CascadeClassifier("haarcascade_upperbody.xml")
cascadeFace = cv2.CascadeClassifier('haarcascade_frontalface_default.xml')
hog = cv2.HOGDescriptor()
hog.setSVMDetector(cv2.HOGDescriptor_getDefaultPeopleDetector())

 
def detect(gray, frame):
    body = cascadeBody.detectMultiScale(gray,1.05,1,1)
    head = cascadeHead.detectMultiScale(gray,1.05,1,1)
    face = cascadeFace.detectMultiScale(gray,1.05,1,1)
    for (x, y, w, h) in head:
        cv2.rectangle(frame, (x, y), (x+w, y+h), (0, 255, 0), 2)
        roi_gray = gray[y:y+h, x:x+w]
    for (bx, by, bw, bh) in body:
        cv2.rectangle(frame, (bx, by), (bx+bw, by+bh), (0, 0, 255), 2)
    for (hx, hy, hw, hh) in face:
        cv2.rectangle(frame, (hx, hy), (hx+hw, hy+hh), (255, 0, 0), 2)

    return frame

def detecthog(gray, frame):
    (rects, weights) = hog.detectMultiScale(gray, winStride=(2,2), padding=(16,16), scale=1.01)
    for (x, y, w, h) in rects:
        cv2.rectangle(frame,(x,y),(x+w,y+ h),(0,0,255),2)
    rects = np.array([[x, y, x + w, y + h] for (x, y, w, h) in rects])
    pick = object_detection.non_max_suppression(rects, probs=None, overlapThresh=0.85)
    for (xA, yA, xB, yB) in pick:
        cv2.rectangle(frame, (xA, yA), (xB, yB), (0, 255, 0), 2)
    return frame


	
image = cv2.imread('pedestrians.jpg')
image = imutils.resize(image, width=min(400, image.shape[1]))
orig = image.copy()
canvas = detecthog(image, image)
cv2.imshow("image",image)
#cv2.imshow("original", orig)
cv2.waitKey(0)


