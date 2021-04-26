src/main/java coverage: 83.9%
# Java Based CRUD: An Inventory Management System

A command line interface that interacts with a relational database containing the customers, products, orders, and orders_items tables. The user can create, read, update and delete entries, as well as retrieve the total cost of orders.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

#### Java 
Go to this link [download JDK](https://www.oracle.com/java/technologies/javase-jdk16-downloads.html)
Click the Download link that corresponds to the .exe for your version of Windows.
*e.g. jdk-16.0.1_windows-x64_bin.exe*

Run the installer with admin priveleges and follow the instructions.
Check if the installer set the *PATH* veriable for you, as JDK15+ does this automatically.

To set the *PATH* variable, browse to **Control Panel** and then **System.** 
Click **Advanced** and then **Environment Variables.**
Add the location of the *bin* folder of your JDK installation to the **PATH** variable in **System Variables.**
Typically, the full path is:
*C:\Program Files\Java\jdk-16\bin*

To verify your JDK installation, launch a command prompt window and type:
*javac - version*
And
*java -version*

If you don't get a successful response from these commands, you will need to return to the installation process.

#### Maven

Go to this link [download Maven](https://maven.apache.org/download.cgi)
Click the Binary zip archive download under 'Files'.
*e.g. apache-maven-3.8.1-bin.zip*

Once that has downloaded, unzip it into your C:\Program Files folder.
Copy the path for the installation.
*e.g. C:\Program Files\apache-maven-3.6.3*

To set the *PATH* variable, browse to **Control Panel** and then **System.** 
Click **Advanced** and then **Environment Variables.**
Add a new system variable using that path, with the name **M2_HOME** or something recognisable.
Finally, add **%M2_HOME%\bin** to the **PATH** variable.

To verify the Maven installation, launch a command prompt window and type:
*mvn -version*

If you don't get a successful response from the command, you will need to return to the installation process.

#### Eclipse

Go to this link [download Eclipse](https://www.eclipse.org/downloads/)
Download the X86_64 executable.
Run the .exe and complete the setup wizard to install Eclipse, default/java options are fine throughout.

#### MySQL Server

Go to this link [download MySQL](https://dev.mysql.com/downloads/windows/installer/8.0.html)
Download the *mysql-installer-community-8.0.24.0.msi*
Complete the setup wizard, making sure to select MySQL server.
All default options are fine, with a default password of **root** for the server

### Installing

A step by step series of examples that tell you how to get a development env running

#### Clone the project from GitHub

Use the following command to clone this repository to your local machine:
*git clone https://github.com/ALowtonQA/ALowtonQA_assessment.git*

#### Import the project to Eclipse as an existing Maven project

In Eclipse, choose *File > Import*.
Then, under **Maven** choose **Existing Maven Project**.
Select the previously imported repository as the root directory.
Ensure the *pom.xml* file is visible and selected, then click Import.

#### Run the Application

To run the application from Eclipse, simply right click the project folder in the hierarchical view on the left and *Right click > Run as > Java Application*

You will now be able to give user input to the program through the Console located at the bottom of the screen. 

To run the application from your command line, browse to the root folder of the project using a command prompt.
Then execute the following command:
*java -jar ims-0.0.1-jar-with-dependencies.jar*

## Running the tests

To run all 101 tests on the system, right click the project and *Run as > JUnit Test*

### Unit Tests 

Each of the unit tests can be ran individually.

For example, to test only the functionality of the CustomerController class:
Browse to the *src/test/java/com.qa.ims.controller* package using the hierarchical view on the left.
Locate the *CustomerControllerTest* file and *Right click > Run as > JUnit Test*
This will unit test the Customer Controller class in isolation, by testing each method functions correctly.

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Author

* [anoushlowton](https://github.com/ALowtonQA)

## License

This project is licensed under the MIT license - see the [LICENSE.md](LICENSE.md) file for details 

*For help in [Choosing a license](https://choosealicense.com/)*

## Acknowledgments

* **Chris Perrins** - *Initial work* - [christophperrins](https://github.com/christophperrins)
