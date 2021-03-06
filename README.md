### Example of logging directly to Elasticsearch from Logback

---
#### Add Elastic Appender to your logback file
 
```
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    ...
    <appender name="ELASTIC" class="com.internetitem.logback.elasticsearch.ElasticsearchAppender">
        <url>http://${ELASTIC_HOST}/_bulk</url>
        <index>logs-%date{yyyy-MM-dd}</index>
        <type>${APP_NAME}</type>
        <loggerName>es-logger</loggerName> <!-- optional -->
        <errorLoggerName>es-error-logger</errorLoggerName> <!-- optional -->
        <connectTimeout>30000</connectTimeout> <!-- optional (in ms, default 30000) -->
        <errorsToStderr>false</errorsToStderr> <!-- optional (default false) -->
        <includeCallerData>false</includeCallerData> <!-- optional (default false) -->
        <logsToStderr>false</logsToStderr> <!-- optional (default false) -->
        <maxQueueSize>104857600</maxQueueSize> <!-- optional (default 104857600) -->
        <maxRetries>1</maxRetries> <!-- optional (default 3) -->
        <readTimeout>30000</readTimeout> <!-- optional (in ms, default 30000) -->
        <sleepTime>250</sleepTime> <!-- optional (in ms, default 250) -->
        <rawJsonMessage>false</rawJsonMessage> <!-- optional (default false) -->
        <includeMdc>false</includeMdc> <!-- optional (default false) -->
        <maxMessageSize>100</maxMessageSize> <!-- optional (default -1 -->
        <properties>
            <property>
                <name>host</name>
                <value>${HOSTNAME}</value>
                <allowEmpty>false</allowEmpty>
            </property>
            <property>
                <name>severity</name>
                <value>%level</value>
            </property>
            <property>
                <name>thread</name>
                <value>%thread</value>
            </property>
            <property>
                <name>stacktrace</name>
                <value>%ex</value>
            </property>
            <property>
                <name>marker</name>
                <value>%marker</value>
            </property>
            <property>
                <name>logger</name>
                <value>%logger</value>
            </property>
        </properties>
        <headers>
            <header>
                <name>Content-Type</name>
                <value>application/json</value>
            </header>
            <header>
                <name>Authorization</name>
                <value>Basic ${ELASTICSEARCH_TOKEN}</value>
            </header>
        </headers>
    </appender>
    ...

    <!-- LOG "com.example.demo*" at TRACE level -->
    <logger name="com.example.demo" level="trace" additivity="false">
        <appender-ref ref="ELASTIC"/>
    </logger>

</configuration>

```
#### Setup Elastic & Kibana

```
docker-compose run kibana
// elastic will also be started as kibana depeonds_on elastic as specified in docker-compose.yml
``` 

##### Get the basic authentication token of Base64 encoded 'username:password' 
```
Base64.getEncoder().encodeToString("elastic:changeme".getBytes())
```

##### Update elasticsearch token in docker-compose.yml of the application service
```
"ELASTICSEARCH_TOKEN=ZWxhc3RpYzpjaGFuZ2VtZQ=="
```

Run the service
```
docker-compose run hello-world-app
```