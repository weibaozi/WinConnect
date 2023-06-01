
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
import time

def volume_to_db(volume_level):
    # Map the volume level to the dB range (rough approximation)
    if volume_level == 0:
        db_value = -75
    else:
        db_value = 35 * math.log10(volume_level / 100)
    return db_value 


def get_shortcut_path():
    app_menu = os.path.join(os.environ["APPDATA"], "Microsoft", "Windows", "Start Menu", "Programs")
    menu = r"C:\ProgramData\Microsoft\Windows\Start Menu\Programs"
    paths = [app_menu, menu]
    folders = []
    for path in paths:
        folder_pattern = os.path.join(path, "**")
        folders += [f for f in glob.glob(folder_pattern, recursive=True) if os.path.isdir(f)]
        folders.append(path)

    shortcut_pattern = os.path.join("{}*", "*.lnk")
    shortcut_files = []
    for folder in folders:
        shortcut_files += glob.glob(shortcut_pattern.format(folder), recursive=True)

    # Create a dictionary to store the shortcuts
    shortcuts_dict = {}

    # Process each shortcut file
    for shortcut_file in shortcut_files:
        filename = os.path.splitext(os.path.basename(shortcut_file))[0]
        shortcuts_dict[filename] = shortcut_file
    
    # Adding some edge cases
    shortcuts_dict["calc"] = r"C:\Windows\System32\calc.exe"

    # Print the dictionary
    # for key, value in shortcuts_dict.items():
    #     print(f"{key}: {value}")
    
    return shortcuts_dict
    

allshortcuts = get_shortcut_path()
# print(allshortcuts["calc"])

# Get default audio device using PyCAW (For volume controls)
devices = AudioUtilities.GetSpeakers()
interface = devices.Activate(IAudioEndpointVolume._iid_, CLSCTX_ALL, None)
volume = cast(interface, POINTER(IAudioEndpointVolume))

def decode_function(text):
    words = text.split(' ', 1)
    if words[0] == "startapp":
        if words[1] == "soundcloud":
            subprocess.Popen(["C:\Program Files\Google\Chrome\Application\chrome.exe", "https://soundcloud.com/you/likes"])
        else:
            #print(words[1])
            subprocess.Popen(allshortcuts[words[1]], shell=True)

        pass
    
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

    else:
        print("Command not found")

# command = input("Enter command: ")
# decode_function(command)