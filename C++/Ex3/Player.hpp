// roynaor10@gmail.com

#ifndef COUP_PLAYER_HPP
#define COUP_PLAYER_HPP

#include <string>
#include "Role.hpp"

enum class LastAction {
    None,
    gather,
    tax,
    bribe,
    arrest,
    sanction,
    coup,
    pass,
};


class Player {
private:
    std::string name;
    int coins;
    Role* role;
    bool active;
    bool sanctioned;
    bool arrestBlocked;
    LastAction lastAction;
    Player* lastCoupTarget = nullptr;

public:
    Player(const std::string& name, Role* role);

    const std::string& getName() const;
    void setRole(Role* newRole);
    int getCoins() const;
    bool isActive() const;
    std::string getRoleName() const;
    Role* getRole() const;
    bool isSanctioned() const;
    void setSanctioned(bool value);
    LastAction getLastAction() const { return lastAction; }
    void clearLastAction() { lastAction = LastAction::None; }
    bool isArrestBlocked() const { return arrestBlocked; }
    void setArrestBlocked(bool value) { arrestBlocked = value; }
    void clearArrestBlocked() { arrestBlocked = false; }
    Player* getLastCoupTarget() const { return lastCoupTarget; }
    void setLastCoupTarget(Player* target) { lastCoupTarget = target; }



    void addCoins(int amount);
    void removeCoins(int amount);
    void deactivate();
    void reactivate();

    // Game actions:
    void gather();
    void tax();
    void bribe();
    void arrest(Player& target);
    void sanction(Player& target);
    void coup(Player& target);
    void pass();
};

#endif // COUP_PLAYER_HPP
