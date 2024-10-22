# Utiliser l'image de base jupyter/scipy-notebook
FROM jupyter/scipy-notebook:latest

# Passer à l'utilisateur root pour installer des paquets supplémentaires
USER root

# Installer PySpark
RUN pip install pyspark

# Changer l'utilisateur pour jovyan (utilisateur par défaut de l'image Jupyter)
USER $NB_UID

# Exposer le port 8888 pour Jupyter Notebook
EXPOSE 8888

# Démarrer Jupyter Notebook avec les options par défaut
CMD ["start-notebook.sh", "--NotebookApp.token=''"]
