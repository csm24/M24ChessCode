pwd
javac -cp ../Library/ictk-0.2.0-nonet.jar:. ./swantech/*.java
jar cfm swantech.jar manifest.mf ./swantech/*.class ../Library/ictk-0.2.0-nonet.jar ./res/*
