import socket

port = 31234
ip_address = '127.0.0.1'
PHRASE = 'exit'

def tcp_send(ip_address, port, message):
    # Create a socket object
    s = socket.socket()

    # Connect to the IP address and port
    s.connect((ip_address, port))

    message=message.encode('utf-8')
    # Send the "Hello, world!" string to the receiver
    s.send(message)

    # Wait for the acknowledgement from the receiver
    ack = s.recv(1024)

    # Close the socket connection
    s.close()

def send_console(ip_address, port):
    # Create a socket object
    s = socket.socket()

    # Connect to the IP address and port
    s.connect((ip_address, port))

    # Send the "Hello, world!" string to the receiver
    message = "Hello, world!".encode()
    s.send(message)

    # Wait for the acknowledgement from the receiver
    while True:

        if recvThread.stop_flag:  # Check the stop_flag
            print("Thread stopped.")
            break

        message=input("Enter message: ")
        s.send(message.encode())
        if message.strip() == PHRASE:
            print(f'Sent {PHRASE}, closing connection...')
            s.close()
            break

    # Close the socket connection
    s.close()

# send_console(ip_address, port)