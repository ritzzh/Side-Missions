import os
if os.path.isfile("path/to/config"):
    os.remove("path/to/config")

import PyPDF2
from instabot import Bot

bot = Bot()
bot.login(username="fired_neuron", password="") #sender username
user = ["belikeanu"] #receiver username

pdfFileObj = open('wuthering_heights.pdf', 'rb') #localpdf
pdfReader = PyPDF2.PdfFileReader(pdfFileObj)

# page = pdfReader.numPages
for i in range(10): #number of pages
    pagex = pdfReader.getPage(i) 
    message = pagex.extractText()
    bot.send_messages(message, user)
pdfFileObj.close()
