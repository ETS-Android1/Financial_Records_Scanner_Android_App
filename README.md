# Group_12

## Members

| Full Name            | Seneca Email              | Skype Account                 | GitHub Username  | GitHub Email                  |
| -------------------- | ------------------------- | ----------------------------- | ---------------- | ----------------------------- |
| Parsa Jalilifar      | pjalilifar@myseneca.ca    | parsajalilifar@gmail.com      | Parsa-jalilifar  | parsajalilifar@gmail.com      |
| Shervin Tafreshipour | stafreshipour@myseneca.ca | shervintafreshipour@gmail.com | shervintafreshi  | shervintafreshipour@gmail.com |
| Cristian Maierean    | cmaierean@myseneca.ca     | bazernwow@gmail.com           | CristianMaierean | bazernwow@gmail.com           |



### Project Description:

Initially, our thought was to create an android application thats primary purpose was to scan users financial records (Mostly in the form of receipts and other 'receipt-like' documents) via the optical camera installed on the users smart phone, to analyze and extract the key pieces of monetary information. Once the financial details have been systematically extracted from the document, they will be stored in along with other documents the user has already scanned.Finally, when a user wishes to produce some sort of expenses spreadsheet detailing both calculated fields and the aggregated data retrieved from previous images.

Secondly, to facilitate the first functional requirement of the app, we decided to create a user-login system for our app. The users profiles would contain previously uploaded images, aggregated expenses spreadsheets and most likely some other financial data/trends we were able to extract from the user's uploaded images.

Frameworks:

1. Firebase() - This flexible database has provided some early success in our testing. Currently we are using it to store both user information, and any relevant images the user has uploaded.
2. Java(mostly Android and AndroidX libraries) - Java itself is great for android app design so we decided to use it to create the interface of our application.
3. Python(Flask, Pytesseract, opencv, caffe2) - Flask will be used for the developing the underlying image processing micro service, while Pytesseract is being utilized because of its accurate data extraction ability from both computer generated and natural photos.

The Problem:  
 Small businesses require a lot of receipts throughout the course of the year. Usually, someone has to go through every receipt, record the date, location, amount, HST, etc, and put it into an accounting category. This generally takes a lot of time and will in turn cost businesses more money.

Our App:
Use your Android device to take a picture of the receipt. Our app will use in house A.I Technology to recognize certain tokens such as date, location, amount, etc. Once tokens are found, user checks, edits, and selects a category. App places receipt into the correct category of the CSV file. All CSV files will be stored on our database and we can charge monthly.

At the end of the year, your accountant has to go through this large CSV file with all the categories and simply call functions like =SUM(), =AVERAGE(), =COUNT()...

All photos of the Raw receipt will be stored on our database and will be in the same folder/linked together with the CSV file line. Each line in the CSV file will contain an id that will be linked to its corresponding photo. Photos will be kept for evidence in case the physical receipt is lost or damaged. We will have all of the functions that a normal CSV file has.
Receipt Building App

HomePage:

- Will have main part displaying user info and other important information
- Button on the Left: Allows the Users to go through their photo's/receipt images. Inside of this there will be more functionality
- Middle Button: Allows user to upload/take photos. In this option, the user will be allowed to autofill info or manually enter it in.
- Button on the Right: Allows user to go to their csv main file and within this option there will be lots of functionality.

Photo's/Receipt Page:

- Should have a way of filtering/sorting all of these photos. By i.d, date of upload, date of purchase.
- All photos should be linked in the database to the right documentation in the csv file.
- User can create folder to store the images in and organize them better.

Upload/Take Photos:

- When the user clicks this, it should take them directly to the camera section where they can scan and take the photo.
- Once the photo is taken, the user has the choice of proceeding or retaking the photo
- If the image is good, the user can either chose to autofill the info through our automated system or they can manually enter the information into the system.
- Our database should automatically generate an id for every photo and link it to the information entered.
- If the user choses the autofill option, our ai will try its best to scan the name of the store, date of purchase, total amount, and hst spent.
- If the user wishes to add other information they can manually enter it in.
- If our autofill option cannot find a token it will alert the user and ask them to enter the information in manually.
- Once autofill is complete it will confirm all of the information with the user and give the user a choice of either fixing(manually entering) or saving it.
- Once the user chooses to save the image/info, they will be presented with a new scene showing all of their categories in the csv file. They will chose which category to save it in

CSV file:

- When the user clicks this, it will take them to their main csv file. (I will show you my actual csv file for reference)
- The user can easily go through each category and add info, delete info, replace info.
- We have to look into finding an open source excel type software that we can easily integrate into our app.
- Our csv file should be able to also perform calculations like excel can.

![image](https://user-images.githubusercontent.com/65363069/88753401-091dc900-d12a-11ea-905a-5f8e0d0e2e73.png)

