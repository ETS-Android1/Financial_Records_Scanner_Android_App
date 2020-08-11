### Scanning User's Financial Records Via The Optical Camera Of Smart Phone (done by Parsa Jalilifar)

*** 

### Actor (User)

Business owner who has a valid account in the system.

### Pre-Condition

Each user must be logged in into the system to get access to the content of the mobile app.

To login into the app by using email address and password, the user must have been registered in side of register page.

Converting to CSV files can be done when the user had uploaded the pictures to the database.

### Main Flow

1. User can choose among three login and one register options in login page .(Email And Password ,Google ,Face Book ,Account Register)
2. After finishing login, the user will be brought to home page and a list of financial records pictures will be shown to the user.
3) User can add to the financial record pictures by using plus sign button then inside of new window can determine informations like category
   of record.
4) User can see all financial record pictures and the converted picture to CSV files by click on all images or CSV files buttons in home page.
5) All picture can be deleted, shared and converted to the CSV file.
6) All CSV files can be shared or removed from database.
7) Side bar menu which is located in navbar and is accessible in all Activities can give user access to sign out, about page, home page, to profile 
   page and to enter record page.
8) About page gives information about the project and people who had worked together to make it.
9) Profile page show information of user who had logged in into the system.
10) Record page helps to the user to make CSV file when the software is not able to extract information form picture to make the CSV file.

### Alternative Flow

1. If the chosen picture is not suitable to convert to the CSV file the system must inform the user and suggest to the user to manually to enter
   the information to be converted to the CSV file.(Step 10)


### Post Condition
The user can upload more record to database if he wants to do.


### User's Request a Financial Document based on previously taken photos (Done By Shervin Tafreshipour)

*** 

### Actor (User)

An Applicaton user who has a valid account in the system.

### Pre-Condition

The user must be logged into their account, and have several images previously uploaded and scanned.

### Main Flow

1. User navigates to the 'Render Financial documents' section of the app.
   The app displays a menu allowing the user to render a document by category, financial period, and type of document. 
2. The user then selects the relevant options relating to the financial documents they are requesting.
   The system then responds by asking if they would like to save the document locally or to a cloud storage service such as Google drive.
3. Finally the user will select their preferred storage location.
   Application will notify the user that the download is in progress/when the download has finished. 
   
### Alternative Flow

1. The user selects to render a document by category which has no financial records attributed to it.
   System will respond by letting the user know that the specific option is unavailable.
2. User selects to render a document by financial period which no financial records exists pertaining that specific period.
   The application will again respond by letthing the user know that the specific option is unavailable.

### Post Condition

The user will be able to generate and upload more financials documents as they see fit.



### Adding Receipt Into CSV file (Done by Cristian Maierean)

*** 

### Actor (User)

The business owner has a valid account in the system.

### Pre-Condition

The user must be already logged into the system and have already scanned their document.

### Main Flow

1. The user Verifies that all of the information entered is correct. 
2. The system sends a final check/verification to the user.
3. The system presents to the user all of their categories as buttons that they can choose from. 
4. User Selection (Cannot proceed further without this step)
5. The system sends a check/verification. 
6. The system saves the user's document into the CSV file under the appropriate expense category


### Alternative Flow

1. If the user detects an error or our system chose the wrong input, the user can manually override.
2. If the user declines verification, take back to the manual override section.
3. If the user escapes or closes the app during Category selection, check if the user has a "Draft" Category. "If not, create it and save data into it".
4. If the user declines verification, ask them to reselect a category



### Post Condition

The user can add as many expense categories as they wish. 

