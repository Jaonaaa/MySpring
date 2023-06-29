
set rootPath="C:\Users\Jaona\Desktop\Naina Repository\ 
set warFileName=projet_test.war 
set jspFilePath="C:\Users\Jaona\Desktop\Naina Repository\Test_Framework
set sourceProjectPath="C:\Users\Jaona\Desktop\Naina Repository\Test_Framework\WEB-INF\classes\"
set tempFilePath="C:\Users\Jaona\Desktop\Naina Repository\
set xmlConfigFile="C:\Users\Jaona\Desktop\Naina Repository\Test_Framework\WEB-INF\web.xml"

@REM Compile the framework
cd MySpring
javac -source 1.8 -target 1.8 utilities/*.java
javac -source 1.8 -target 1.8 Annotation/*.java
javac -source 1.8 -target 1.8 models/*.java
javac -source 1.8 -target 1.8 etu1915/framework/Mapping.java
javac -source 1.8 -target 1.8 etu1915/framework/servlet/*.java
javac -source 1.8 -target 1.8 src/Main.java

@REM Compile source files / ....

@REM Build Temp Folder
mkdir %tempFilePath%tempProject"
mkdir %tempFilePath%tempProject\WEB-INF"
mkdir %tempFilePath%tempProject\WEB-INF\lib"
mkdir %tempFilePath%tempProject\WEB-INF\classes"

@REM Build the jar file and place it in the temp lib folder
jar cvf spring.jar  ./ 
copy spring.jar  %tempFilePath%tempProject/WEB-INF/lib/spring.jar

@REM .jsp file the the temp folder
copy %jspFilePath%\*.jsp" %tempFilePath%\tempProject"
copy %xmlConfigFile% %tempFilePath%tempProject\WEB-INF\web.xml"
echo Tous | Xcopy %sourceProjectPath% %tempFilePath%tempProject\WEB-INF\classes\" /E/H/C/I 


@REM compile the project of the user 

@REM create war file
cd %tempFilePath%tempProject\"
jar cvf %warFileName% *
copy %warFileName% "../"
del %warFileName%

@REM go back to Root and delete temporary folder
cd %rootPath%"
echo O | rmdir %tempFilePath%tempProject" /s