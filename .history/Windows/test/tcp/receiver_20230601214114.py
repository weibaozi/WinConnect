import socket

<<<<<<< HEAD:test/tcp/receiver.py
port = 8787
ip_address = '127.0.0.1'
=======
port = 51234
ip_address = '10.0.0.217'
>>>>>>> main:Windows/test/tcp/receiver.py
PHRASE = 'exit'
def tcp_receive(port,func=None, debug_func=None):
   # Create a socket object
    s = socket.socket()

    # Bind the socket to a local host and port
    try:
        s.bind((ip_address, port))
    except socket.error as e:
        print(f'Error binding socket: {e}')
        return
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
                    if debug_func:
                        debug_func(text)
            
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
            
                
def main():
    tcp_receive()
