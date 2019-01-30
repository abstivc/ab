#!/usr/bin/python3

import pymongo

myclient = pymongo.MongoClient("mongodb://localhost:27017/")
mydb = myclient["testdb"]

mycol = mydb["sites"]
# 只有在插入文档数据后，才真正的创建成功

mydict = {"name": "RUNOOB", "alexa": "10000", "url": "https://www.runoob.com"}
x = mycol.insert_one(mydict)
print(x)

dblist = myclient.list_database_names()
if "testdb" in dblist:
    print("数据库已存在！")
else:
    print("没有")

collist = mydb. list_collection_names()
# collist = mydb.collection_names()
if "sites" in collist:   # 判断 sites 集合是否存在
  print("集合已存在！")
else:
    print("没有")
