Sample for Spring batch using command line job runner. This program willbe run with the script provided below:

```shell
#!/bin/bash

# get the project directory absolute path
PROJECTDIR=$1

# get the present working directory absolute path
CURRENTDIR=$(pwd)

# get the job name
JOBNAME=$2

# get the input file absolute path
FILEURL=$3

# go to the local project folder
# cd /home/akhil/Development/springBatchExample
echo "goto $PROJECTDIR"
cd $PROJECTDIR

# package the jar (excluding the tests), this will also copy the dependencies in target/libs directory
echo "Packaging the jar and copying dependencies in target/libs directory"
mvn clean package -DskipTests=true

# exit the local project folder
# cd /home/akhil/Development
echo "goto $CURRENTDIR"
cd $CURRENTDIR

# run the spring boot application that has the spring batch configuration
java -jar ${PROJECTDIR}/target/*.jar -classpath "${PROJECTDIR}/target/libs/*" --spring.batch.job.names=${JOBNAME} inputFileUrl=${FILEURL}
```

This program will run a spring batch job ( **job name** provided in **JOBNAME** ) to read the input file provided using the job parameter **inputFileUrl** and converts all the product names in the input file to lowercase and save all the converted data in in-memory based database. After loading the data in database, it will display the loaded data as provided in below example:

```
2020-07-26 15:32:47.361  INFO 3404 --- [           main] .e.b.l.JobCompletionNotificationListener : FOUND <InputData(id=7, subscriberId=78901234, productName=abc, amount=15.23)> in the database
```