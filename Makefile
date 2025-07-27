JAVAC = javac
JAVADOC = javadoc
JAVA = java
SRC_DIR = src
BIN_DIR = bin
RESOURCES_DIR = resources
SRC_FILES = $(wildcard $(SRC_DIR)/*.java)
CLASS_FILES = $(patsubst $(SRC_DIR)/%.java, $(BIN_DIR)/%.class, $(SRC_FILES))
MAIN_CLASS = KnowledgeBaseGUI

# Default main class (can be overridden)
MAIN_CLASS ?= KnowledgeBaseGUI

# Default target
all: compile generate-doc

generate-doc:
	$(JAVADOC) -d doc -sourcepath $(SRC_DIR) -subpackages .

# Compile Java source files into the bin directory
compile:
	mkdir -p $(BIN_DIR)
	$(JAVAC) -d $(BIN_DIR) -cp $(BIN_DIR) $(SRC_FILES)

# Run the main Java program
run: compile
	$(JAVA) -cp $(BIN_DIR):$(RESOURCEZ_DIR) $(MAIN_CLASS)

# Clean up compiled files
clean:
	rm -rf $(BIN_DIR)/*.class
