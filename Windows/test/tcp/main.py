import sys
from PyQt5.QtCore import pyqtSignal, QObject, QTimer
from PyQt5.QtWidgets import QApplication, QDialog, QWidget, QMainWindow
from Ui_TCP import Ui_MainWindow
import time
from threading import Thread
from receiver import tcp_receive
from sender import tcp_send
from WinDecode import decode_function
from hardwareInfo import get_cpu_usage, get_ram_usage
from hardwareInfoSender import connect_phone, sendInfo, stopSending
import socket

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

    def band(self):
        self.ui.sendButton.clicked.connect(self.send_message)
        my_signal.setRecvText.connect(self.ui.recvText.setHtml)
        self.ui.recvSwitch.stateChanged.connect(self.recv_switch)
        self.ui.sendSwitch.stateChanged.connect(self.send_switch)

    def send_message(self):
        text = self.ui.sendText.toPlainText()
        ip = self.ui.inputIP.text()
        port = self.ui.sendPort.value()
        Thread(target=tcp_send, args=(ip, port, text)).start()

    def recv_switch(self):
        if self.ui.recvSwitch.isChecked():
            port = self.ui.recvPort.value()
            recvThread = Thread(target=tcp_receive, args=(port, decode_function, my_signal.setRecvText.emit))
            recvThread.start()
        else:
            print("stopping.........")
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
        # get the CPU and RAM usage
        cpu_usage = get_cpu_usage()
        ram_usage = get_ram_usage()

        # update the labels
        self.ui.cpuLabel.setText(f"CPU Usage: {cpu_usage}%")
        self.ui.ramLabel.setText(f"RAM Usage: {ram_usage}%")  # adjust the units as per your needs


    def send_usage_data(self):
        cpu_usage = get_cpu_usage()
        ram_usage = get_ram_usage()
        ip = self.ui.inputIP.text()
        port = self.ui.sendPort.value()

        # Construct the data as a tuple or a list
        data = (cpu_usage, ram_usage)

        # Convert the data to bytes
        data_bytes = bytes(data)

        tcp_send(ip, port, data_bytes)


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
