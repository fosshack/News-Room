# -*- coding: utf-8 -*-
from __future__ import unicode_literals
from django.views.decorators.csrf import csrf_exempt
from django.http import HttpResponse
from django.http import JsonResponse
from django.core import serializers
from analysis import chk_origin
from django.shortcuts import render
from .models import News
import simplejson
@csrf_exempt
def index(request):
    return HttpResponse("Hello, world. You're at the polls index.")

@csrf_exempt
def upload_news(request):
	if request.method=='POST':
		imge = request.POST.get('uploadedfile')
		title_r = request.POST.get("title")
		lon_r = 0 #request.POST.get("lon")
		lan_r = 0 #request.POST.get("lat")
		uv= 0
		dv = 0 
		content_r = request.POST.get("content")
		org =  0
		org = chk_origin(content_r)
		"""fh = open("one.png", "wb")
		fh.write(img_data.decode('base64'))
		fh.close()"""
		new_news = News(title=title_r,img=imge,content=content_r,upvotes=uv,downvotes=dv,lon=lon_r,lan=lan_r,originality=org)
		new_news.save()
		response_data=[{"success": "1"}]
		return HttpResponse(simplejson.dumps(response_data))
@csrf_exempt
def list_news(request):
	news_entries = News.objects.all()
	data = serializers.serialize("json", news_entries)
	print data
	return HttpResponse(data)
@csrf_exempt
def upvote(request):
	if request.method=='POST':
		pkey = request.POST.get("pk")
		new = News.objects.get(pk=pkey)
		new.upvote = new.upvote + 1
		new.save()
@csrf_exempt
def downvote(request):
	if request.method=='POST':
		pkey = request.POST.get("pk")
		new = News.objects.get(pk=pkey)
		new.upvote = new.upvote - 1
		new.save()
		
	 
