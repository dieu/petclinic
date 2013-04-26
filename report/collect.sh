#!/bin/sh
JUNIT_TEST_REPORTS=$1
FILE_NAME=$2
(cat header.txt;echo "<root>";tail -n+2 -q $JUNIT_TEST_REPORTS/*.xml;echo "</root>") > $2.xml
