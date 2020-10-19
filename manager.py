
## FUTURE PATCHES NOTES ##
## we need to create config vars for our application HEROKU | DOCKER

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
    image_PATH_LIST = ['Images/' + path for path in image_PATH_LIST]  

    with concurrent.futures.ThreadPoolExecutor() as executor:
        executor.map(retrieve_document, json_PATH_LIST)
        executor.map(retrieve_image, image_PATH_LIST)

def retrieve_document(source_name):

    global bucket
    resource = bucket.blob(source_name)
    try:
       assert resource.exists()
    except:
       return
    resource.download_to_filename('./json_documents/' + source_name[-14:])

def retrieve_image(source_name):
     
     global bucket
     resource = bucket.blob(source_name)
     try:
        assert resource.exists()
     except:
        return
     resource.download_to_filename('./test_images/' + source_name[-16:])

def start_aggregation_PROTOCOL(extraction_data_DICT, request_data_DICT):

    for data_item in request_data_DICT.values():
        for extracted_item in extraction_data_DICT:

            data_ID = data_item['image_ID']
            if data_ID[:-4] == extracted_item and extraction_data_DICT[extracted_item] is not None:
                 json_source_ID = data_item['json_ID']
                 json_excel_conv(json_source_ID)
                 df = pd.read_excel('./excel_documents/' + json_source_ID[:-4] + '_UM.xlsx')                   
                 row_data = [{'Purchase Date':  extraction_data_DICT[data_ID[:-4]][0], 
                              'Business Name':    extraction_data_DICT[data_ID[:-4]][1], 
                              'Purchase Category':   extraction_data_DICT[data_ID[:-4]][2], 
                              'Sub-Total': extraction_data_DICT[data_ID[:-4]][4] - extraction_data_DICT[data_ID[:-4]][3], 
                              'Tax': extraction_data_DICT[data_ID[:-4]][3], 
                              'Total': extraction_data_DICT[data_ID[:-4]][4]
                 }]
                 df = df.append(row_data, ignore_index=True)
                 df.to_excel(r'./modified_excel_documents/' + data_item['json_ID'][:-5] + '_M.xlsx',index=None, header=True)
                 
                 
def json_excel_conv(source_name):
    df_2 = pd.read_json('./json_documents/' + source_name)
    df_2.to_excel(r'./excel_documents/' + source_name[:-4] + '_UM.xlsx', index=None, header=True)

     
def start_migration_PROTOCOL():
    global bucket
    for filename in os.listdir('./modified_excel_documents'):
            df = pd.read_excel('./modified_excel_documents/' + filename)
            df.to_json('./modified_json_documents/' + filename[:-7] + '_TR.json', orient='columns', indent=4)
            document_blob = bucket.blob('documents/' + filename[:-7] + '.json')
            document_blob.upload_from_filename('./modified_json_documents/' + filename[:-7] + '_TR.json')


def task_report_PROTOCOL(request_data_DICT):
      
      task_REPORT_DICT = {}
      id_MATCH = None
      for data_item,request_item in zip(request_data_DICT.values(), request_data_DICT):
          request_item_name = request_item  
          id_MATCH = False  
          for filename in os.listdir('./modified_json_documents'):
              if data_item['json_ID'][:-5] == filename[:-8]:
                  task_REPORT_DICT[request_item_name] = 'Operation Passed'
                  id_MATCH = True
          
          if id_MATCH is False:
              task_REPORT_DICT[request_item_name] = 'Operation Failed'
      
      reset_PROTOCOL()
      return task_REPORT_DICT
      

def reset_PROTOCOL():

     path = os.getcwd()
     for filename in os.listdir(path + '/excel_documents'):
         os.remove(path + '/excel_documents/' + filename)   
     for filename in os.listdir(path + '/extraction_data'):
         os.remove(path + '/extraction_data/' + filename)   
     for filename in os.listdir(path + '/json_documents'):
         os.remove(path + '/json_documents/' + filename)    
     for filename in os.listdir(path + '/test_images'):
         os.remove(path + '/test_images/' + filename)
     for filename in os.listdir(path + '/modified_excel_documents'):    
         os.remove(path + '/modified_excel_documents/' + filename)
     for filename in os.listdir(path + '/modified_json_documents'):
         os.remove(path + '/modified_json_documents/' + filename)     



