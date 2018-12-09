import firebase_admin
from firebase_admin import db
from firebase_admin import credentials
import time

localtime = time.asctime(time.localtime(time.time()))
formattime = localtime[4:-5]

cred = credentials.Certificate('java1d-78754-firebase-adminsdk-tfjza-bbde21376c.json')
default_app = firebase_admin.initialize_app(cred, {'databaseURL' : "https://java1d-78754.firebaseio.com/"})

ref = db.reference('Basketball Courts')
users_ref = ref.child('Tampines')
users_ref.child('Tampines Hub').update({
        formattime: '4'
})
