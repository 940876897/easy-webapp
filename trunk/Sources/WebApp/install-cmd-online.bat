call mvn clean
call mvn install -P online -Dmaven.test.skip=true
call cmd
