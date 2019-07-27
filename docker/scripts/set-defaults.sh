#!/bin/bash

export BROKER_HOST=broker
export MYSQL_HOST=mysql
export MYSQL_PASSWORD=datavault
export RABBITMQ_HOST=rabbitmq
export RABBITMQ_PASSWORD=datavault
export USER_DATA_DIR=/Users
export ARCHIVE_DIR=/tmp/datavault/archive
export TEMP_DIR=/tmp/datavault/temp
export META_DIR=/tmp/datavault/meta
export AWS_S3_BUCKET=datavault-archive
export AWS_REGION=eu-west-1
export AWS_ACCESS_KEY_ID=-
export AWS_SECRET_KEY_ID=-
export VAULT_ENABLE=true
export VAULT_ADDR=http://vault:8200
export VAULT_TOKEN=datavault
export VAULT_SECRET_PATH=kv/data/datavault
export VAULT_DATA_KEY_NAME=data-encryption-key
export VAULT_SSH_KEY_NAME=ssh-encryption-key
export VAULT_PEM_FILE= 
export KEYSTORE_ENABLE=false
export KEYSTORE_PATH=/docker_datavault-home/keystore/DatavaultKeyStore
export KEYSTORE_PASSWORD=veryStrongPassword
