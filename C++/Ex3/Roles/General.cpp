// roynaor10@gmail.com

#include <iostream>
#include "General.hpp"
#include <string>
#include "../Player.hpp"

std::string General::getName() const {
    return "General";
}

bool General::needsTargetForSpecial() const {
    return true;
}


// Special ability - Undo coup also on himself
std::string General::specialAbility(Player& self, Player& player) {
    if (player.getLastAction() == LastAction::coup && player.getLastCoupTarget() != nullptr) {
        player.getLastCoupTarget()->reactivate();
        std::string savedName = player.getLastCoupTarget()->getName();
        player.clearLastAction();
        player.setLastCoupTarget(nullptr);

        return " blocked " + player.getName() + "'s coup. " + savedName + " is saved!";
    } else {
        return "General could not block: " + player.getName() + "'s last action was not coup.";
    }
}

