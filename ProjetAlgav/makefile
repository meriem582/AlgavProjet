SRC_DIR=src
OUT_DIR=Classes
LIB_DIR=libs
CLASSPATH=$(SRC_DIR):$(LIB_DIR)/*

JFLAGS=-d $(OUT_DIR) -cp $(CLASSPATH)
JC=javac

.PHONY: all clean

all:
	mkdir -p $(OUT_DIR)
	$(JC) $(JFLAGS) $(SRC_DIR)/**/*.java

clean:
	rm -rf $(OUT_DIR)
