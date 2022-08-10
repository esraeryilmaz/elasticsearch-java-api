# Java API with ElasticSearch.

### `Java SE` (Standart Edition)
Java SE's API provides the core functionality of the Java programming language. It defines everything from the basic types and objects of the Java programming language to high-level classes that are used for networking, security, database access, graphical user interface (GUI) development, and XML parsing.

### `Java EE` (Enterprise Edition)
The Java EE platform is built on top of the Java SE platform. The Java EE platform provides an API and runtime environment for developing and running large-scale, multi-tiered, scalable, reliable, and secure network applications.

### `Java Spring Boot`
Spring on other hand is the application development framework for JavaEE. It’s an open-source Java Platform which provides supports to Java for developing robust Java application very smoothly and easily. 

- JavaEE has oracle based license -  Spring has an open-source license. 

### What is `API` ?
APIs make it possible for programs to interact with each other. This is especially important for programs because they can written in different languages, so APIs provide a means for different programs to overcome the “language barrier”.

### What is `Rest` ?
Representational state transfer (REST) is a software architectural style that describes a uniform interface between decoupled components in the Internet in a Client-Server architecture.

- REST API is one of the most widely used API in Java.

<img src="" width="400"/>

## Getting Started

1. I assume elasticsearch is installed, if not firstly you should install elasticsearch. You can use Docker, it will be easier.

2. Install Java for Windows. Also IDE should be downloaded, I recommend Eclipse.

3. Go to https://start.spring.io/ and create spring project. Add dependencies.
<img src="" width="400"/>

4. Open this spring project in Eclipse (File > Import > Existing maven project > Next > Browse > Find project > Select Folder > Finish). We can add various dependencies to the pom.xml file in the project. It can stay like this for now.

**`MAVEN`** : Maven is a popular open-source build tool developed by the Apache Group to build, publish, and deploy several projects at once for better project management. The tool provides allows developers to build and document the lifecycle framework. 

**`POM`** : Stands for "Project Object Model". It is an XML representation of a Maven project held in a file named pom.xml.

<img src="" width="400"/>

- Packages will be created under src/main/java and new classes will be created under these packages.


## How to implement Elasticsearch with Java ?

### Here is a simple example :
<img src="" width="400"/>

### Building simple Java EE application that calls Rest services. (example API : https://my-json-server.typicode.com/typicode/demo/posts)

### Building Java EE application that adds data into the elasticsearch.

- CONNECTION : It is responsible to make connection with ElasticSearch from Java client.
```java
RestHighLevelClient client = new RestHighLevelClient(
RestClient.builder(new HttpHost("localhost", 9200, "http")));
```

- CREATING INDEX : Creating New Index into ElasticSearch. Run this piece of code just one time.
```java
CreateIndexRequest request = new CreateIndexRequest("sampleindex");
request.settings(Settings.builder().put("index.number_of_shards", 1).put("index.number_of_replicas", 2));
CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
System.out.println("response id: " + createIndexResponse.index());
```

- DATA INSERTION WAY1 : Inserting a Sample String Value
```java
IndexRequest indexRequest1 = new IndexRequest("sampleindex");
indexRequest1.id("001");
indexRequest1.source("SampleKey","SampleValue");
IndexResponse indexResponse1 = client.index(indexRequest1, RequestOptions.DEFAULT);
System.out.println("response id: "+indexResponse1.getId());
System.out.println("response name: "+indexResponse1.getResult().name());
```

- DATA INSERTION WAY2 : Inserting a Map data
```java
HashMap<String, Integer> map = new HashMap<String, Integer>();
map.put("keyOne", 10); 
map.put("keyTwo", 30);
map.put("KeyThree", 20);
IndexRequest indexRequest2 = new IndexRequest("sampleindex");
indexRequest2.id("002");
indexRequest2.source(map);
IndexResponse indexResponse2 = client.index(indexRequest2, RequestOptions.DEFAULT);
System.out.println("response id: "+indexResponse2.getId());
System.out.println("response name: "+indexResponse2.getResult().name());
```

- DATA INSERTION WAY3 :  Inserting class object's data. Employee class created and after that;
```java
Employee emp = new Employee("Esra", "Eryılmaz",  LocalDate.now() );
IndexRequest indexRequest3 = new IndexRequest("sampleindex");
indexRequest3.id("003");
indexRequest3.source(new ObjectMapper().writeValueAsString(emp), XContentType.JSON);
IndexResponse indexResponse3 = client.index(indexRequest3, RequestOptions.DEFAULT);
System.out.println("response id: "+indexResponse3.getId());
System.out.println("response name: "+indexResponse3.getResult().name());
```

- DATA UPDATE WAY1 : Updating a Sample String Value
```java
UpdateRequest updateRequest1 = new UpdateRequest("sampleindex", "003");
updateRequest1.doc("firstName", "Zeynep");
UpdateResponse updateResponse1 = client.update(updateRequest1, RequestOptions.DEFAULT);
System.out.println("updated response id: "+ updateResponse1.getId());
```

- DATA UPDATE WAY2 : Updating Id with particular Map values
	
  UpdateRequest -> it will keep previous values and it will update if key matches, if key not matches it will add extra keys into that data.
  IndexRequest -> it will replace your previous data. (deletes previous data)
```java
Map<String, Object> updateMap = new HashMap<String, Object>();
updateMap.put("age","25");
updateMap.put("sector","Education");
UpdateRequest updateRequest2 = new UpdateRequest("sampleindex", "003").doc(updateMap);
UpdateResponse updateResponse2 = client.update(updateRequest2, RequestOptions.DEFAULT);
System.out.println("updated response id: "+updateResponse2.getId());
```

- DATA UPDATE WAY3 : Inserting class object's data
```java
Employee newEmp = new Employee("Esra2", "Eryılmaz2",  LocalDate.now() );
IndexRequest request = new IndexRequest("sampleindex");
request.id("003");
request.source(new ObjectMapper().writeValueAsString(newEmp), XContentType.JSON);
IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
System.out.println("response id:" + indexResponse.getId());
System.out.println(indexResponse.getResult().name());
```

- DATA DELETION WAY1 : Delete particular record
```java
DeleteRequest deleteRequest = new DeleteRequest("sampleindex","003");
DeleteResponse deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);
System.out.println("response id: " + deleteResponse.getId());
```

- DATA DELETION WAY2 : Delete entire index
```java
DeleteIndexRequest delRe = new DeleteIndexRequest("sampleindex");
client.indices().delete(delRe, RequestOptions.DEFAULT);
```


### Building Java EE application that reads data from the elasticsearch.

- DATA GETTING
```java
GetRequest getEmployeeRequest = new GetRequest("sampleindex","003");
GetResponse getResponseGet = client.get(getEmployeeRequest, RequestOptions.DEFAULT);

if(getResponseGet.isExists()) {
	java.util.Map<String, Object> sourceAsMap = new HashMap<>(); 
	sourceAsMap = getResponseGet.getSourceAsMap();
	System.out.println(sourceAsMap);
}
```
<img src="" width="400"/>


### KEEP IN MIND :  

- Default server port : 8080 
- “/” as first character at `@RequestMapping` or `@GetMapping` 
- Annotate API class/method with `@RequestMapping` 
- Annotation in correct line. 

