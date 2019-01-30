#!/usr/bin/python3
from flask import Flask, render_template, request
import base64
app = Flask(__name__)

p_key ='MIICXgIBAAKBgQDW2m8ahyD859xHMTWbiu3H2u1xcxW5XROQzU+oA55yEfSH+HF0c1F+ri/yb4dgU+tfzf4WEbM/Ek3LNI0q8eejTBeiSDn0Ny4dNNhtGkQXbpbICvyf4s4fHdKsj+V8IWr2x5p0VB8wt0C7YzEd1JDDZQMZ4PLFSK3Jm8mBtLNMAQIDAQABAoGBAM/VlUwwEzUShP9JFvZTuhhuKupnzUf2RYr5UGjSt17ZB5bQOc/QDbFhhngacvY3t6SBe5yVmrMUXbSILVaxksOaQBQFqdaxBUuEm4EEGl+7+zsI5FVZsVBXdO9WB25fZTJtpfdjuCxxPpWC0xQuMQNiruxXtqNaAk2r2v/BfKTBAkEA8udEmf6U5WrLy3mT9U/TjSaiv/W0QjaUB1bohFQogZ42QrpXykBLpJZJ2EhY+jvS3XTLA6z8eKl2yHErrxjmtQJBAOJv/1PBLX5kZ3O2RlpCedYekE1vsTY/7Jz2qI7TzvgO+DvElWJhNkgaHECGX1BBhJmjJg15wCb5lV1p1Kew850CQQCrPqYbluJr9uWkFptb96IiQdm9UUB66bVkkz2rhRVlhB+m3W5k9P0fFKEM7Lxl9eUMIXm6pqr7eiV23Go6sGh9AkBOUSRDSJ4+h+WMDPAaScGwABQy5D2roMJqq5kzBSkhfLP4oA52316R443/9znCHVcKeHYx1PlTwaEalP77HOW1AkEAxbIZ1TPJkxD6/ZmvUcbrX4HONZx9GGnbg+l1NEQNqtqboDHHSm2EzmGMB3zzOU6LT5YTSbxC6y8so42dzknFhw =='

@app.route('/login')
def index():
  return render_template('login.html')


@app.route('/FlaskTutorial', methods=['POST'])
def success():
  if request.method == 'POST':
      pass_ = request.form['pass']
      return render_template('success.html', pass_=decrypt(p_key, pass_))
  else:
      pass

def decrypt(rsakey, encrypt_text):
    cipher = Cipher_pkcs1_v1_5.new(rsakey)
    return cipher.decrypt(base64.b64decode(encrypt_text), '')

if __name__ == '__main__':
  app.run(debug=True)
