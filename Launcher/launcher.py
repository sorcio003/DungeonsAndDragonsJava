import subprocess

# Esegui il .jar senza aprire la finestra del terminale
subprocess.Popen(['java', '-jar', '../target/dnd-1.0-SNAPSHOT.jar'], creationflags=subprocess.CREATE_NO_WINDOW)
