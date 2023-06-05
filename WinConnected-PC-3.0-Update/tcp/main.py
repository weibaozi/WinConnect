import sys
from PyQt5.QtCore import pyqtSignal, QObject, QTimer
from PyQt5.QtWidgets import QApplication, QDialog, QWidget, QMainWindow, QVBoxLayout, QPushButton, QListWidgetItem
from Ui_TCP import Ui_MainWindow
import time
from threading import Thread
from receiver import tcp_receive, get_IPaddress
from sender import tcp_send
from WinDecode import decode_function
from hardwareInfo import get_cpu_usage, get_ram_usage
from hardwareInfoSender import connect_phone, sendInfo, stopSending
from PathSaver import set_filePath, delete_path, delete_all_paths, get_pathList, get_fileNameList
import socket
from PyQt5.QtCore import Qt
import os

class my_signal(QObject):
    setRecvText = pyqtSignal(str)

my_signal = my_signal()

class MyMainForm(QMainWindow, Ui_MainWindow):
    def __init__(self, parent=None):
        super().__init__()
        self.ui = Ui_MainWindow()
        self.ui.setupUi(self)
        self.band()
        self.recvThread = None
        self.sendInfoThread = None

        # Create a QTimer object
        self.timer = QTimer()
        self.timer.timeout.connect(self.update_cpu_ram_usage)
        self.timer.start(1000)  # update every second

        self.update_file_list()
        self.updata_status(0)


    def band(self):
        self.ui.sendButton.clicked.connect(self.send_message)
        my_signal.setRecvText.connect(self.ui.recvText.setHtml)
        self.ui.recvSwitch.stateChanged.connect(self.recv_switch)
        self.ui.sendSwitch.stateChanged.connect(self.send_switch)
        self.ui.SelectFileButton.clicked.connect(self.addApp)
        self.ui.deleteFileButton.clicked.connect(self.delete_selected_files)
        self.ui.deleteAllButton.clicked.connect(self.delteAllApp)
        self.ui.IpLabel.setText(f"PC IP Address: {get_IPaddress()}")


    def send_message(self):
        text = self.ui.sendText.toPlainText()
        ip = self.ui.inputIP.text()
        port = self.ui.sendPort.value()
        Thread(target=tcp_send, args=(ip, port, text)).start()

    def recv_switch(self):
        if self.ui.recvSwitch.isChecked():
            port = self.ui.recvPort.value()
            recvThread = Thread(target=tcp_receive, args=(port, self.updata_status, decode_function, my_signal.setRecvText.emit))
            recvThread.start()

        else:
            print("stopping.........")
            self.updata_status(0)
            stopSending()
            if self.recvThread:
                self.recvThread.stop()
                self.recvThread.join()

    def send_switch(self):
        if self.ui.sendSwitch.isChecked():
            connect_phone(self.ui.inputIP.text(), self.ui.sendPort.value())
            sendInfoThread = Thread(target=sendInfo)
            sendInfoThread.start()
        else:
            stopSending()
            if self.sendInfoThread:
                self.sendInfoThread.stop()
                self.sendInfoThread.join()

    def update_cpu_ram_usage(self):
 
        cpu_usage = get_cpu_usage()
        ram_usage = get_ram_usage()

        # update the labels
        self.ui.cpuLabel.setText(f"CPU Usage: {cpu_usage}%")
        self.ui.ramLabel.setText(f"RAM Usage: {ram_usage}%")  


    def send_usage_data(self):
        cpu_usage = get_cpu_usage()
        ram_usage = get_ram_usage()
        ip = self.ui.inputIP.text()
        port = self.ui.sendPort.value()

        # Construct the data as a tuple or a list
        data = (cpu_usage, ram_usage)

        data_bytes = bytes(data)

        tcp_send(ip, port, data_bytes)

    def file_path_remove(self):
        print(get_pathList())
        print(get_fileNameList())


    def addApp(self):
        set_filePath()
        self.update_file_list()

    
    def delete_selected_files(self):
        for index in range(self.ui.fileListWidget.count()):
            item = self.ui.fileListWidget.item(index)
            if item.checkState() == Qt.Checked:
                file_name = item.text()
                full_path = self.file_dict.get(file_name)  # Get the full path from the dictionary
                if full_path:  # If we found the path
                    delete_path(full_path)
        self.update_file_list()  # Refresh the list

    
    def delteAllApp(self):
        delete_all_paths()
        self.update_file_list()


    # update_file_list
    def update_file_list(self):
        self.ui.fileListWidget.clear()  # First clear the widget
        self.file_dict = {}  # Initialize a new dictionary to hold the filenames and paths
        path_list = get_pathList()  # Get the full paths

        for path in path_list:
            file_name = os.path.basename(path)  # Extract the filename from the path
            self.file_dict[file_name] = path  # Add to the dictionary

            item = QListWidgetItem(file_name)
            item.setFlags(item.flags() | Qt.ItemIsUserCheckable)
            item.setCheckState(Qt.Unchecked)
            self.ui.fileListWidget.addItem(item)  # Add each file name to the widget

    # updata_status
    # A function update current status on UI
    # code == 0 Disconected
    # code == 1 Connected
    # code == 2 Connecting with phone
    # code == 3 Detect a error    
    def updata_status(self, code, error_message = None):
        if code == 0:
            self.ui.statusLabel.setStyleSheet("color: black")
            self.ui.statusLabel.setText("Disconnect")
        if code == 1:
            self.ui.statusLabel.setStyleSheet("color: green")
            self.ui.statusLabel.setText("Connect")
        if code == 2:
            self.ui.statusLabel.setStyleSheet("color: black")
            self.ui.statusLabel.setText("Conecting...")
        if code == 3:
            self.ui.statusLabel.setStyleSheet("color: red")
            self.ui.statusLabel.setText("Error: " + error_message)


    # def handle_click(self):
    #     def innerFunc():
    #         a = self.ui.inputA.value()
    #         b = self.ui.inputB.value()
    #         time_cost=self.ui.latency.value()*10
    #         for index,_ in enumerate(range(time_cost)):
                
    #             progress = index * 100 //time_cost
    #             my_signal.setProgress.emit(progress)
    #             time.sleep(0.1)
    #         my_signal.setProgress.emit(100)
    #         result=a + b
    #         my_signal.setRes.emit(str(result))
    #     task=Thread(target=innerFunc)
    #     task.start()

if __name__ == "__main__":
    # create main application
    app = QApplication(sys.argv)

    # create the dialog UI
    myWin = MyMainForm()
    myWin.show()

    # execute the application
    sys.exit(app.exec_())
