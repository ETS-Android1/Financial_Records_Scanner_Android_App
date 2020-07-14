# Group_12

## Members

| Full Name             | Seneca Email               | Skype Account                  | GitHub Username |  GitHub Email                  |
| --------------------- | -------------------------- | ------------------------------ | --------------- | ------------------------------ |
| Parsa Jalilifar       | pjalilifar@myseneca.ca     | parsajalilifar@gmail.com       | Parsa-jalilifar | parsajalilifar@gmail.com       |
| Shervin Tafreshipour  | stafreshipour@myseneca.ca  | shervintafreshipour@gmail.com  | shervintafreshi | shervintafreshipour@gmail.com  |
| Cristian Maierean     | cmaierean@myseneca.ca      | bazernwow@gmail.com            | CristianMaierean| bazernwow@gmail.com            |

## Ideas 

### Primary Idea:  
                   Initially, our thought was to create an android application thats
                  primary purpose was to scan users financial records (Mostly in the
                  form of receipts and other 'receipt-like' documents) via the optical 
                  camera installed on the users smart phone, to analyze and extract the
                  key pieces of monetary information. Once the financial details have
                  been systematically extracted from the document, they will be stored in 
                  along with other documents the user has already scanned. Finally, when a
                  user wishes to produce some sort of expenses spreadsheet detailing both 
                  calculated fields and the aggregated data retrieved from previous images.

                   Secondly, to faciliate the first functional requirement of the app, we
                  decided to create a user-login system for our app. The users profiles would
                  contain previously uploaded images, aggretated expenses spreadsheets and most
                  likely some other financial data/trends we were able to extract from the user's
                  uploaded images.

                  Frameworks: 1. Firebase() - This flexible database has provided some early success in our testing. Currently we are using it to 
                                 store both user information, and any relevant images the user has uploaded.
                  
                              2. Java(mostly Android and AndroidX libraries) - Java itself is great for android app design so we decided to use it
                                 to create the interface of our application.
                             
                              3. Python(Flask, Pytesseract, opencv, caffe2) - Flask will be used for the developing the underlying image proccessing microservice, 
                                 while Pytesseract is being utilized because of its accurate data extraction ability from both computer generated and natural photos.
                              
                  The Problem:
                  Small businesses require a lot of receipts throughout the course of the year. Usually, someone has to go through every receipt, record the date, location, amount, HST, etc, and put it into an accounting category. This generally takes a lot of time and will in turn cost businesses more money.
                  
                  Our App: 
                  Use your Android device to take a picture of the receipt. Our app will use in house A.I Technology to recognize certain tokens such as date, location, amount, etc. Once tokens are found, user checks, edits, and selects a category. App places receipt into the correct category of the CSV file. All CSV files will be stored on our database and we can charge monthly. 
                  At the end of the year, your accountant has to go through this large CSV file with all the categories and simply call functions like =SUM(), =AVERAGE(), =COUNT()... 
                  All photos of the Raw receipt will be stored on our database and will be in the same folder/linked together with the CSV file line. Each line in the CSV file will contain an id that will be linked to its corresponding photo. Photos will be kept for evidence in case the physical receipt is lost or damaged. 
                  We will have all of the functions that a normal CSV file has.
                  
 




### Secondary Idea: 
                     Our secondary idea revolved around creating a social media application where users would be able to react in real-time to important
                    societial and political events. What would make our platform unique to say twitter or reddit, is that we would be providing a strict
                    setting where we post the current news (collected from both sides of the political spectrum) and allow people to react and debate on
                    this important issues.

                    Frameworks: 1. Angular(): taking from our experiences last semester in BTI425, we are effectively able to produce a rich, interactive,
                                   angular application that can easily be molded to fit our specific needs.

                                2. MongoDB(): This would be used to store user information and other important relevant data.
                