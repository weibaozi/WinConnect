# Receiver Program (receiver.cpp)

This program receives a file over a TCP connection from a sender program. It listens for incoming connections, receives the file name and contents, and saves the file locally.

## Usage
./receiver

## Dependencies

- C++ Compiler (Supporting C++11)
- `sys/socket.h`
- `arpa/inet.h`

## Compilation
g++ -o receiver receiver.cpp

## Running

## Notes

- Make sure the sender program is running and connected to the receiver before running the receiver program.
- The receiver program listens for incoming connections on port `8080`. Modify the code to change the listening port as needed.
- The program notifies the user when a connection is established and when the file transfer is in progress.
- The received file is saved in the same directory with the name provided by the sender.
