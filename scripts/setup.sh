if type -p java; then
 echo found java executable in PATH _java=java
elif [[ -n "$JAVA_HOME" ]] && [[ -x "$JAVA_HOME/bin/java" ]]; then
 echo found java executable in JAVA_HOME _java="$JAVA_HOME/bin/java"
else
 echo "no java installed"
 exit 1
fi
if [[ "$_java" ]]; then
 version=$("$_java" -version 2>&1 | awk -F '"' '/version/ {print $2}')
 echo version "$version"
 if [[ "$version" < "1.8" ]]; then
  echo 'version is less than 1.8'
  exit 1
 fi
fi
if ! type -p git; then
 echo 'git is not installed'
 exit 1
fi
echo 'Prerequisites are met'
git clone git@github.com:son4etyyy/AutomaticRecognitionTraficViolators.git
cd AutomaticRecognitionTraficViolators
chmod +x scripts/postgres.sh
scripts/postgres.sh
chmod +x scripts/nginx.sh
scripts/nginx.sh
sudo apt-get install maven -y
mvn clean install

