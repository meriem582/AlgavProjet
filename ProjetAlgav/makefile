# Makefile pour un projet Java
SRC_DIR=src
OUT_DIR=Classes
JFLAGS=-d $(OUT_DIR) -cp $(SRC_DIR)
JC=javac

.PHONY: all clean

all:
	mkdir -p $(OUT_DIR)
	$(JC) $(JFLAGS) $(SRC_DIR)/**/*.java

clean:
	rm -rf $(OUT_DIR)
