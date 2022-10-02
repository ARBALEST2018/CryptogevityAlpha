from asyncio.windows_events import NULL
import speech_recognition as sr
import pyttsx3
import requests
import time

url = "http://34.231.40.162:8000/api/chatterbot/"
headers = {'Content-type': 'application/json', 'Accept': 'text/plain'}
listener = sr.Recognizer()
engine = pyttsx3.init()
voices = engine.getProperty('voices')
engine.setProperty('voices', voices[1].id)
intro = 'Hello. Welcome to the chatbot of cryptogevity, which is the chatbot mainly for cryptocurrency and related topics.'
ask = 'How can I do for you?'
sample_q = ""

def talk(text):
	engine.say(text)
	engine.runAndWait()

def take_command():
	talk(ask)
	try:
		with sr.Microphone() as source:
			print("I'm listening...")
			voice = listener.listen(source)
			# command = listener.recognize_azure(voice, key = '21dc9352aa4a4267b4a5b7e96250d602')
			# print(command)
			command = listener.recognize_google(voice)
			command = command.lower()
	except:
		return NULL
	if 'abby' in command:
		command = command.replace('abby',' ')
	if "exit" in command or "terminate" in command :
		print(command)
		return NULL
	talk(command)
	print(command)
	return command

def run_command():
	talk(intro)
	while True:
		command = take_command()
		if command != NULL:
			r = requests.post(url, json={"text": command}, headers=headers)
			answer = r.json()["text"]
			print( r.json()["text"])
			talk(answer)
		else:
			break

run_command()



# data = {'text': 'bitcoin'}

# headers = {'Content-type': 'application/json', 'Accept': 'text/plain'}
# r = requests.post(url, json={"text": "bitcoin"}, headers=headers)

# print( r.json()["text"])