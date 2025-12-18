import requests
import json


access_token = 'NTlmNDM3MDYtYWFiMC00ZmQ0LWJhYTItYmJiYzUwNTBjMmQyMGVkN2Q3ZGYtZDhj_PE93_a3359ccd-0b0c-4e93-9577-9c245673a2c2' 
room_id = 'Y2lzY29zcGFyazovL3VybjpURUFNOmV1LWNlbnRyYWwtMV9rL1JPT00vZWQwMmE1YTAtZGI0Ny0xMWYwLWFmNzEtOGRmMzllZjI3YTI4'
url = 'https://webexapis.com/v1/memberships'
headers = {
    'Authorization': 'Bearer {}'.format(access_token),
    'Content-Type': 'application/json'
}
params = {'RoomId': room_id}
res = requests.get(url, headers=headers, params=params)
print (res.json ())

