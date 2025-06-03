// roynaor10@gmail.com

#ifndef COUP_GENERAL_HPP
#define COUP_GENERAL_HPP

#include "../Role.hpp"
#include <string>

class General : public Role {
public:
    std::string getName() const override;
    std::string specialAbility(Player& self, Player& target) override;
    bool needsTargetForSpecial() const override;
    bool endsTurnOnSpecial() const override { return true; }
};


#endif //COUP_GENERAL_HPP
