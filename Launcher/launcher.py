import subprocess

# Esegui il .jar senza aprire la finestra del terminale
subprocess.Popen(['mvn', 'javafx:run'], creationflags=subprocess.CREATE_NO_WINDOW)
