// roynaor10@gmail.com

#ifndef COUP_GAME_HPP
#define COUP_GAME_HPP

#include <vector>
#include <string>
#include "Player.hpp"
#include <SFML/Graphics.hpp>

class Game {
private:
    std::vector<Player*> players;
    int currentTurn;

public:
    Game();

    void addPlayer(Player* player);
    Player* currentPlayer() const;
    void nextTurn();
    Player* findPlayerByName(const std::string& name);
    void removePlayer(const std::string& name);
    std::vector<Player*>& getPlayers() { return players; }
    std::string winner() const;
    std::vector<std::string> activePlayers() const;
    std::string handleAction(int actionType, const std::string& selectedTargetName = "");
    bool simplePopupYesNo(const std::string& question);
    Player* findActiveJudge();
    Player* findActiveGovernor();
    void cleanup();

};

#endif // COUP_GAME_HPP
