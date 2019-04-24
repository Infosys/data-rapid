FROM tomcat
MAINTAINER jayan_muralidharan@infosys.com

ADD datarapid-api/target/*.war /usr/local/tomcat/webapps/
ADD wait-for-it.sh .

CMD ["catalina.sh", "run"]