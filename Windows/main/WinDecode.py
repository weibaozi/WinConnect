
import subprocess
import time
import pyautogui
import keyboard
import os
import glob
from ctypes import cast, POINTER
from comtypes import CLSCTX_ALL
from pycaw.pycaw import AudioUtilities, IAudioEndpointVolume
import math
import time,socket
from PathSaver import get_path, get_numPath, get_shortcut_path,get_pathList
import webbrowser
def volume_to_db(volume_level):
    # Map the volume level to the dB range (rough approximation)
    if volume_level == 0:
        db_value = -75
    else:
        db_value = 35 * math.log10(volume_level / 100)
    return db_value 



    
allshortcuts = get_shortcut_path()
keypressed = set()

# print(allshortcuts["calc"])

# Get default audio device using PyCAW (For volume controls)
devices = AudioUtilities.GetSpeakers()
interface = devices.Activate(IAudioEndpointVolume._iid_, CLSCTX_ALL, None)
volume = cast(interface, POINTER(IAudioEndpointVolume))


def openApp(index):
    numPaths = get_numPath()
    if numPaths >= index:
        subprocess.Popen(get_path(index)) 
    else:
        print("Error: App " + str(index) + " path hasn't been defined")


def decode_function(text):
    return_message=None
    words = text.split(' ', 1)
    if words[0] == "startapp":
        if words[1] == "soundcloud":
            subprocess.Popen(["C:\Program Files\Google\Chrome\Application\chrome.exe", "https://soundcloud.com/you/likes"])                                     
        else:
            #print(words[1])
            allshortcuts_new ={}
            app=words[1]
            newfile=get_pathList()
            for file in newfile:
                filename=os.path.basename(file)
                allshortcuts_new[filename]=file
            allshortcuts_new.update(allshortcuts)
            allshortcuts_new=dict(sorted(allshortcuts_new.items()))
            matching_apps = [key for key in allshortcuts_new.keys() if app.lower() in key.lower()]
            if len(matching_apps) >= 1:
                print("find: ", matching_apps)
                subprocess.Popen(allshortcuts_new[matching_apps[0]], shell=True)
                return_message= "starting "+matching_apps[0]
            else:
                return_message="appnotfound"
                
                print("Error: App not found ")
    elif words[0] == "startweb":
        url = words[1]
        webbrowser.open(url)
    elif words[0] == "arrow":
        if words[1] == "up":
            keyboard.press('up')
            keyboard.release('up')
        elif words[1] == "down":
            keyboard.press('down')
            keyboard.release('down')
        elif words[1] == "left":
            keyboard.press('left')
            keyboard.release('left')
        elif words[1] == "right":
            keyboard.press('right')
            keyboard.release('right')

    elif words[0] == "media":
        if words[1] == "play":
            pyautogui.press("playpause")
        elif words[1] == "next":
            pyautogui.press("nexttrack")
        elif words[1] == "prev":
            pyautogui.press("prevtrack")
    elif words[0] == "volume":
        if words[1] == "up":
            pyautogui.press("volumeup")
        elif words[1] == "down":
            pyautogui.press("volumedown")
        elif words[1] == "mute":
            pyautogui.press("volumemute")

    elif words[0] == "setvol":
        print("Setting volume to " + words[1], type(words[1]))
        db_value = volume_to_db(int(words[1]))
        volume.SetMasterVolumeLevel(db_value, None)
    elif words[0] == "shutdown":
        return_message="shutting down"
        os.system("shutdown /s /t 5")
    elif words[0] == "key":
        keys=words[1].split(' ')
        for key in keys:
            keyboard.press(key)
        time.sleep(0.05)
        for key in keys:
            keyboard.release(key)
    elif words[0] == "keypress":
        keys=words[1].split(' ')
        for key in keys:
            keyboard.press(key)
            keypressed.add(key)
    elif words[0] == "keyrelease":
        keys = words[1].split(' ')
        for key in keys:
            keyboard.release(key)
            keypressed.remove(key)
    else:
        print("Command not found")
    return return_message
    #return_message="test!!!!!!!!"

# command = input("Enter command: ")
# decode_function(command)
