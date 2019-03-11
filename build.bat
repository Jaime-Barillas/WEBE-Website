@ECHO OFF
SETLOCAL

SET "tomcatAppDir=C:\Users\jaime\Documents\DC-Y2-S2\webe\tomcat8\webapps\barillasj\"
SET "tomcatClassDir=%tomcatAppDir%\WEB-INF\classes\webd4201\barillasj\"
SET "projectDir=C:\Users\jaime\Documents\DC-Y2-S2\webe\website\"
SET "classFilesDir=%projectDir%build\classes\webd4201\barillasj\"

ECHO.
ECHO Compiling css styles in %classFilesDir%webpages\css
rem NOTE: sass is itself another batch file so it must be called with call
CALL sass --no-source-map %classFilesDir%webpages\css\:%classFilesDir%webpages\css\

ECHO Copying css styles and images in %classFilesDir%webpages\{css,images}\ to %tomcatAppDir%{css,images}
rem 1>NUL stops any output to stdout from showing up in the console, 2>NUL works for stderr
COPY /Y %classFilesDir%webpages\css\*.css %tomcatAppDir%css 1>NUL 2>NUL
COPY /Y %classFilesDir%webpages\images\*.png %tomcatAppDir%images 1>NUL 2>NUL
COPY /Y %classFilesDir%webpages\images\*.jpg %tomcatAppDir%images 1>NUL 2>NUL

ECHO Copying jsp pages in %classFilesDir%webpages to %tomcatAppDir%
COPY /Y %classFilesDir%webpages\*.jsp %tomcatAppDir% 1>NUL

ECHO Copying class files in %classFilesDir% and subdirectories to %tomcatClassDir%
rem Copy all the .class files to the tomcat webapp folder under the WEB-INF\classes\ subfolder
XCOPY /Y /S /C /Q %classFilesDir%*.class %tomcatClassDir% 1>NUL

ECHO Updating web.xml file
webbuild.exe "%tomcatAppDir%WEB-INF\\" "webd4201\barillasj" "WEBD4201 BarillasJ" "Website for WEBD4201"
ECHO Done!

ENDLOCAL