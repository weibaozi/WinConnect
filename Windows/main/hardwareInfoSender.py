import socket
import time
from hardwareInfo import get_cpu_usage, get_ram_usage

receiver_ip = '127.0.0.1'
receiver_port = 2569

sendFlag = True
sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

# Connect to the receiver
def connect_phone(receiver_ip, receiver_port):
    global sock
    sock.settimeout(2)  # Set timeout to 2 seconds
    try:
        sock.connect((receiver_ip, receiver_port))
        print("Connected to the receiver successfully!")
    except socket.timeout:
        print("Connection attempt timed out.")
    except Exception as e:
        print(f"Error connecting to the receiver: {e}")
        exit(1)


def send_data(data):
    try:
        sock.sendall(data.encode())
        #print("Data sent successfully!")
    except Exception as e:
        print(f"Error sending data: {e}")
        stopSending()

def sendInfo():
    global sendFlag
    sendFlag = True
    print("Sending PC hardward usage...")
    while sendFlag:
   
        cpu_usage = get_cpu_usage()
        ram_usage = get_ram_usage()

        data = f"{cpu_usage},{ram_usage}"
        send_data(data)

        # Sleep for a certain amount of time before the next iteration
        # Adjust the sleep duration based on your needs
        time.sleep(0.2)
    
    global sock
    sock.close()
    print("Disconnected with cell phone")



def stopSending():
    global sendFlag
    sendFlag = False

#if __name__ == "__main__":
#    connect_phone()
#    sendInfo()
