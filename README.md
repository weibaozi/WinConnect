# WinConnect PC Program

## Update - 05/29/2023

### New Features Added

1. **Hardware Info Displayed on PC Program**: With this update, you can now view detailed hardware information directly within the WinConnect PC program. This feature provides you with essential data about your computer's hardware components, such as CPU, RAM.

2. **Hardware Data Sending**: Adding a new functionality that allows you to send hardware data from your PC to external devices or servers. To initiate the data sending process, click the "SendBox" checkBox in the WinConnect PC program interface. Before clicking the SendBox, make sure to edit the IP address and port number that match your receiving device in the program. 

### Bugs Fixed

1. **Program Freeze While Creating Multiple Socket**: a bug that caused the WinConnect PC program to freeze when attempting to create multiple receiving sockets.

### New Files Added

1. **hardwareInfo.py**: This new file has been added to the WinConnect PC program. It contains the necessary code to retrieve and display the hardware information on the PC program. The hardwareInfo.py file is responsible for collecting the system's hardware data/
2. **hardwareInfoSender.py**: Another new file, hardwareInfoSender.py, has also been added to WinConnect PC which sending hardware data to mobile reveiver via TCP.


![5E52A3D0-9CC0-4523-B8B1-20B88C63EEB5_1_101_o]([relative/path/to/image.png](https://github.com/weibaozi/WinConnect/assets/123599069/27e1bf2e-f864-4345-ae2b-fea942be2f96)
