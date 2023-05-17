import socket

port = 51234
ip_address = '10.0.0.217'
PHRASE = 'exit'
def tcp_receive(port,func=None):
   # Create a socket object
    s = socket.socket()

    # Bind the socket to a local host and port
    s.bind(("", port))

    # Listen for incoming connections
    s.listen(1)

    while True:
        print('Waiting for a new connection...')
        
        # Wait for a client to connect
        conn, addr = s.accept()
        
        # Receive data until the client disconnects
        with conn:
            print(f'Connected by {addr}')
            while True:
                try:
                    data = conn.recv(1024)
                    if not data:
                        break
                    text = data.decode('utf-8')
                    print(f'Received: {text}')
                    if func:
                        func(text)
                    if text.strip() == PHRASE:
                        print(f'Received {PHRASE}, closing connection...')
                        conn.close()
                        break
                    ack = "ACK".encode()
                    conn.send(ack)
                except ConnectionResetError:
                    print(f'Connection closed by {addr}')
                    conn.close()
                    print("Connection closed exiting.....")
                    return 0
                
def receive_console(port):
    # Create a socket object
    s = socket.socket()

    # Bind the socket to a local host and port
    s.bind(("", port))

    # Listen for incoming connections
    s.listen(1)


    while True:
        print('Waiting for a new connection...')
        
        # Wait for a client to connect
        conn, addr = s.accept()
        
        # Receive data until the client disconnects
        with conn:
            print(f'Connected by {addr}')
            while True:
                try:
                    data = conn.recv(1024)
                    if not data:
                        break
                    text = data.decode('utf-8')
                    print(f'Received: {text}')
                    if text.strip() == PHRASE:
                        print(f'Received {PHRASE}, closing connection...')
                        conn.close()
                        break
                    ack = "ACK".encode()
                    conn.send(ack)
                except ConnectionResetError:
                    print(f'Connection closed by {addr}')
                    conn.close()
                    print("Connection closed exiting.....")
                    return 0
    # # Receive the "Hello, world!" string from the sender
    # message = conn.recv(1024).decode()
    # print(message)

    # # Send an acknowledgement back to the sender
    # ack = "ACK".encode()
    # conn.send(ack)

    # # Close the socket connection
    # conn.close()
    # s.close()

# tcp_receive(port)
# print("finished")