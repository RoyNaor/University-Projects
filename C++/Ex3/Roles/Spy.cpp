// roynaor10@gmail.com

#include <iostream>
#include <string>
#include "Spy.hpp"
#include "../Player.hpp"

std::string Spy::getName() const {
    return "Spy";
}

bool Spy::needsTargetForSpecial() const {
    return true;
}

// Special ability - See the amount of money of a player and block his Arrest
std::string Spy::specialAbility(Player& self, Player& player) {
    std::string msg =  player.getName() + " has " + std::to_string(player.getCoins()) + " coins and arrest blocked";
    player.setArrestBlocked(true);
    std::cout << player.getName() << "blocked from using Arrest in his next turn!" << "\n";
    return msg;
}