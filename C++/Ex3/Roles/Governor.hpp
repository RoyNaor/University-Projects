// roynaor10@gmail.com

#ifndef COUP_GOVERNOR_HPP
#define COUP_GOVERNOR_HPP

#include "../Role.hpp"
#include <string>

class Governor : public Role {
public:
    std::string getName() const override;
    int taxAmount() const override;
    std::string specialAbility(Player& self, Player& target) override;
    bool needsTargetForSpecial() const override;
    bool endsTurnOnSpecial() const override { return false; }
};

#endif // COUP_GOVERNOR_HPP
