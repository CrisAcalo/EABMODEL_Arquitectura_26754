import requests
import json

access_token = 'NTk2NjBhMjUtMGZkMi00YzlkLThmYjItNmQ0MWE2ZTY1ZWE1MDM5MzU5N2YtZGE0_PE93_a3359ccd-0b0c-4e93-9577-9c245673a2c2' 
room_id = 'Y2lzY29zcGFyazovL3VybjpURUFNOmV1LWNlbnRyYWwtMV9rL1JPT00vZWQwMmE1YTAtZGI0Ny0xMWYwLWFmNzEtOGRmMzllZjI3YTI4'
message = 'Hola esta es una paractica de Arquitectura de software Ejemplo del uso de *Markdown* en Webex Teams.\n\n- **Negrita**\n- _Itálica_\n- `Código` \n\n[Enlace a la ESPE](https://www.espe.edu.ec) estamos probando el uso de markdown en Webex Teams.'
url = 'https://webexapis.com/v1/messages'
headers = {
    'Authorization': 'Bearer {}'.format(access_token),
    'Content-Type': 'application/json'
}
params = {'roomId': room_id, 'markdown': message}
res = requests.post(url, headers=headers, json=params)
print (res.json ())
