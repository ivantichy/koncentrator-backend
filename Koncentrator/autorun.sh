#!/bin/bash

state=1
command="cd target && ls -l | grep kon.jar"

previous=`eval $command`

while sleep 1;
do

actual=`eval $command`

#echo $actual
#echo $previous
#echo $state
#echo $command

if [ $state == 1 ] && [ "$actual" != "" ]; then
	
	# started to write (file size)
	if [ "$actual" != "$previous" ]; then
		state=2
	fi
	
fi

if [ $state == 2 ]; then
	
	# stop writing (file size)
	if [ "$actual" == "$previous" ]; then
		
		echo "do the stuff"
		
		bash testng.sh
		#reset state
		state=1
	
	fi
fi

previous=$actual
 
done


#while inotifywait -e close_write,delete target/kon.jar ; do echo aa; done
