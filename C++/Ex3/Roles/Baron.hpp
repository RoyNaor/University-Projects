// roynaor10@gmail.com

#ifndef COUP_BARON_HPP
#define COUP_BARON_HPP

#include "../Role.hpp"

class Baron : public Role {
public:
    std::string getName() const override;
    std::string specialAbility(Player& self, Player& target) override;
    std::string specialAbility(Player& self) override;
    bool needsTargetForSpecial() const override;
    bool endsTurnOnSpecial() const override { return true; }
};

#endif // COUP_BARON_HPP
