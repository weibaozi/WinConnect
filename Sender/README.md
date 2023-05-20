# Sender Program (sender.cpp)

This program sends a file over a TCP connection to a receiver program. It takes a file path as a command-line argument and sends the file contents to the receiver.

## Usage
./sender [file_path]

Replace `[file_path]` with the actual path to the file you want to send.

## Dependencies

- C++ Compiler (Supporting C++11)
- `sys/socket.h`
- `arpa/inet.h`

## Compilation
g++ -o sender sender.cpp

## Running
./sender [file_path]


## Notes

- Make sure the sender program is running and connected to the receiver before running the receiver program.
- The receiver program listens for incoming connections on port `8080`. Modify the code to change the listening port as needed.
- The program notifies the user when a connection is established and when the file transfer is in progress.
- The received file is saved in the same directory with the name provided by the sender.

