# Clock
Investigating Java Date and Time functions..

## Overview
This project has been set up as a Maven project that uses JavaFX, FXML and 
CSS to render the GUI. Maven can be run from the command line as shown below.
Maven resolves dependencies and builds the application independently of an IDE.

## Dependencies
Clock is dependent on the following:

  * Java 15.0.1
  * Apache Maven 3.6.3

The code has been structured as a standard Maven project which requires Maven 
and a JDK to be installed. A quick web search will help, but if not 
[Apache](https://maven.apache.org/install.html) should guide you through the
install. Also [OpenJFX](https://openjfx.io/openjfx-docs/) can help set up your 
favourite IDE.

## Cloning and Running
The following commands clone and execute the code:

    git clone https://github.com/PhilLockett/Clock.git
	cd Clock/
	mvn clean javafx:run

### Updating
To get the latest code, run the following command from the EBookGen directory:

    git pull --rebase origin

Then use the mvn command above to run it. If the update fails, delete the 
entire Clock directory and execute the "Cloning and Running" steps again.

  
