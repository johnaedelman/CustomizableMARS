@echo off
set /p "filename=Input the filename of your custom language: "
for /F "tokens=1 delims=." %%a in ("%filename%") do (
   set "filename=%%a"
)
javac -d out mars/mips/instructions/customlangs/%filename%.java
jar cf %filename%.jar -C out .
rmdir /S /Q out
move /y %~dp0/%filename%.jar %~dp0/mars/mips/instructions/customlangs/
if %errorlevel%==0 (
	echo:
	echo JAR file successfully built and moved to /mars/mips/instructions/customlangs/. 
	echo It will now appear in the Language Switcher.
)
if %errorlevel%==1 (
	echo:
	echo Error when building or moving JAR file to /mars/mips/instructions/customlangs/. 
	echo Ensure you have inputted the filename of your custom language's .java file correctly.
)
pause