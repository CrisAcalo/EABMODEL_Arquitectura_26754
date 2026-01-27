import requests
import json

access_token = 'NTlmNDM3MDYtYWFiMC00ZmQ0LWJhYTItYmJiYzUwNTBjMmQyMGVkN2Q3ZGYtZDhj_PE93_a3359ccd-0b0c-4e93-9577-9c245673a2c2' 
url = 'https://webexapis.com/v1/rooms'
headers = {
    'Authorization': 'Bearer {}'.format(access_token),
    'Content-Type': 'application/json'
}
params= {'title': 'Sala de Monster'}
res = requests.post(url, headers=headers, json=params)
print (res.json ())
