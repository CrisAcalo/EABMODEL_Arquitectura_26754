import requests
import json


access_token = 'NjE2ZTZjMTEtMWE3My00ZGYxLTgzMmItNTlmZTdjYWE2Y2U4OGViOWU4ZWEtYzVl_PE93_a3359ccd-0b0c-4e93-9577-9c245673a2c2' 
room_id = 'your_room_id'
url = 'https://webexapis.com/v1/memberships'
headers = {
    'Authorization': 'Bearer {}'.format(access_token),
    'Content-Type': 'application/json'
}
params = {'RoomId': room_id}
res = requests.get(url, headers=headers, params=params)
print (res.json ())

