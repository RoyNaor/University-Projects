// roynaor10@gmail.com

#ifndef COUP_SPY_HPP
#define COUP_SPY_HPP

#include "../Role.hpp"

class Spy : public Role {
public:
    std::string getName() const override;
    std::string specialAbility(Player& self, Player& player) override;
    bool needsTargetForSpecial() const override;
    bool endsTurnOnSpecial() const override { return false; }
};

#endif // COUP_SPY_HPP
