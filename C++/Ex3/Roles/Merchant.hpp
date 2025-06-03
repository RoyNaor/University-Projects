// roynaor10@gmail.com

#ifndef COUP_MERCHANT_HPP
#define COUP_MERCHANT_HPP


#include "../Role.hpp"

class Merchant : public Role {
public:
    std::string getName() const override;
    std::string specialAbility(Player& self) override;
    bool needsTargetForSpecial() const override;
    bool endsTurnOnSpecial() const override { return false; }
};



#endif //COUP_MERCHANT_HPP
