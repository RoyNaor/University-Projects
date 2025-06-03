// roynaor10@gmail.com

#include <iostream>
#include "Governor.hpp"
#include <string>
#include "../Player.hpp"

std::string Governor::getName() const {
    return "Governor";
}

int Governor::taxAmount() const {
    return 3;
}

bool Governor::needsTargetForSpecial() const {
    return true;
}


// Special ability - Undo Tax for other player who chose it
std::string Governor::specialAbility(Player& self, Player& player) {
    if (player.getLastAction() == LastAction::tax) {
        player.removeCoins(player.getRole()->taxAmount());
        player.clearLastAction();
        std::string msg = " undo " + player.getName() + "'s tax action.";
        std::cout << msg << std::endl;
        return msg;
    } else {
        std::string msg = "Governor could not block: " + player.getName() + "'s last action was not tax.";
        std::cout << msg << std::endl;
        return msg;
    }
}
