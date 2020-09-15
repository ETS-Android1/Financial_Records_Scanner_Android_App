
## Back-End

The server itself will be hosted by Heroku through [Amazon's AWS](https://aws.amazon.com/), and the server will built on a [flask framework](https://flask.palletsprojects.com/en/1.1.x/). This back-end service is essentially communicating with the front-end mobile application to handle various requests. In order to properly tend to these requests, [Tesseract](https://pypi.org/project/pytesseract/) and [OpenCV2](https://opencv.org/), two [python](https://www.python.org/) frameworks which both have powerful OCR capabilities, will be used in to sufficiently analyze images and return relevant formatted financial documents.      

The app is using [Firebase Authentication](https://firebase.google.com/docs/auth) for login part of app and [Firebase database](https://firebase.google.com/docs/database) to store some information for instance image url, file url, user information,etc in addition of [Firebase storage](https://firebase.google.com/docs/storage?authuser=0) to save actual image and file. to make the app able to use login options like google and face book be side email and password options registration form developers.facebook.com also developers.google.com had be done. 
In other to get images from user and show them in recyclerview image libraries of [Android Image Cropper](https://github.com/ArthurHub/Android-Image-Cropper) and [picasso](https://github.com/square/picasso) had used.

## Front-End

[Adobe XD](https://www.adobe.com/ca/products/xd.html) was used firstly to design activities and fragments that will be used in our mobile app. [Android's XML](https://developer.android.com/guide/topics/ui/declaring-layout) is used to make our layouts just like home page, image page, file page ,and etc. The mobile app uses [Java](https://www.java.com/en/download/) as main language to make the connection among [Android's XML](https://developer.android.com/guide/topics/ui/declaring-layout) code and [Firebase](https://firebase.google.com/) features.


## Developing Tools

For the development of the back-end services, mainly [visual studio code](https://code.visualstudio.com/) will used in conjunction with several python extensions. 

To develop GUI and to Communicate with Firebase in our mobile app, [Android Studio](https://developer.android.com/studio) used as our main IDE.  
