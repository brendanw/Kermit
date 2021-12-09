#! /bin/bash
set -e

for i in `ls samples`; do
echo "---- $i ----"
if [ "$i" != "sample-crashlytics" ]
then
cd samples/$i
./gradlew ciTest
cd ../..
fi
done