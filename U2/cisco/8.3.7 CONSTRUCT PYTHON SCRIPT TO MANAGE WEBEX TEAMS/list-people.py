import requests
import json


access_token = 'NjE2ZTZjMTEtMWE3My00ZGYxLTgzMmItNTlmZTdjYWE2Y2U4OGViOWU4ZWEtYzVl_PE93_a3359ccd-0b0c-4e93-9577-9c245673a2c2' 
url = 'https://webexapis.com/v1/people'
headers = {
    'Authorization': 'Bearer {}'.format(access_token),
    'Content-Type': 'application/json'
}
params = {
    'email': 'sptorres1@espe.edu.ec'
}
res = requests.get(url, headers=headers, params=params)
print(json.dumps(res.json(), indent=4)) 