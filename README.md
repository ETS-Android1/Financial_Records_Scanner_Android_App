# Group_12

## Members

| Full Name            | Seneca Email              | Skype Account                 | GitHub Username  | GitHub Email                  |
| -------------------- | ------------------------- | ----------------------------- | ---------------- | ----------------------------- |
| Parsa Jalilifar      | pjalilifar@myseneca.ca    | parsajalilifar@gmail.com      | Parsa-jalilifar  | parsajalilifar@gmail.com      |
| Shervin Tafreshipour | stafreshipour@myseneca.ca | shervintafreshipour@gmail.com | shervintafreshi  | shervintafreshipour@gmail.com |


### The Problem:  
Small businesses require a lot of receipts throughout the course of the year. Usually, someone has to go through every receipt, record the date, location, amount, HST, etc, and put it into an accounting category. This generally takes a lot of time and will in turn cost businesses more money.
 
### Project Description:

We will design an Android application which primary purpose is to scan users financial records (Mostly in the form of receipts and other 'receipt-like' documents) via the optical camera installed on the users smart phone, to analyze and extract the key pieces of monetary information. Once the financial details have been systematically extracted from the document, they will be stored in along with other documents the user has already scanned. Finally, users can create spreadsheets of recepits from different categories, from different times ranges, etc.

To facilitate the first functional requirement of the app, there will be a user-login system for our app. The users profiles would contain previously uploaded images, aggregated expenses spreadsheets and most likely some other financial data/trends we were able to extract from the user's uploaded images.

Frameworks:

1. Firebase() - This flexible database has provided some early success in our testing. Currently we are using it to store both user information, and any relevant images the user has uploaded.
2. Java(mostly Android and AndroidX libraries) - Java itself is great for android app design so we decided to use it to create the interface of our application.
3. Python(Flask, Pytesseract, opencv, caffe2) - Flask will be used for the developing the underlying image processing micro service, while Pytesseract is being utilized because of its accurate data extraction ability from both computer generated and natural photos.


Our App:
Use your Android device to take a picture of the receipt. Our app will use in house A.I Technology to recognize certain tokens such as date, location, amount, etc. Once tokens are found, user checks, edits, and selects a category. App places receipt into the correct category of the CSV file. All CSV files will be stored on our database and we can charge monthly.

At the end of the year, your accountant has to go through this large CSV file with all the categories and simply call functions like =SUM(), =AVERAGE(), =COUNT()...

All photos of the Raw receipt will be stored on our database and will be in the same folder/linked together with the CSV file line. Each line in the CSV file will contain an id that will be linked to its corresponding photo. Photos will be kept for evidence in case the physical receipt is lost or damaged. We will have all of the functions that a normal CSV file has.
Receipt Building App
