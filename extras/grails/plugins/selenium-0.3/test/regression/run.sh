#!/bin/sh

PLUGIN_VERSION=0.3
DEV_MODE="true"

if [ $DEV_MODE = "true" ] 
then
rm -rf temp
fi

mkdir temp
cd temp

if [ $DEV_MODE = "true" ] 
then
	cd ../../..
        grails clean
	grails package-plugin
	cd test/regression/temp
	cp ../../../grails-selenium-$PLUGIN_VERSION.zip .
else 
	wget http://javathinking.com/grails/grails-selenium-plugin/$PLUGIN_VERSION/grails-selenium-$PLUGIN_VERSION.zip
	wget http://javathinking.com/grails/grails-selenium-plugin/$PLUGIN_VERSION/test/regression/resources
fi

grails create-app selenium-test
cd selenium-test
grails install-plugin ../grails-selenium-$PLUGIN_VERSION.zip
cp -rf ../../resources/* .


echo Run the application in temp/selenium-test, then browse to
echo   "http://localhost:8080/selenium-test/selenium/core/TestRunner.html?test=..%2F..%2Fselenium/suite"

grails run-app