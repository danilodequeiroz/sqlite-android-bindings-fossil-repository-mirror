#!/usr/bin/env bash

set -e

if [[ $# -ne 1 ]] ; then
  echo "$0 <path to android base>"
  exit -1
fi
BASE=/home/dan/tmp/git/base/

FILES=`find sqlite3/src/main/java/ -name *.java | grep -v CloseGuard`
FILES+=" "
FILES+=`find sqlite3/src/main/jni/ -name android_database*.cpp`
FILES+=" "
FILES+=`find sqlite3/src/main/jni/ -name android_database*.h`

for f in $FILES ; do
  B=`basename $f`
  echo "Copying $B..."
  cp `find $BASE -name $B` $f
done

