#!/usr/bin/env bash
echo run this from V1/src directory

javac -Xlint:unchecked -cp ../Library  ./swantech/*.java
#jar cfm swantech.jar manifest.mf ./swantech/*.class ../Library ./res/* ../engine/*
echo run as java -cp .:../Library  swantech/AppGame