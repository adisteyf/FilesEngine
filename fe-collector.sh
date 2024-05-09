#!/bin/bash
# START THIS SCRIPT ONLY IN FILESENGINE DIRECTORY!!! (.../IdeaProjects/FilesEngine)
# Clear folder
rm -rf for-zip
mkdir for-zip
# Copy assets/ in folder
cp -r assets/ for-zip/
# Create scripts for start Files Engine
cd for-zip/
echo "#!/bin/bash" > запустить\ движок.sh
echo "java -jar FilesEngine.jar" >> запустить\ движок.sh
echo "@echo off" > запустить\ движок.bat
echo "java -jar FilesEngine.jar" >> запустить\ движок.bat
# Add .jar in for-zip
forzipdir="$(pwd)"
cd ..
cd out/artifacts/FilesEngine_jar
cp FilesEngine.jar $forzipdir
cd $forzipdir
cd ..
rm -f fe.zip
zip -r fe.zip for-zip/