import psutil

def get_cpu_usage():
    return psutil.cpu_percent(interval=0.1)

def get_ram_usage():
    ram = psutil.virtual_memory()
    return ram.percent
