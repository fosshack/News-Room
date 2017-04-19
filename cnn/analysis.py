import json
from watson_developer_cloud import ToneAnalyzerV3

def check_originality(txt):
   tone_analyzer = ToneAnalyzerV3(
   username='64e8ffe0-148b-4500-b36a-6fd241ffd857',
   password='uEbxWr1rQLXh',
   version='2016-05-19 ')
   return tone_analyzer.tone(text=txt)
def chk_origin(text):
	data = check_originality(text)
	tones = data["document_tone"]["tone_categories"][1]["tones"][1]["score"]
	return tones

