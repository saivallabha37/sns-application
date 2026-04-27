@echo off
REM Compile all Java files
echo Compiling Java files...
javac -cp lib/ojdbc6.jar -d bin src/com/sns/db/*.java
javac -cp lib/ojdbc6.jar;bin -d bin src/com/sns/model/*.java
javac -cp lib/ojdbc6.jar;bin -d bin src/com/sns/dao/*.java
javac -cp lib/ojdbc6.jar;bin -d bin src/com/sns/util/*.java
javac -cp lib/ojdbc6.jar;bin -d bin src/com/sns/ui/*.java
echo Compilation complete!
pause
