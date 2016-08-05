Deploying apidoc
================

 - Runs in EC2 on docker images. Database is RDS Postgresql
 - yum install docker
 - service docker start
 - docker pull mbryzek/apidoc:0.11.31
 - docker run ...

Installing Docker on mac
========================

  http://docs.docker.io/installation/mac/

Building the Docker Image
=========================

  Step 1: Using a small set of scripts that will tag the repo, update
  any markup to latest tag, and then create the docker image:
  
  /web/ionroller-tools/bin/release-and-deploy

  You can also build the docker image directly:

    cd www/ui
    git pull
    cd ../..
    ./ui-build.sh
    docker build -t mbryzek/apidoc:0.11.31 .

Releasing a schema change
=========================

    sudo yum install http://yum.postgresql.org/9.4/redhat/rhel-6-x86_64/pgdg-ami201503-94-9.4-1.noarch.rpm
    sudo yum install postgresql94

    cd /web/apidoc/schema
    sem-dist
    scp -F /web/metadata-architecture/ssh/config /web/schema-apidoc/dist/schema-apidoc-*.tar.gz dbgateway:~/
    ssh -F /web/metadata-architecture/ssh/config dbgateway
    tar xfz schema-apidoc-*.tar.gz
    cd schema-apidoc-*

    echo "host:5432:apidoc:api:PASSWORD" > ~/.pgpass
    chmod 0600 ~/.pgpass

    sem-apply --user apoi --host host --name apidoc

Database backup
===============

    pg_dump  -Fc -h host -U api -f apidoc.dmp apidoc
    pg_restore -U api -c -d apidoc apidoc.dmp

Debugging
=========

 - Login to EC2 https://console.aws.amazon.com/

 - SSH to an instance
   ssh -F /web/metadata-architecture/ssh/mbryzek <EC2 Hostname>

 - sudo docker ps -a

 - sudo docker logs <container id>

 - Docker log location: /var/log/upstart/docker.log
