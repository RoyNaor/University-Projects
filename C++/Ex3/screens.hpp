// roynaor10@gmail.com

#ifndef COUP_SCREENS_HPP
#define COUP_SCREENS_HPP

#include <string>

class Game;

bool runOpeningScreen();
bool runAddPlayersScreen(Game& game);
bool runAssignRolesScreen(Game& game);
void runGameLoopScreen(Game& game);
void runWinnerScreen(const std::string& name);



#endif // COUP_SCREENS_HPP
