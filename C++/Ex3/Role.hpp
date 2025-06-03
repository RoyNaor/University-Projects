// roynaor10@gmail.com

#ifndef COUP_ROLE_HPP
#define COUP_ROLE_HPP

#include <string>

class Player;

class Role {
public:
    virtual ~Role() = default;

    virtual std::string getName() const = 0;

    virtual int taxAmount() const { return 2; }
    virtual std::string specialAbility(Player& self) { return ""; }
    virtual std::string specialAbility(Player& self, Player& target) { return ""; }
    virtual bool canBlockCoup() const { return false; }
    virtual bool needsTargetForSpecial() const { return false; }
    virtual bool endsTurnOnSpecial() const { return true; }
};

#endif //COUP_ROLE_HPP
