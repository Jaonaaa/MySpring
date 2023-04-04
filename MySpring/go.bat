javac -source 1.8 -target 1.8 utilities/*.java
javac -source 1.8 -target 1.8 Annotation/*.java
javac -source 1.8 -target 1.8 models/*.java
javac -source 1.8 -target 1.8 etu1915/framework/Mapping.java
javac -source 1.8 -target 1.8 etu1915/framework/servlet/*.java
javac -source 1.8 -target 1.8 src/Main.java
java src/Main

jar cvf spring.jar ./ 

copy "spring.jar" "../../../Test_Framework/WEB-INF/lib/spring.jar"
cd ../../../ 
cd Test_Framework/
jar cvf projet_test.war *
copy "projet_test.war" "../"
del "projet_test.war"