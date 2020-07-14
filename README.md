# Group_12

## Members

| Full Name             | Seneca Email               | Skype Account                  | GitHub Username |  GitHub Email                  |
| --------------------- | -------------------------- | ------------------------------ | --------------- | ------------------------------ |
| Parsa Jalilifar       | pjalilifar@myseneca.ca     | parsajalilifar@gmail.com       | Parsa-jalilifar | parsajalilifar@gmail.com       |
| Shervin Tafreshipour  | stafreshipour@myseneca.ca  | shervintafreshipour@gmail.com  | shervintafreshi | shervintafreshipour@gmail.com  |
|                       |                            |                                |                 |                                |

## Ideas 

### Primary Idea:   Initially, our thought was to create an android application thats
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



### Secondary Idea:  Our secondary idea revolved around creating a social media application where users would be able to react in real-time to important
                    societial and political events. What would make our platform unique to say twitter or reddit, is that we would be providing a strict
                    setting where we post the current news (collected from both sides of the political spectrum) and allow people to react and debate on
                    this important issues.

                    Frameworks: 1. Angular(): taking from our experiences last semester in BTI425, we are effectively able to produce a rich, interactive,
                                   angular application that can easily be molded to fit our specific needs.

                                2. MongoDB(): This would be used to store user information and other important relevant data.
                