#!/bin/bash

export BROKER_HOST=${BROKER_HOST:-broker}
export MYSQL_HOST=${MYSQL_HOST:-mysql}
export MYSQL_PASSWORD=${MYSQL_PASSWORD:-datavault}
export RABBITMQ_HOST=${RABBITMQ_HOST:-rabbitmq}
export RABBITMQ_PASSWORD=${RABBITMQ_PASSWORD:-datavault}
export USER_DATA_DIR=${USER_DATA_DIR:-/Users}
export ARCHIVE_DIR=${ARCHIVE_DIR:-/tmp/datavault/archive}
export TEMP_DIR=${TEMP_DIR:-/tmp/datavault/temp}
export META_DIR=${META_DIR:-/tmp/datavault/meta}
export AWS_S3_BUCKET=${AWS_S3_BUCKET:-datavault-archive}
export AWS_REGION=${AWS_REGION:-eu-west-1}
export AWS_ACCESS_KEY_ID=${AWS_ACCESS_KEY_ID:--}
export AWS_SECRET_KEY_ID=${AWS_SECRET_KEY_ID:--}
export VAULT_ENABLE=${VAULT_ENABLE:-true}
export VAULT_ADDR=${VAULT_ADDR:-http://vault:8200}
export VAULT_TOKEN=${VAULT_TOKEN:-datavault}
export VAULT_SECRET_PATH=${VAULT_SECRET_PATH:-kv/data/datavault}
export VAULT_DATA_KEY_NAME=${VAULT_DATA_KEY_NAME:-data-encryption-key}
export VAULT_SSH_KEY_NAME=${VAULT_SSH_KEY_NAME:-ssh-encryption-key}
export VAULT_PEM_FILE=${VAULT_PEM_FILE:- }
export KEYSTORE_ENABLE=${KEYSTORE_ENABLE:-false}
export KEYSTORE_PATH=${KEYSTORE_PATH:-/docker_datavault-home/keystore/DatavaultKeyStore}
export KEYSTORE_PASSWORD=${KEYSTORE_PASSWORD:-veryStrongPassword}
