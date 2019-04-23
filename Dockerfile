FROM tomcat
MAINTAINER jayan_muralidharan@infosys.com

ADD datarapid-api/target/*.war /usr/local/tomcat/webapps/

CMD ["catalina.sh", "run"]