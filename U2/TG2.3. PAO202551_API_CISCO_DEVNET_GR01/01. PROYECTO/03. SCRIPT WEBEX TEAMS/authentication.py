import requests
import json
import time

access_token = 'NTk2NjBhMjUtMGZkMi00YzlkLThmYjItNmQ0MWE2ZTY1ZWE1MDM5MzU5N2YtZGE0_PE93_a3359ccd-0b0c-4e93-9577-9c245673a2c2'
url = 'https://webexapis.com/v1/people/me'
headers = {
    'Authorization': 'Bearer {}'.format(access_token)
}
res = requests.get(url, headers=headers)
print(json.dumps(res.json(), indent=4))
