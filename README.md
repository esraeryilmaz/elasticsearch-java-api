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

<img src="https://github.com/esraeryilmaz/elasticsearch-java-api/blob/main/img/rest.png" width="600"/>

## Getting Started

1. I assume elasticsearch is installed, if not firstly you should install elasticsearch. You can use Docker, it will be easier.

2. Install Java for Windows. Also IDE should be downloaded, I recommend Eclipse.

3. Go to https://start.spring.io/ and create spring project. Add dependencies.
<img src="https://github.com/esraeryilmaz/elasticsearch-java-api/blob/main/img/spring%20project.PNG" />

4. Open this spring project in Eclipse (File > Import > Existing maven project > Next > Browse > Find project > Select Folder > Finish). We can add various dependencies to the pom.xml file in the project. It can stay like this for now.

**`MAVEN`** : Maven is a popular open-source build tool developed by the Apache Group to build, publish, and deploy several projects at once for better project management. The tool provides allows developers to build and document the lifecycle framework. 

**`POM`** : Stands for "Project Object Model". It is an XML representation of a Maven project held in a file named pom.xml.

<img src="https://github.com/esraeryilmaz/elasticsearch-java-api/blob/main/img/eclipse.PNG"/>

- Packages will be created under src/main/java and new classes will be created under these packages.


## How to implement Elasticsearch with Java ?

### Here is a simple example :
<img src="https://github.com/esraeryilmaz/elasticsearch-java-api/blob/main/img/java1.png"/>

### Building simple Java EE application that calls Rest services. (example API : https://my-json-server.typicode.com/typicode/demo/posts)
<img src="https://github.com/esraeryilmaz/elasticsearch-java-api/blob/main/img/java2.png" />

### Building Java EE application that adds data into the elasticsearch.

- We are gonna use libraries such as rest high level client, so first update the pom.xml file.

Add the following :
```xml
<dependency>
	<groupId>org.elasticsearch.client</groupId>
	<artifactId>elasticsearch-rest-high-level-client</artifactId>
	<version>7.9.2</version>
</dependency>

<dependency>
	<groupId>org.elasticsearch.client</groupId>
	<artifactId>elasticsearch-rest-client</artifactId>
	<version>7.9.2</version>
</dependency>

<dependency>
	<groupId>org.elasticsearch</groupId>
	<artifactId>elasticsearch</artifactId>
	<version>7.9.2</version><!--$NO-MVN-MAN-VER$-->
</dependency>

<dependency>
	<groupId>com.fasterxml.jackson.core</groupId>
	<artifactId>jackson-databind</artifactId>
	<version>2.11.1</version>
</dependency>
```

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
<img src="https://github.com/esraeryilmaz/elasticsearch-java-api/blob/main/img/update1.PNG" />

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
<img src="https://github.com/esraeryilmaz/elasticsearch-java-api/blob/main/img/update2.PNG" />

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
<img src="https://github.com/esraeryilmaz/elasticsearch-java-api/blob/main/img/update3.PNG"/>

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
<img src="https://github.com/esraeryilmaz/elasticsearch-java-api/blob/main/img/data%20getting.png" />


## KEEP IN MIND :  

- Default server port : 8080 
- “/” as first character at `@RequestMapping` or `@GetMapping` 
- Annotate API class/method with `@RequestMapping` 
- Annotation in correct line. 

## Second way to implement Elasticsearch in Java Spring Boot

### You need : 
- Spring Boot 1.5.1.RELEASE
- Elasticsearch 2.4.4
- Java 8

### Here is the architecture : 
<img src="https://github.com/esraeryilmaz/elasticsearch-java-api/blob/main/img/architecture%20layers.png"/>

* ```Controller``` : Controller layer contains the application logic, mapping the user request to particular functions and passing the user input to service layer to apply the business logic. 
* ```Service``` : This is the layer between the controller and repository which performs the business logic and validation logic. The controller passes the user input to the service layer and after applying the business logic, it is passed to the repository layer. 
* ```Repository``` : The layer which interact with the database CRUD operations via the DAOs(data access objects). 
* ```Model``` : Is the simple POJO classes which is acting as the DTO(Interact with application level data transfer) or DAO(Interaction with database operations)     

### The usage of the spring annotations : 
<img src="https://github.com/esraeryilmaz/elasticsearch-java-api/blob/main/img/annotations.png" width="500"/>

* ```@Component``` : In spring framework,we can use the class level annotation @Component to order to create a bean from the class automatically and register in the Spring container to release it on demand.

Those three annotations have some special features than the @Component annotation ; 

* ```@Controller``` : The @Controller annotation is not only registering a controller bean in the container, but also providing the capability of handling the  request mappings(@RequestMapping) received from the clients. 
* ```@Service``` : This is an alternative to @Component that specifies you intend to use the class as part of your service layer. 
* ```@Repository``` : This annotation marks a class as part of your data layer, for handling storage, retrieval, and search. 

### Project Structure : 
<img src="https://github.com/esraeryilmaz/elasticsearch-java-api/blob/main/img/project%20structure.PNG"/>

