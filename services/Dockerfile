FROM centos
MAINTAINER Pär Wenåker
RUN yum -y update
RUN yum -y install epel-release
RUN yum -y install python-pip python-dev build-essential
RUN yum -y install supervisor
COPY ./app /app
COPY supervisord.conf /etc/supervisor/conf.d/supervisord.conf
WORKDIR /app
RUN chmod 775 *.py
RUN pip install -r requirements.txt
EXPOSE 9080 9081
CMD ["/usr/bin/supervisord", "-c", "/etc/supervisor/conf.d/supervisord.conf"]