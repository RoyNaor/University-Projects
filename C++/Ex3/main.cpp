// roynaor10@gmail.com

#include "screens.hpp"
#include "Game.hpp"

int main() {
    Game game;

    if (!runOpeningScreen()) {
        game.cleanup();
        return 0;
    }
    if (!runAddPlayersScreen(game)) {
        game.cleanup();
        return 0;
    }
    if (!runAssignRolesScreen(game)) {
        game.cleanup();
        return 0;
    }

    runGameLoopScreen(game);

    game.cleanup();
    return 0;
}
