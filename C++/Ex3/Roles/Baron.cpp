// roynaor10@gmail.com

#include <iostream>
#include "Baron.hpp"
#include "../Player.hpp"

std::string Baron::getName() const {
    return "Baron";
}

bool Baron::needsTargetForSpecial() const {
    return false;
}

std::string Baron::specialAbility(Player& self) {
    if (self.getCoins() < 3) {
        std::string msg = self.getName() + " does not have enough coins to invest (needs 3).";
        return msg;
    }

    self.removeCoins(3);
    self.addCoins(6);
    std::string msg = self.getName() + " invested 3 coins and received 6 coins!";

    return msg;
}

// Special ability - Invest 3 and get 6
std::string Baron::specialAbility(Player& self, Player& player) {
    if (self.getCoins() < 3) {
        std::string msg = " does not have enough coins to invest (needs 3).";
        return msg;
    }

    self.removeCoins(3);
    self.addCoins(6);
    std::string msg = " invested 3 coins and received 6 coins!";

    return msg;
}

