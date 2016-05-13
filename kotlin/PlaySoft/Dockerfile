FROM centos

RUN yum -y update ; yum clean all
RUN yum install -y java-1.8.0-openjdk.x86_64

COPY rest-service/target/rest-service-1.0-SNAPSHOT.jar /opt/

EXPOSE 4567

CMD ["java", "-jar", "/opt/rest-service-1.0-SNAPSHOT.jar"]

