set M2_HOME=../../bin/apache-maven-3.0.4
set PATH=%PATH%;%M2_HOME%\bin

call mvn clean
call mvn install -Dmaven.test.skip=true
call cmd
