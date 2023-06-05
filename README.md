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



# WinConnect-PC 3.0 Update 
## Update - 06/04/2023
## Folder: WinConnected-PC-3.0-Update

## Update Overview
This update to WinConnect-PC brings several new features and enhancements to improve the overall user experience. The update focuses on app selection and path saving, displaying the IP address on the user interface (UI), and introducing error message prompts on the UI.

## Features

### 1. App Selection for remote start and Path Saving
A new feature that allows you to select specific applications for remote start within WinConnect-PC. This feature enables you to choose the applications you want to run remotely, providing more flexibility and control over your remote connections.

### 2. IP Display on UI
Added an IP display on the UI. The IP address is now prominently visible, enabling you to quickly identify the current connection details without having to navigate through multiple settings. This feature enhances the monitoring and management of your network connections within WinConnect-PC.

### 3. Error Message Prompt on UI
An error message prompt on the UI. Whenever an error occurs, you will now receive a clear and informative message on the UI, helping you understand the issue and take appropriate action. This feature streamlines the troubleshooting process and ensures a smoother user experience.
