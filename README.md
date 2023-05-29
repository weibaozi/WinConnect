# WinConnect PC Program

## Update - 05/29/2023

In this update of WinConnect PC, we have introduced new features, fixed some bugs, and added new files to enhance the functionality and performance of the program.

### New Features Added

1. **Hardware Info Displayed on PC Program**: With this update, you can now view detailed hardware information directly within the WinConnect PC program. This feature provides you with essential data about your computer's hardware components, such as CPU, RAM, storage devices, graphics card, and more. It enables you to have a comprehensive overview of your system's specifications at a glance.

2. **Hardware Data Sending**: We have implemented a new functionality that allows you to send hardware data from your PC to external devices or servers. To initiate the data sending process, click the "SendBox" button in the WinConnect PC program interface. Before clicking the button, make sure to edit the IP address and port number in the code to match the receiving device. 

### Bugs Fixed

1. **Program Freeze While Creating Multiple Socket**: We have addressed a bug that caused the WinConnect PC program to freeze when attempting to create multiple receiving sockets.

### New Files Added

1. **hardwareInfo.py**: This new file has been added to the WinConnect PC program. It contains the necessary code to retrieve and display the hardware information on the PC program. The hardwareInfo.py file is responsible for collecting the system's hardware data/
2. 
3. **hardwareInfoSender.py**: Another new file, hardwareInfoSender.py, has also been added to WinConnect PC which sending hardware data to mobile reveiver via TCP.