#### ```Configuration``` 
- EsConfig class configures elasticsearch to this project and make a connection with elasticsearch. This class read the application.properties file where we store the cluster name, elasticsearch host and port. 
- @EnableElasticsearchRepositories is used to enable Elasticsearch repositories that will scan the packages of the annotated configuration class for Spring Data repositories by default. 
- @Value is used here for reading the properties from the application.properties file.
- The Client() method creates a transport connection with elasticsearch.
- The configuration following sets up an Embedded Elasticsearch Server which is used by the ElasticsearchTemplate. The ElasticsearchTemplate bean uses the Elasticsearch Client and provides a custom layer for manipulating data in Elasticsearch.

```java
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.second.demo.repository")
public class EsConfig {

	@Value("${elasticsearch.host}")
	private String EsHost;

	@Value("${elasticsearch.port}")
	private int EsPort;

	@Value("${elasticsearch.clustername}")
	private String EsClusterName;

	@Bean
	public Client client() throws Exception {
		Settings esSettings = Settings.builder()
				.put("cluster.name", EsClusterName)
				.build();

		return TransportClient.builder()
				.settings(esSettings)
				.build()
				.addTransportAddress(
						new InetSocketTransportAddress(InetAddress.getByName(EsHost), EsPort));
	}

	@Bean
	public ElasticsearchOperations elasticsearchTemplate() throws Exception {
		return new ElasticsearchTemplate(client());
	}
}
```

#### ```Model```
Here we have annotated our LogRecord data objects with a @Document annotation that we can also use to determine index settings like name, numbers of shards or number of replicas. One of the attributes of the class needs to be an id, either by annotating it with @Id.
```java
@Document(indexName = "records", type = "log")
public class LogRecord {
	@Id
	private String id;
	private Date date;
	private String message;
	private SeverityEnum severity;

	public enum SeverityEnum {
		INFO,
		ERROR,
		WARNING,
		DEBUG
	};
}
```

#### ```Repository```
Here, We have extended ElasticsearchRepository which provide us many of apis that we don't need to define externally. This is the base repository class for elasticsearch based domain classes. Since it extends Spring based repository classes, we get the benefit of avoiding boilerplate code required to implement data access layers for various persistence stores.
```java
@Repository
public interface RecordRepository extends ElasticsearchRepository<LogRecord, String> {
	Page<LogRecord> findByDate(Date date, Pageable pageable);
	List<LogRecord> findByMessage(String message);
}
```

#### ```Service```
The following class, we have @Autowired the RecordRepository. We can simply call the CRUDRepository methods and the method we have declared in repository class with the RecordRepository object.

```java
public interface RecordService {
	LogRecord save(LogRecord record);
	void delete(LogRecord record);
	LogRecord findOne(String id);
	Iterable<LogRecord> findAll();
	Page<LogRecord> findByDate(Date date, PageRequest pageRequest);
	List<LogRecord> findByMessage(String message);
}

@Service
public class RecordServiceImpl implements RecordService {

	private RecordRepository recordRepository;

	@Autowired
	public void setRecordRepository(RecordRepository recordRepository) {
		this.recordRepository = recordRepository;
	}

	public LogRecord save(LogRecord record) {
		return recordRepository.save(record);
	}

	public void delete(LogRecord record) {
		recordRepository.delete(record);
	}

	public LogRecord findOne(String id) {
		return recordRepository.findOne(id);
	}

	public Iterable<LogRecord> findAll() {
		return recordRepository.findAll();
	}

	public Page<LogRecord> findByDate(Date date, PageRequest pageRequest) {
		return recordRepository.findByDate(date, pageRequest);
	}

	public List<LogRecord> findByMessage(String message) {
		return recordRepository.findByMessage(message);
	}
}

```

#### ```Controller```
- @GetMapping(“”): To fetch the document from given id.
- @PostMapping(“”): Create or update the document.
- @DeleteMapping(“”): Use to delete the document.
```java
@RestController
@RequestMapping("/records/log")
public class LogRecordController {

	@Autowired
	private RecordService recordService;

	// EX : localhost:8080/records/log/0001
	@GetMapping("/{id}")
	public LogRecord getById(@PathVariable String id) {
		return recordService.findOne(id);
	}

	/*
	 * Instead of RequestMapping you can use DeleteMapping, PostMapping...
	 */

	// EX : curl -H "Content-Type: application/json" -X POST -d '{"id":"0008", "date":978300000000,"message":"message8","severity":"ERROR"}'
	// http://localhost:8080/records/log
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<LogRecord> insertLog(@RequestBody LogRecord log) {
		return new ResponseEntity<LogRecord>(recordService.save(log), HttpStatus.CREATED);
	}

	// EX : localhost:8080/records/log/0009
	// If the id's are the same as below, there is no need to write them in pathvariable.
	@ResponseBody
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<LogRecord> deleteLog(@PathVariable("id") String id) {
		LogRecord tmp = recordService.findOne(id);
		recordService.delete(tmp);
		return new ResponseEntity<LogRecord>(HttpStatus.NO_CONTENT);
	}
}
```





