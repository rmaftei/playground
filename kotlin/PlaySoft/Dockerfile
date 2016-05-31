FROM centos

RUN yum -y --nogpg update ; yum clean all
RUN yum -y --nogpg install java-1.8.0-openjdk.x86_64 wget

RUN cd /root
RUN wget https://fastdl.mongodb.org/linux/mongodb-linux-x86_64-3.2.6.tgz

RUN tar zxf mongodb-linux-x86_64-3.2.6.tgz

ENV PATH mongodb-linux-x86_64-3.2.6/bin/:$PATH

RUN mkdir -p /opt/mongodb/db
RUN mkdir -p /var/log/mongodb

COPY run.sh /root

COPY rest-service/target/rest-service-1.0-SNAPSHOT.jar /opt/

EXPOSE 4567

CMD ["sh", "/root/run.sh"]

