#!/bin/bash
set -e
export CATALINA_HOME=/opt/tomcat
echo "=== 1. Build du framework ==="
cd framework
mvn clean install
cd ..

echo "=== 2. Build de l'app ==="
cd app
mvn clean package
cd ..

echo "=== 3. Deploy sur Tomcat ==="
cp app/target/app-test.framework-1.0.0.war $CATALINA_HOME/webapps/
echo "Deploy OK → http://localhost:8080/app-test.framework-1.0.0/"
