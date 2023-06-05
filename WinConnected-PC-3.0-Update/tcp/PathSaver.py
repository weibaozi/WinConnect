import tkinter as tk
from tkinter import filedialog
import os.path
import os

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
    file_path = filedialog.askopenfilename(filetypes=[("Executable files", "*.exe")])

    if get_numPath() >= 9:
        print("Error: Cannot add more applications. Maximum number of applications (9) has been reached.")
        return
    
    if file_path:
        try:
            with open("file_paths.txt", "a") as f:
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
