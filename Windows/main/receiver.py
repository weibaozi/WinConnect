import socket
#from WinDecode import decode_function

#port = 31234
#ip_address = '10.0.0.89'
PHRASE = 'exit'
def get_IPaddress():
    hostname = socket.gethostname()
    ip_address = socket.gethostbyname(hostname)
    return ip_address

def tcp_receive(port, updata_status, func=None, debug_func=None):
   # Create a socket object

    s = socket.socket()

    #get your pc ip address
    ip_address = get_IPaddress()

    # Bind the socket to a local host and port
    try:
        s.bind((ip_address, port))
    except socket.error as e:
        updata_status(3, f"Error binding socket: Please use other port number")
        print(f'Error binding socket: {e}')
        return
    # Listen for incoming connections
    s.listen(1)

    while True:
        print('Waiting for a new connection...')
        
        # Wait for a client to connect
        updata_status(2)
        conn, addr = s.accept()
        updata_status(1)

        # Receive data until the client disconnects
        with conn:
            print(f'Connected by {addr}')
            while True:
                reply=None
                try:
                    data = conn.recv(1024)
                    if not data:
                        break
                    text = data.decode('utf-8')
                    print(f'Received: {text}')
                    if text.strip() == PHRASE:
                        print(f'Received {PHRASE}, closing connection...')
                        conn.send("exit".encode())
                        conn.close()
                        return
                    
                    if func:
                        reply=func(text)
                    if debug_func:
                        debug_func(text)

                    if reply is not None:
                        conn.send(reply.encode())
                    else:
                        conn.send("ACK".encode())

                except ConnectionResetError:
                    print(f'Connection closed by {addr}')
                    updata_status(1)
                    conn.close()
                    print("Connection closed exiting.....")
                    break
            
                
