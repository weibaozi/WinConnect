#include <iostream>
#include <fstream>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <string.h>

#define PORT 8080

int main() {
    int server_fd, new_socket, valread;
    struct sockaddr_in address;
    int addrlen = sizeof(address);
    char buffer[10240] = {0};
    std::ofstream file;
    std::string filename;

    // Create socket
    if ((server_fd = socket(AF_INET, SOCK_STREAM, 0)) == 0) {
        std::cerr << "Socket creation error" << std::endl;
        return -1;
    }

    address.sin_family = AF_INET;
    address.sin_addr.s_addr = INADDR_ANY;
    address.sin_port = htons(PORT);

    // Bind the socket to localhost:8080
    if (bind(server_fd, (struct sockaddr *)&address, sizeof(address)) < 0) {
        std::cerr << "Bind failed" << std::endl;
        return -1;
    }

    // Start listening for connections
    if (listen(server_fd, 3) < 0) {
        std::cerr << "Listen failed" << std::endl;
        return -1;
    }

    std::cout << "Waiting for a connection..." << std::endl;

    // Accept a new connection
    if ((new_socket = accept(server_fd, (struct sockaddr *)&address, (socklen_t *)&addrlen)) < 0) {
        std::cerr << "Accept failed" << std::endl;
        return -1;
    }

    std::cout << "Connection established. Receiving file..." << std::endl;

    // Receive the file name
    valread = recv(new_socket, buffer, sizeof(buffer), 0);
    if (valread <= 0) {
        std::cerr << "File name receive error" << std::endl;
        return -1;
    }
    filename = buffer;

    // Create the file
    file.open(filename, std::ios::binary);
    if (!file) {
        std::cerr << "File creation error" << std::endl;
        return -1;
    }

    // Receive file contents and write to the file
    while (true) {
        valread = recv(new_socket, buffer, sizeof(buffer), 0);
        if (valread <= 0)
            break;
        file.write(buffer, valread);
    }

    // Close the file
    file.close();

    std::cout << "File received successfully: " << filename << std::endl;

    return 0;
}
