# Compiler and flags
CXX = g++
CXXFLAGS = -std=c++17 -Wall -I. -IRoles

# SFML libraries
LIBS = -lsfml-graphics -lsfml-window -lsfml-system

# Directories
OBJ_DIR = obj
BIN_DIR = bin

# Source files
ROLE_SRCS = $(wildcard Roles/*.cpp)
SRCS = main.cpp Game.cpp Player.cpp screens.cpp $(ROLE_SRCS)
OBJS = $(patsubst %.cpp,$(OBJ_DIR)/%.o,$(SRCS))

# Main executable
TARGET = $(BIN_DIR)/CoupGame

# Test files
TEST_SRCS = tests.cpp Player.cpp $(ROLE_SRCS)
TEST_OBJS = $(patsubst %.cpp,$(OBJ_DIR)/%.o,$(TEST_SRCS))
TEST_EXEC = $(BIN_DIR)/tests

# Default build
all: $(TARGET)

# Linking for main game
$(TARGET): $(OBJS)
	@mkdir -p $(BIN_DIR)
	$(CXX) $(CXXFLAGS) $^ -o $@ $(LIBS)

# Linking for tests
$(TEST_EXEC): $(TEST_OBJS)
	@mkdir -p $(BIN_DIR)
	$(CXX) $(CXXFLAGS) $^ -o $@

# Build object files
$(OBJ_DIR)/%.o: %.cpp
	@mkdir -p $(dir $@)
	$(CXX) $(CXXFLAGS) -c $< -o $@

# Run main game with Valgrind
valgrind:
	ulimit -n 1024 && valgrind --leak-check=full --track-origins=yes --show-leak-kinds=all $(TARGET)

# Run unit tests
test: $(TEST_EXEC)
	./$(TEST_EXEC)

# Clean all build artifacts
clean:
	rm -rf $(OBJ_DIR) $(BIN_DIR)

.PHONY: all clean test valgrind
