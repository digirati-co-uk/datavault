FROM ubuntu:18.04
LABEL maintainer="Daniel Grant <daniel.grant@digirati.com>"

SHELL ["/bin/bash", "-o", "pipefail", "-c"]
ARG DEBIAN_FRONTEND=noninteractive
ARG PRE_COMMIT_VERSION=1.17.0

RUN apt-get update \
    && apt-get install -y --no-install-recommends openjdk-8-jdk-headless maven python3 python3-setuptools \
       python3-pip python3-wheel software-properties-common apt-transport-https curl gnupg-agent shellcheck git \
    && curl -fsSL https://download.docker.com/linux/ubuntu/gpg | apt-key add - \
    && add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" \
    && apt-get update \
    && apt-get install -y --no-install-recommends docker-ce\
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/* \
    && pip3 install pre-commit==$PRE_COMMIT_VERSION \
    && useradd -m -d /home/build -s /bin/bash -u 1010 build

ENV JAVA_HOME="/usr/lib/jvm/java-8-openjdk-amd64"

USER build
WORKDIR /home/build
