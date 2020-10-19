
import json
from flask import Flask, request, jsonify, make_response
from flask_restful import Api, Resource, reqparse, abort 
from flask_jwt import JWT, jwt_required, current_identity
from manager import retrieve_USERS_PROTOCOL, start_FETCHING_PROTOCOL, start_aggregation_PROTOCOL, start_migration_PROTOCOL, task_report_PROTOCOL
from analyzer import Extractor

app = Flask(__name__)
api = Api(app)

parser = reqparse.RequestParser()
extractor = Extractor()
users = []

class User(object):
    def __init__(self,user_id, user_name, user_email):
        self.id = user_id
        self.user_ID = user_name
        self.user_Email = user_email

for user, user_id in zip(retrieve_USERS_PROTOCOL().values(), retrieve_USERS_PROTOCOL()):
    users.append(User(user_id ,user['Name'],user['Email'])) 

user_ID_table = {u.user_ID: u for u in users} 
user_email_table = {u.user_Email: u for u in users}

def authenticate(user_ID, user_email):
    global user_email_table
    temp_user = user_email_table.get(user_email, None)  
    return temp_user

def identity(payload):
    global user_ID_table
    user_id = payload['identity']
    return user_ID_table.get(user_id, None) 

app.config['SECRET_KEY'] = 'VMFqJhpkUFVUTovH0H3x210iB8Na3P1ZUusS9jOuJllmJa0EZ26GSuRds1hC'
jwt = JWT(app, authenticate, identity)

class Protected(Resource):
    @jwt_required()
    def post(self):
        return '%s' % current_identity

class Analyze(Resource):

    def post(self):

        json_ID_LIST = []
        spreadsheet_ID_LIST = []
        image_ID_LIST = []
        request_DATA = request.json

        if type(request_DATA) is not None:

            for data_items in request_DATA.values():
                 json_ID_LIST.append(data_items['json_ID'])
                 spreadsheet_ID_LIST.append(data_items['spreadsheet_ID'])
                 image_ID_LIST.append(data_items['image_ID'])
                  
            start_FETCHING_PROTOCOL(json_ID_LIST, image_ID_LIST)
            extractor.start_EXTRACTION()
            start_aggregation_PROTOCOL(extractor.get_resultant(), request_DATA)
            start_migration_PROTOCOL()

            return make_response(jsonify(task_report_PROTOCOL(request_DATA)),200)
           
            
api.add_resource(Protected, '/protect')
api.add_resource(Analyze, '/process_images')

if __name__ == "__main__":
    app.run(debug=True, host='localhost', port=8080)
