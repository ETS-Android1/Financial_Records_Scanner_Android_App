### Sharing A Financial Record or Image Via Internet (done by Parsa Jalilifar)

*** 

### Actor (User)

Business owner who has a valid account in the system.

### Pre-Condition

Each user must be logged in into the system to get access to the content of the mobile app and must have some scanned and uploaded pictures.

### Main Flow
 
1.	After successfully login, the user is able to navigate between images and CSV records.
2.	The user select an specific images or record to share.
3. The user decide how to share it by using email, social media and etc.
4. The result of share action either being success or fail will be reported to the user.

### Alternative Flow

1.	If the internet connection is not available to share the image or CSV file the system asks the user to check 
connection of his phone again or try later.


### Post Condition
The recipient has received the image or CSV file.



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

