FROM ubuntu:latest

ENV DEBIAN_FRONTEND=noninteractive
RUN apt update && apt upgrade -y &&\
    apt install wget clojure python3 python3-pip -y

RUN mkdir ~/bin && \
    wget -c "https://raw.githubusercontent.com/technomancy/leiningen/stable/bin/lein" -O ~/bin/lein && \
    chmod +x ~/bin/lein

RUN ~/bin/lein -v
WORKDIR /opt

ADD src /opt/deepgreen-camel-clj/src
ADD resources /opt/deepgreen-camel-clj/resources
ADD project.clj /opt/deepgreen-camel-clj/
ADD Dockerfile /opt/deepgreen-camel-clj/
ADD .env.production /opt/deepgreen-camel-clj/
ADD .env.development /opt/deepgreen-camel-clj/
RUN ln -s .env.production .env # this is needed for Python module pydotenvs TODO: fix it!!!

ADD packages/deepgreen-file-importer/src/*.py /opt/deepgreen-camel-clj/packages/deepgreen-file-importer/src/
ADD packages/deepgreen-file-importer/requirenments.txt /opt/deepgreen-camel-clj/packages/deepgreen-file-importer/

WORKDIR /opt/deepgreen-camel-clj
RUN ~/bin/lein uberjar
RUN ln -s /opt/deepgreen-camel-clj/packages/deepgreen-file-importer/src/deepgreen_file_importer_v2.py /usr/local/bin/file-importer.py
RUN chmod a+x /usr/local/bin/file-importer.py

RUN ln -s /usr/bin/python3 /usr/bin/python
RUN pip3 install -r packages/deepgreen-file-importer/requirenments.txt

#RUN locale-gen en_US.UTF-8
ENV LANG en_US.UTF-8 LANGUAGE en_US:en LC_ALL en_US.UTF-8

CMD java -jar /opt/deepgreen-camel-clj/target/uberjar/deepgreen-camel-clj-0.1.0-SNAPSHOT-standalone.jar
