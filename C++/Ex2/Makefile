CXX = g++
CXXFLAGS = -std=c++20 -Wall -Wextra -g

SRC = SquareMat.cpp
DEMO = main.cpp
TEST = test_SquareMat.cpp
OBJS = $(SRC:.cpp=.o)

MAIN_EXEC = main
TEST_EXEC = test_SquareMat

all: $(MAIN_EXEC)

$(MAIN_EXEC): $(SRC) $(DEMO)
	$(CXX) $(CXXFLAGS) -o $(MAIN_EXEC) $(SRC) $(DEMO)

$(TEST_EXEC): $(SRC) $(TEST)
	$(CXX) $(CXXFLAGS) -o $(TEST_EXEC) $(SRC) $(TEST)

.PHONY: Main test valgrind clean

Main: $(MAIN_EXEC)
	./$(MAIN_EXEC)

test: $(TEST_EXEC)
	./$(TEST_EXEC)

valgrind: $(MAIN_EXEC)
	valgrind --leak-check=full ./$(MAIN_EXEC)

clean:
	rm -f *.o $(MAIN_EXEC) $(TEST_EXEC)
