web:    with_jdwp java $JAVA_OPTS -jar target/cas.war --server.ssl.enabled=false --cas.events.trackConfigurationModifications=false --cas.server.http.enabled=false --cas.server.name=https://jasigcas.herokuapp.com --cas.server.prefix=https://jasigcas.herokuapp.com/cas --cas.adminPagesSecurity.ip=.+ --endpoints.enabled=true --endpoints.sensitive=false --server.port=$PORT --cas.monitor.endpoints.enabled=true --cas.monitor.endpoints.sensitive=false --cas.serviceRegistry.initFromJson=true --spring.boot.admin.url=https://casbootadminserver.herokuapp.com --spring.boot.admin.client.managementUrl=https://jasigcas.herokuapp.com/cas/status --spring.boot.admin.client.name=CAS --spring.zipkin.baseUrl=https://caszipkinserver.herokuapp.com/ --eureka.client.serviceUrl.defaultZone=https://caseureka.herokuapp.com/eureka/ --eureka.client.enabled=true --eureka.instance.statusPageUrl=https://jasigcas.herokuapp.com/cas/status/info --eureka.instance.healthCheckUrl=https://jasigcas.herokuapp.com/cas/status/health --eureka.instance.homePageUrl=https://jasigcas.herokuapp.com/cas/ --eureka.client.healthcheck.enabled=true --spring.cloud.config.discovery.enabled=false --cas.adminPagesSecurity.actuatorEndpointsEnabled=true --logging.level.org.apereo=DEBUG
