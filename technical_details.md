
## Back-End

The server itself will be hosted by Heroku through Amazon's AWS, and the server will built on a flask framework. This back-end service is essentially
communicating with the front-end mobile application to handle various requests. In order to properly tend to these requests, Tesseract and OpenCV2,
two python frameworks which both have powerful OCR capabilities, will be used in to sufficiently analyze images and return relevant formatted 
financial documents.      

The app is using Firebase Authentication for login part of app and Firebase database to store some information for instance 
image url, file url, user information,etc in addition of Firebase storage to save actual image and file. to make the app able to use login options
like google and face book be side email and password options registration form developers.facebook.com also developers.google.com had be done. 
In other to get images from user and show them in recyclerview image libraries of Android Image Cropper and picasso had used.

## Front-End

Adobe XD was used firstly to design activities and fragments that will be used in our mobile app. XML (It is similar to html) is used to make our
layouts just like home page, image page, file page ,and etc. The mobile app uses Java as main language to make the connection among XML code and 
firebase features.


## Developing Tools

For the development of the back-end services, mainly visual studio code will used in conjunction with several python extensions. 

To develop GUI and to Communicate with Firebase in our mobile app, Android Studio used as our main IDE.  
