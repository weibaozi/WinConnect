
import subprocess
import time
import pyautogui

def decode_function(text):
    words = text.split()
    if words[0] == "startapp":
        app = "start " + words[1]

        # Run the command and capture the output
        output = subprocess.check_output(app, shell=True)

        # Decode the output from bytes to string
        output = output.decode("utf-8")

        # Print the output
        print(output)
        pass
    elif words[0] == "media":
        if words[1] == "play":
            pyautogui.press("playpause")
        elif words[1] == "next":
            pyautogui.press("nexttrack")
        elif words[1] == "prev":
            pyautogui.press("prevtrack")
    else:
        print("Command not found")

# command = input("Enter command: ")
# decode_function(command)