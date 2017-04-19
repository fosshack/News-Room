# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models

class News(models.Model):
	title = models.CharField(max_length=200)
	content = models.CharField(max_length=4000)
	lon = models.FloatField(default=0.0)
	lan = models.FloatField(default=0.0)
	img = models.ImageField(upload_to="images", null=True, blank=True)
	upvotes = models.IntegerField(default=0)
	downvotes = models.IntegerField(default=0)
	originality = models.FloatField(default=0.0)
	def __unicode__(self):
        	return self.title


