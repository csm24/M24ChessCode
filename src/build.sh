#!/usr/bin/env bash
pwd
javac -Xlint:unchecked -cp .:../Library  ./swantech/*.java
jar cfm swantech.jar manifest.mf ./swantech/*.class ../Library ./res/* ../engine/*
rm swantech/*.class
