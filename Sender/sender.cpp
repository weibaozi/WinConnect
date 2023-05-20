#include <iostream>
#include <fstream>
#include <string>
#include <sys/socket.h>
#include <arpa/inet.h>

#define PORT 8080

int main(int argc, char* argv[]) {
    if (argc != 2) {
        std::cerr << "Usage: " << argv[0] << " [file_path]" << std::endl;
        return 1;
    }

    const std::string filePath = argv[1];
    const std::string fileName = filePath.substr(filePath.find_last_of('/') + 1);

    int sock = 0;
    struct sockaddr_in serv_addr;
    char buffer[10240] = {0};
    std::ifstream file;

    // Create socket
    if ((sock = socket(AF_INET, SOCK_STREAM, 0)) < 0) {
        std::cerr << "Socket creation error" << std::endl;
        return -1;
    }

    serv_addr.sin_family = AF_INET;
    serv_addr.sin_port = htons(PORT);

    // Convert IPv4 and IPv6 addresses from text to binary form
    if (inet_pton(AF_INET, "127.0.0.1", &serv_addr.sin_addr) <= 0) {
        std::cerr << "Invalid address/ Address not supported" << std::endl;
        return -1;
    }

    // Connect to the receiver
    if (connect(sock, (struct sockaddr *)&serv_addr, sizeof(serv_addr)) < 0) {
        std::cerr << "Connection failed" << std::endl;
        return -1;
    }

    // Open the file
    file.open(filePath, std::ios::binary);
    if (!file) {
        std::cerr << "File open error" << std::endl;
        return -1;
    }

    // Get the total size of the file
    file.seekg(0, std::ios::end);
    long long totalBytes = file.tellg();
    file.seekg(0, std::ios::beg);

    // Send the file name
    send(sock, fileName.c_str(), fileName.length(), 0);

    // Send file contents
    long long sentBytes = 0;
    int progress = 0;
    while (!file.eof()) {
        file.read(buffer, sizeof(buffer));
        int bytesRead = file.gcount();
        int bytesSent = send(sock, buffer, bytesRead, 0);
        if (bytesSent < 0) {
            std::cerr << "File send error" << std::endl;
            return -1;
        }
        sentBytes += bytesSent;
        int newProgress = (int)((double)sentBytes / totalBytes * 100);
        if (newProgress != progress) {
            progress = newProgress;
            std::cout << "Progress: " << progress << "%" << std::endl;
        }
    }

    // Close the file
    file.close();

    std::cout << "File sent successfully" << std::endl;

    return 0;
}
