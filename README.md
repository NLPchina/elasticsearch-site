# Welcome to the Elasticsearch-sider 

------

The project is a elasticsearch plugin collection, You can use it in green, not need to modify your elasticsearch any configuration



- ##### Getting Started

  * first you need jdk8 , Suggested install it on elasticsearch machine
  * git clone https://github.com/NLPchina/elasticsearch-site.git
  * run it `./run.sh start` 
  * visit 'http://localhost:8080/apidoc' for restful api


- ##### About config
  * setting elasticsearch ip and port
	open 'elasticsearch-site/jcoder_home/resource/ioc.js'


- ##### Plugins
  * es-sql http://localhost:8080/web/sql/
  * head http://localhost:8080/web/head/

  ````
# if you want use head , you must take this setting to your elasticsearch.yml 
http.cors.enabled : true
http.cors.allow-origin : "*"
http.cors.allow-methods : OPTIONS, HEAD, GET, POST, PUT, DELETE
http.cors.allow-headers : X-Requested-With,X-Auth-Token,Content-Type, Content-Length
  ````
  
- ##### Thanks
	[ElasticSearch-sql](https://github.com/NLPchina/elasticsearch-sql) 
	[ElasticSearch-head](https://github.com/mobz/elasticsearch-head) 

	
  


