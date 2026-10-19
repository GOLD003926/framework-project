#!/bin/bash

# Définition des variables
APP_NAME="AppFramework"
SRC_DIR="src/java"
WEB_DIR="src/webapps"
BUILD_DIR="build"
LIB_DIR="lib"
TOMCAT_WEBAPPS="./../tomcat/webapps"
MINI_FRAMEWORK_JAR="$LIB_DIR/mini-framework-v9.jar"

# Nettoyage et création du répertoire temporaire
rm -rf $BUILD_DIR
mkdir -p $BUILD_DIR/WEB-INF/classes
mkdir -p $BUILD_DIR/WEB-INF/lib

# Compilation des fichiers Java avec le JAR des Servlets
find $SRC_DIR -name "*.java" > sources.txt
javac -cp $MINI_FRAMEWORK_JAR -d $BUILD_DIR/WEB-INF/classes @sources.txt
rm sources.txt

# Copier les fichiers web (web.xml, JSP, etc.)
cp -r $WEB_DIR/* $BUILD_DIR/WEB-INF/

# Copier le JAR du mini-framework dans WEB-INF/lib
cp $MINI_FRAMEWORK_JAR $BUILD_DIR/WEB-INF/lib/
cp $LIB_DIR/gson-2.13.2.jar $BUILD_DIR/WEB-INF/lib/
cp $LIB_DIR/sqlite-jdbc-3.46.1.3.jar $BUILD_DIR/WEB-INF/lib/

# Générer le fichier .war dans le dossier build
cd $BUILD_DIR || exit
jar -cvf $APP_NAME.war *
cd ..

# Déploiement dans Tomcat
cp -f $BUILD_DIR/$APP_NAME.war $TOMCAT_WEBAPPS/

echo ""

echo "Déploiement terminé. Redémarrez Tomcat si nécessaire."

echo ""
