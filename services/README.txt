> docker-machine create --driver virtualbox default
> eval $(docker-machine env)
> docker-machine ls
> docker build -t services-flask:latest .
> docker run -p 9080:9080 -p 9081:9081 -d services-flask