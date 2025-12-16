import requests
import json

access_token = 'NjE2ZTZjMTEtMWE3My00ZGYxLTgzMmItNTlmZTdjYWE2Y2U4OGViOWU4ZWEtYzVl_PE93_a3359ccd-0b0c-4e93-9577-9c245673a2c2' 
room_id = 'Y2lzY29zcGFyazovL3VybjpURUFNOmV1LWNlbnRyYWwtMV9rL1JPT00vMjRiNDdhNzAtNjgyZC0xMWYwLWI2MjQtMmQ4NDE3NDBlODFk'
message = 'Hello **DevNet Associates**!!'
url = 'https://webexapis.com/v1/messages'
headers = {
    'Authorization': 'Bearer {}'.format(access_token),
    'Content-Type': 'application/json'
}
params = {'roomId': room_id, 'markdown': message}
res = requests.post(url, headers=headers, json=params)
print (res.json ())
