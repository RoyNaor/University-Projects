// roynaor10@gmail.com

#include <iostream>
#include <string>
#include "Merchant.hpp"
#include "../Player.hpp"

std::string Merchant::getName() const {
    return "Merchant";
}

bool Merchant::needsTargetForSpecial() const {
    return false;
}

// Special ability - Get free Coin if he has more than 3 coins
std::string Merchant::specialAbility(Player& self) {
    std::string msg =  "Got 1 free coin ";
    self.addCoins(1);
    return msg;
}
