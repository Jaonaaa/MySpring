javac -source 1.8 -target 1.8 -parameters utilities/*.java
javac -source 1.8 -target 1.8 -parameters Annotation/*.java
javac -source 1.8 -target 1.8 -parameters models/*.java
javac -source 1.8 -target 1.8 -parameters etu1915/framework/Mapping.java
javac -source 1.8 -target 1.8 -parameters etu1915/framework/servlet/*.java
javac -source 1.8 -target 1.8 -parameters src/Main.java
java src/Main

jar cvf spring.jar ./ 

copy "spring.jar" "../../../Test_Framework/WEB-INF/lib/spring.jar"
cd ../../../ 
cd Test_Framework/
jar cvf projet_test.war *
copy "projet_test.war" "../"
del "projet_test.war"