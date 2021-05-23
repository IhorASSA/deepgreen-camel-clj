FROM ubuntu:latest

ENV DEBIAN_FRONTEND=noninteractive
RUN apt update && apt upgrade -y &&\
    apt install git wget clojure -y

RUN mkdir ~/bin && \
    wget -c "https://raw.githubusercontent.com/technomancy/leiningen/stable/bin/lein" -O ~/bin/lein && \
    chmod +x ~/bin/lein && \
    echo "export PATH=$PATH:~/bin" >> .profile

ENV PATH=$PATH:~/bin

RUN ~/bin/lein -v
WORKDIR /opt

ADD . /opt/deepgreen-camel-clj
# RUN git clone https://github.com/aahutsal/deepgreen-camel-clj.git && \
#     cd deepgreen-camel-clj/packages/ && \
#     git clone https://github.com/aahutsal/deepgreen-file-importer # TODO: fix through --recurse-submodules

WORKDIR /opt/deepgreen-camel-clj
RUN ~/bin/lein uberjar

CMD java -jar /opt/deepgreen-camel-clj/target/uberjar/deepgreen-camel-clj-0.1.0-SNAPSHOT-standalone.jar



