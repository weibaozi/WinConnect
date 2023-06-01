# import required modules
import sys
from PyQt5.QtCore import Qt,pyqtSignal,QObject
from PyQt5.QtWidgets import QApplication, QDialog, QWidget,QMainWindow
from Ui_TCP import Ui_MainWindow
import time
from threading import Thread
from receiver import tcp_receive
from sender import tcp_send
from WinDecode import decode_function
import socket
class my_signal(QObject):
    setRecvText=pyqtSignal(str)
my_signal=my_signal()
# create main window class
class MyMainForm(QMainWindow, Ui_MainWindow):
    def __init__(self, parent=None):
        super().__init__()
        self.ui=Ui_MainWindow()
        self.ui.setupUi(self)
        self.band()
        self.recvThread=None
    def band(self):
        self.ui.sendButton.clicked.connect(self.send_message)
        my_signal.setRecvText.connect(self.ui.recvText.setHtml)
        self.ui.recvSwitch.stateChanged.connect(self.recv_switch)
        # my_signal.setRes.connect(self.set_result)
        # my_signal.setProgress.connect(self.set_progress)
    def send_message(self):
        text=self.ui.sendText.toPlainText()
        ip=self.ui.inputIP.text()
        port=self.ui.sendPort.value()
        Thread(target=tcp_send,args=(ip,port,text)).start()

    def recv_switch(self):
        if self.ui.recvSwitch.isChecked():
            port=self.ui.recvPort.value()
            recvThread=Thread(target=tcp_receive,args=(port,decode_function, my_signal.setRecvText.emit))
            recvThread.start()
            # my_signal.setRecvText.emit("start receiving")
        else:
            print("stopping.........")
            if self.recvThread:
                self.recvThread.stop()
                self.recvThread.join()
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
