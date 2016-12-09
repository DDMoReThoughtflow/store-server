# DDMoRe Thoughtflow Provenance Store

## Overview
Spring Boot application for storing provenance information. The source code is
split into sub modules which provide a separation of the code into logical
layers allowing for easy unit testing.

## Building
### Requirements
* Java 1.7 or above.

### Commands
The project uses Gradle to build the source code into the required outputs. To
build the source code into the output the command: `gradlew build` will
generate the artifacts.

#### Useful Gradlew tasks
* `test`: runs the unit tests.
* `bootRun`: starts the provenance storage server.
* `eclipse`: generates the eclipse project structure/configuration for import into eclipse.
* `cleanEclipse`: removes the eclipse configuration files.
* `clean`: cleans the build folders.
* `tasks`: displays the full list of tasks.

### Output
This generates an executable jar file which can be found in the `build/libs`
directory.

## Implementation
The default port for the rest service is `10100`, this is configured in the
application.yml file.

## Running
Spring boot packages the application as an uber jar so running it is as simple
as the following command.
```sh
$ java -jar thoughtflow-store-server-0.1.0-SNAPSHOT.jar
```

## LICENSE & Copyright

Copyright 2016, Mango Solutions Ltd - All rights reserved.

SPDX-License-Identifier:   AGPL-3.0
