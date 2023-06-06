import tkinter as tk
from tkinter import filedialog

import os.path
import os, glob

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
    shortcuts_dict=dict(sorted(shortcuts_dict.items()))
    return shortcuts_dict

# return number of path in the file_path.txt
def get_numPath():
    if not os.path.exists("file_paths.txt"):
        return 0

    with open("file_paths.txt", "r") as f:
        lines = f.readlines()

    return sum(1 for line in lines if line.strip())

# return a list of path of User selected app 
def get_pathList():
    if not os.path.exists("file_paths.txt"):
        return []

    with open("file_paths.txt", "r") as f:
        lines = f.readlines()

    if not lines:
        return []

    return [line.strip() for line in lines if line.strip()]

# return a list of name of the Apps that user selected
def get_fileNameList():
    if get_numPath() == 0:
        return []

    path_list = get_pathList()
    return [os.path.basename(path) for path in path_list]


def set_filePath():
    root = tk.Tk()
    root.withdraw()
    #file_path = filedialog.askopenfilename(filetypes=[("Executable files", "*.exe")])
    file_paths = filedialog.askopenfilenames()
    
    if file_paths:
        try:
            with open("file_paths.txt", "a") as f:
                for file_path in file_paths:
                    f.write(file_path + os.linesep)
        except IOError as e:
            print("Error: Failed to open or write to file.")
            print(e)


def get_path(index):
    pathList = get_pathList()
    return [pathList[index - 1]]

def delete_path(path_name):
    if not os.path.exists("file_paths.txt"):
        raise ("No application has been selected")
        
    with open("file_paths.txt", "r") as f:
        lines = f.readlines()
    
    path_deleted = False
    with open("file_paths.txt", "w") as f:
        for line in lines:
            if line.strip("\n") == path_name and not path_deleted:
                path_deleted = True
            else:
                f.write(line)


def delete_all_paths():
    if not os.path.exists("file_paths.txt"):
        raise Exception("No application has been selected")
    
    with open("file_paths.txt", "w") as f:
        pass




'''
if __name__ == "__main__":
    
    file_path = select_file()
    if file_path:
        store_file_path(file_path)
    else:
        print("No file selected")
    
    #delete_path("C:/Sean/UCSC/CSE/CSE115A/Windows/test/tcp/Win_UI/TCP.ui")
    
 '''  
