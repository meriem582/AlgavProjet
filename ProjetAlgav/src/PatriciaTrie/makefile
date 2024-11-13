# Compilateur et options de compilation
JC = javac

# Extensions de fichier pour chaque règle
.SUFFIXES: .java .class

# Règle pour compiler un fichier Java en fichier .class
.java.class:
	$(JC) $*.java

# Liste des fichiers source Java
CLASSES = \
	*.java

# Cible par défaut
default: classes

# Règle pour compiler tous les fichiers
classes: $(CLASSES:.java=.class)

# Règle pour nettoyer les fichiers compilés
clean:
	rm -f *.class

.PHONY: default classes clean