import socket
from tkinter import *
from tkinter import filedialog, messagebox
import threading
import os

server_port = 12345

def start_server():
    def server_thread():
        server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        server_socket.bind(("localhost", server_port))
        server_socket.listen(1)

        client_socket, client_address = server_socket.accept()

        filename = client_socket.recv(1024).decode()

        if not os.path.exists('receivedFile'):
            os.makedirs('receivedFile')
            
        with open('receivedFile/'+filename, 'wb') as file:
            while True:
                data = client_socket.recv(1024)
                if not data:
                    break
                file.write(data)
                
        client_socket.close()
        messagebox.showinfo('File Received', 'File received successfully')

    threading.Thread(target=server_thread).start()


def select_file():
    filename = filedialog.askopenfilename()
    if filename:
        send_file(filename)


def send_file(filename):
    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    client_socket.connect(("localhost", server_port))

    client_socket.send(filename.split('/')[-1].encode())

    with open(filename, 'rb') as file:
        while True:
            data = file.read(1024)
            if not data:
                break
            client_socket.send(data)

    client_socket.close()
    messagebox.showinfo('File Sent', 'File sent successfully')

def set_server_port():
    global server_port
    server_port = int(port_entry.get())  # get port number from entry
    messagebox.showinfo('Port Set', 'Server port set to ' + str(server_port))

root = Tk()
root.title('WinConnect')
root.geometry('300x300')  # set window size
root.configure(bg='white')  # set window background color


space_label = Label(root, bg='white')  # create a label to set vertical space
space_label.pack(pady=10)  # set vertical space

port_label = Label(root, text="Server Port:", bg='white')  # create label "Server Port:"
port_label.pack()

port_entry = Entry(root)  # create an entry to get port number
port_entry.pack()

set_port_button = Button(root, text="Set Port", command=set_server_port, bg='white')  # create a button to set port
set_port_button.pack()

space_label = Label(root, bg='white')  # create a label to set vertical space
space_label.pack(pady=10)  # set vertical space


start_button = Button(root, text="Start Server", command=start_server, bg='white')  # set toolbar background color
start_button.pack()

space_label = Label(root, bg='white')  # create a label to set vertical space
space_label.pack(pady=10)  # set vertical space

select_button = Button(root, text="Select File", command=select_file, bg='white')  # set toolbar background color
select_button.pack()
root.mainloop()
