
## FUTURE PATCHES NOTES ##
## we need to create config vars for our application HEROKU | DOCKER
## Implementing multithreading to download resources all at once

import os 
import json
import pandas as pd
import urllib.request
import firebase_admin
import concurrent.futures
from firebase_admin import credentials
from firebase_admin import db
from firebase_admin import storage
from google.cloud import storage as google_storage

cred = credentials.Certificate('btr490-project-firebase-adminsdk-3xbj4-61e9e79cb7.json')
firebase_admin.initialize_app(cred, {'databaseURL': 'https://btr490-project.firebaseio.com/', 'storageBucket': 'btr490-project.appspot.com'})


storage_client = google_storage.Client()
bucket = storage_client.get_bucket(storage.bucket())


def retrieve_USERS_PROTOCOL(): 
    database_user_ref = db.reference('/Users')
    return database_user_ref.get()
     
def start_FETCHING_PROTOCOL(json_PATH_LIST = None, image_PATH_LIST = None):
    
    json_PATH_LIST = ['documents/' + path for path in json_PATH_LIST]
    image_PATH_LIST = ['images/' + path for path in image_PATH_LIST]  

    with concurrent.futures.ThreadPoolExector() as executor:
        executor.map(retrieve_document, json_PATH_LIST)
        executor.map(retrieve_image, image_PATH_LIST)

def retrieve_document(source_name):

    global bucket
    resource = bucket.blob(source_name)
    try:
       assert resource.exists()
    except:
       return
    resource.download_to_filename('./json_document/' + source_name[-10:] + '.json')

def retrieve_image(source_name):
     
     global bucket
     resource = bucket.blob(source_name)
     try:
        assert resource.exists()
     except:
        return
     resource.download_to_filename('./test_images/' + source_name[-7:] + '.json')

def start_aggregation_PROTOCOL(extraction_data_DICT, request_data_DICT):
    
    for data_item in request_data_DICT:
        for extracted_item in extraction_data_DICT:
            if data_item['image_ID'][:-4] == extracted_item and extracted_item is not None:
                 json_excel_conv(data_item['json_ID'])
                 df = pd.read_excel('./excel_documents/' + data_item['json_ID'][:-5] + '_UM.xlsx')
                 row_data = [{'Purchase Date':data_item[0], 
                              'Business Name': data_item[1], 
                              'Purchase Category': data_list[2], 
                              'Sub-Total': data_list[4] - data_list[3], 
                              'Tax': data_list[3], 
                              'Total': data_list[4]
                 }]
                 df = df.append(row_data, ignore_index=True)
                 df.to_excel(r'./modified_excel_documents/modified_' + data_item['json_ID'][:-5] + '_M.xlsx',index=None, header=True)
                 
                 

def json_excel_conv(source_name):
    df_2 = pd.read_json('./json_document/' + source_name)
    df_2.to_excel(r'./excel_documents/' + source_Name + '_UM.xlsx', index=None, header=True)

     
def start_migration_PROTOCOL():
    global bucket
    for filename in os.listdir('./modified_excel_documents'):
            df = pd.read_excel('./modified_excel_document/' + filename)
            df.to_json('./modified_json_document/' + filename[:-7] + '_TR.json', orient='columns', indent=4)
            document_blob = bucket.blob('documents' + filename[:-7] + '.json')
            document_blob.upload_from_filename('./modified_json_document/' + filename[:-7] + '_TR.json')


def task_report_PROTOCOL(request_data_DICT):
      task_REPORT_DICT = {}
      id_MATCH = None
      for data_item in request_data_DICT:
            
          id_MATCH = False  
          for filename in os.listdir('./modified_json_documents'):
              if data_item['json_ID'][:-5] == filename[:-8]:
                  task_REPORT_DICT[data_item] = 'Operation Passed - 001'
                  id_match = True
          
          if id_MATCH is False:
              task_REPORT_DICT[data_item] = 'Operation Failed - 002'
      reset_PROTOCOL()
      return task_REPORT_DICT
      
def reset_PROTOCOL():

     path = os.getcwd()
     for filename in os.listdir(path + '/excel_document'):
         os.remove(path + '/excel_document/' + filename)   
     for filename in os.listdir(path + '/extraction_data'):
         os.remove(path + '/extraction_data/' + filename)   
     for filename in os.listdir(path + '/json_documents'):
         os.remove(path + '/json_document/' + filename)    
     for filename in os.listdir(path + '/test_images'):
         os.remove(path + '/test_images/' + filename)




