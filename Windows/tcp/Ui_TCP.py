# -*- coding: utf-8 -*-

# Form implementation generated from reading ui file 'g:\study\work\cse115\test\tcp\Win_UI\TCP.ui'
#
# Created by: PyQt5 UI code generator 5.15.9
#
# WARNING: Any manual changes made to this file will be lost when pyuic5 is
# run again.  Do not edit this file unless you know what you are doing.


from PyQt5 import Qt, QtCore, QtGui, QtWidgets


class Ui_MainWindow(object):
    def setupUi(self, MainWindow):
        MainWindow.setObjectName("MainWindow")
        MainWindow.resize(888, 766)
        self.centralwidget = QtWidgets.QWidget(MainWindow)
        self.centralwidget.setObjectName("centralwidget")
        self.gridLayout = QtWidgets.QGridLayout(self.centralwidget)
        self.gridLayout.setObjectName("gridLayout")
        spacerItem = QtWidgets.QSpacerItem(255, 20, QtWidgets.QSizePolicy.Expanding, QtWidgets.QSizePolicy.Minimum)
        self.gridLayout.addItem(spacerItem, 1, 0, 1, 1)
        spacerItem1 = QtWidgets.QSpacerItem(20, 144, QtWidgets.QSizePolicy.Minimum, QtWidgets.QSizePolicy.Expanding)
        self.gridLayout.addItem(spacerItem1, 2, 1, 1, 1)
        self.verticalLayout_3 = QtWidgets.QVBoxLayout()
        self.verticalLayout_3.setObjectName("verticalLayout_3")
        self.horizontalLayout = QtWidgets.QHBoxLayout()
        self.horizontalLayout.setObjectName("horizontalLayout")
        self.verticalLayout = QtWidgets.QVBoxLayout()
        self.verticalLayout.setObjectName("verticalLayout")
        self.sendButton = QtWidgets.QPushButton(self.centralwidget)
        self.sendButton.setObjectName("sendButton")
        self.verticalLayout.addWidget(self.sendButton)
        spacerItem2 = QtWidgets.QSpacerItem(20, 35, QtWidgets.QSizePolicy.Minimum, QtWidgets.QSizePolicy.Expanding)
        self.verticalLayout.addItem(spacerItem2)
        self.horizontalLayout.addLayout(self.verticalLayout)
        self.sendText = QtWidgets.QPlainTextEdit(self.centralwidget)
        self.sendText.setObjectName("sendText")
        self.horizontalLayout.addWidget(self.sendText)
        self.verticalLayout_3.addLayout(self.horizontalLayout)
        self.horizontalLayout_7 = QtWidgets.QHBoxLayout()
        self.horizontalLayout_7.setObjectName("horizontalLayout_7")
        spacerItem3 = QtWidgets.QSpacerItem(40, 20, QtWidgets.QSizePolicy.Expanding, QtWidgets.QSizePolicy.Minimum)
        self.horizontalLayout_7.addItem(spacerItem3)
        self.horizontalLayout_6 = QtWidgets.QHBoxLayout()
        self.horizontalLayout_6.setObjectName("horizontalLayout_6")
        self.label_3 = QtWidgets.QLabel(self.centralwidget)
        self.label_3.setObjectName("label_3")
        self.horizontalLayout_6.addWidget(self.label_3)
        self.recvPort = QtWidgets.QSpinBox(self.centralwidget)
        self.recvPort.setMaximum(65535)
        self.recvPort.setProperty("value", 51234)
        self.recvPort.setObjectName("recvPort")
        self.horizontalLayout_6.addWidget(self.recvPort)
        self.horizontalLayout_7.addLayout(self.horizontalLayout_6)
        spacerItem4 = QtWidgets.QSpacerItem(40, 20, QtWidgets.QSizePolicy.Expanding, QtWidgets.QSizePolicy.Minimum)
        self.horizontalLayout_7.addItem(spacerItem4)
        self.verticalLayout_3.addLayout(self.horizontalLayout_7)
        self.horizontalLayout_2 = QtWidgets.QHBoxLayout()
        self.horizontalLayout_2.setObjectName("horizontalLayout_2")
        self.verticalLayout_2 = QtWidgets.QVBoxLayout()
        self.verticalLayout_2.setObjectName("verticalLayout_2")
        self.recieve = QtWidgets.QLabel(self.centralwidget)
        self.recieve.setObjectName("recieve")
        self.verticalLayout_2.addWidget(self.recieve)
        spacerItem5 = QtWidgets.QSpacerItem(20, 35, QtWidgets.QSizePolicy.Minimum, QtWidgets.QSizePolicy.Expanding)
        self.verticalLayout_2.addItem(spacerItem5)
        self.horizontalLayout_2.addLayout(self.verticalLayout_2)
        self.recvText = QtWidgets.QTextBrowser(self.centralwidget)
        self.recvText.setObjectName("recvText")
        self.horizontalLayout_2.addWidget(self.recvText)
        self.verticalLayout_3.addLayout(self.horizontalLayout_2)
        self.gridLayout.addLayout(self.verticalLayout_3, 1, 1, 1, 1)
        spacerItem6 = QtWidgets.QSpacerItem(254, 20, QtWidgets.QSizePolicy.Expanding, QtWidgets.QSizePolicy.Minimum)
        self.gridLayout.addItem(spacerItem6, 1, 2, 1, 1)
        self.verticalLayout_5 = QtWidgets.QVBoxLayout()
        self.verticalLayout_5.setObjectName("verticalLayout_5")
        spacerItem7 = QtWidgets.QSpacerItem(20, 40, QtWidgets.QSizePolicy.Minimum, QtWidgets.QSizePolicy.Expanding)
        self.verticalLayout_5.addItem(spacerItem7)
        self.verticalLayout_4 = QtWidgets.QVBoxLayout()
        self.verticalLayout_4.setObjectName("verticalLayout_4")
        self.horizontalLayout_5 = QtWidgets.QHBoxLayout()
        self.horizontalLayout_5.setObjectName("horizontalLayout_5")
        spacerItem8 = QtWidgets.QSpacerItem(37, 17, QtWidgets.QSizePolicy.Expanding, QtWidgets.QSizePolicy.Minimum)
        self.horizontalLayout_5.addItem(spacerItem8)
        self.label_2 = QtWidgets.QLabel(self.centralwidget)
        self.label_2.setObjectName("label_2")
        self.horizontalLayout_5.addWidget(self.label_2)
        self.inputIP = QtWidgets.QLineEdit(self.centralwidget)
        self.inputIP.setMaxLength(15)
        self.inputIP.setAlignment(QtCore.Qt.AlignCenter)
        self.inputIP.setObjectName("inputIP")
        self.horizontalLayout_5.addWidget(self.inputIP)
        spacerItem9 = QtWidgets.QSpacerItem(37, 17, QtWidgets.QSizePolicy.Expanding, QtWidgets.QSizePolicy.Minimum)
        self.horizontalLayout_5.addItem(spacerItem9)
        self.verticalLayout_4.addLayout(self.horizontalLayout_5)
        self.horizontalLayout_4 = QtWidgets.QHBoxLayout()
        self.horizontalLayout_4.setObjectName("horizontalLayout_4")
        spacerItem10 = QtWidgets.QSpacerItem(40, 20, QtWidgets.QSizePolicy.Expanding, QtWidgets.QSizePolicy.Minimum)
        self.horizontalLayout_4.addItem(spacerItem10)
        self.horizontalLayout_3 = QtWidgets.QHBoxLayout()
        self.horizontalLayout_3.setObjectName("horizontalLayout_3")
        self.label = QtWidgets.QLabel(self.centralwidget)
        self.label.setObjectName("label")
        self.horizontalLayout_3.addWidget(self.label)
        self.sendPort = QtWidgets.QSpinBox(self.centralwidget)
        self.sendPort.setMaximum(65535)
        self.sendPort.setProperty("value", 51234)
        self.sendPort.setObjectName("sendPort")
        self.horizontalLayout_3.addWidget(self.sendPort)
        self.horizontalLayout_4.addLayout(self.horizontalLayout_3)
        spacerItem11 = QtWidgets.QSpacerItem(40, 20, QtWidgets.QSizePolicy.Expanding, QtWidgets.QSizePolicy.Minimum)
        self.horizontalLayout_4.addItem(spacerItem11)
        self.verticalLayout_4.addLayout(self.horizontalLayout_4)
        self.verticalLayout_5.addLayout(self.verticalLayout_4)
        self.gridLayout.addLayout(self.verticalLayout_5, 0, 1, 1, 1)
        self.recvSwitch = QtWidgets.QCheckBox(self.centralwidget)
        self.recvSwitch.setObjectName("recvSwitch")
        self.gridLayout.addWidget(self.recvSwitch, 2, 2, 1, 1)
        MainWindow.setCentralWidget(self.centralwidget)
        self.menubar = QtWidgets.QMenuBar(MainWindow)
        self.menubar.setGeometry(QtCore.QRect(0, 0, 888, 23))
        self.menubar.setObjectName("menubar")
        MainWindow.setMenuBar(self.menubar)
        self.statusbar = QtWidgets.QStatusBar(MainWindow)
        self.statusbar.setObjectName("statusbar")
        MainWindow.setStatusBar(self.statusbar)

        self.cpuLabel = QtWidgets.QLabel(self.centralwidget)
        self.cpuLabel.setObjectName("cpuLabel")
        self.cpuLabel.setAlignment(QtCore.Qt.AlignCenter)
        self.gridLayout.addWidget(self.cpuLabel, 3, 0, 1, 10)

        self.ramLabel = QtWidgets.QLabel(self.centralwidget)
        self.ramLabel.setObjectName("ramLabel")
        self.ramLabel.setAlignment(QtCore.Qt.AlignCenter)
        self.gridLayout.addWidget(self.ramLabel, 4, 0, 1, 10)

        self.sendSwitch = QtWidgets.QCheckBox(self.centralwidget)
        self.sendSwitch.setObjectName("recvSwitch")
        self.gridLayout.addWidget(self.sendSwitch, 2, 1, 1, 1)

        self.retranslateUi(MainWindow)
        QtCore.QMetaObject.connectSlotsByName(MainWindow)

    def retranslateUi(self, MainWindow):
        _translate = QtCore.QCoreApplication.translate
        MainWindow.setWindowTitle(_translate("MainWindow", "MainWindow"))
        self.sendButton.setText(_translate("MainWindow", "Send"))
        self.label_3.setText(_translate("MainWindow", "Receiver Port"))
        self.recieve.setText(_translate("MainWindow", "receive"))
        self.label_2.setText(_translate("MainWindow", "IP:"))
        self.inputIP.setInputMask(_translate("MainWindow", "000.000.000.000"))
        self.inputIP.setText(_translate("MainWindow", "127.0.0.1"))
        self.label.setText(_translate("MainWindow", "Sender Port"))
        self.recvSwitch.setText(_translate("MainWindow", "CheckBox"))
        self.cpuLabel.setText(_translate("MainWindow", "CPU Usage: "))
        self.ramLabel.setText(_translate("MainWindow", "RAM Usage: "))
        self.sendSwitch.setText(_translate("MainWindow", "SendInfo"))
