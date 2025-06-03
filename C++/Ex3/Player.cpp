// roynaor10@gmail.com

#include "Player.hpp"
#include <stdexcept>

// Constructor
Player::Player(const std::string& name, Role* role)
        : name(name),
        coins(0),
        role(role),
        active(true),
        sanctioned(false),
        arrestBlocked(false),
        lastAction(LastAction::None) {}

// Getters
const std::string& Player::getName() const {
    return name;
}

int Player::getCoins() const {
    return coins;
}

bool Player::isActive() const {
    return active;
}

bool Player::isSanctioned() const {
    return sanctioned;
}

void Player::setSanctioned(bool value) {
    sanctioned = value;
}

void Player::setRole(Role* newRole) {
    role = newRole;
}

std::string Player::getRoleName() const {
    return role->getName();
}

Role* Player::getRole() const {
    return role;
}

// Coin management
void Player::addCoins(int amount) {
    if (amount < 0) {
        throw std::invalid_argument("Cannot add negative coins");
    }
    coins += amount;
}

void Player::removeCoins(int amount) {
    if (amount < 0 || amount > coins) {
        throw std::invalid_argument("Invalid coin removal");
    }
    coins -= amount;
}

// Deactivate player
void Player::deactivate() {
    active = false;
}

void Player::reactivate() {
    active = true;
}


// Game actions
void Player::gather() {
    if (sanctioned) {
        throw std::runtime_error("Cannot gather while under sanction");
    }
    addCoins(1);
    lastAction = LastAction::gather;
}

void Player::tax() {
    if (sanctioned) {
        throw std::runtime_error("Cannot tax while under sanction");
    }
    addCoins(role->taxAmount());
    lastAction = LastAction::tax;
}

void Player::bribe() {
    if (coins < 4) {
        throw std::runtime_error("Not enough coins for bribe");
    }
    removeCoins(4);
    lastAction = LastAction::bribe;
}

void Player::arrest(Player& target) {
    if (&target == this) {
        throw std::runtime_error("Cannot arrest yourself");
    }

    if (arrestBlocked) {
        throw std::runtime_error("You are blocked from using arrest this turn");
    }

    if (target.getCoins() < 1) {
        throw std::runtime_error("Target has no coins to take");
    }

    if (target.getRole()->getName() == "General") {
        target.addCoins(1);
    }

    if (target.getRole()->getName() == "Merchant") {
        if (target.getCoins() < 2) {
            throw std::runtime_error("Target has no coins to take");
        }

        target.removeCoins(2);
        lastAction = LastAction::arrest;
        return;
    }

    target.removeCoins(1);
    addCoins(1);
    lastAction = LastAction::arrest;
}

void Player::sanction(Player& target) {
    if (coins < 3) {
        throw std::runtime_error("Not enough coins for sanction");
    }
    if (target.getRole()->getName() == "Baron") {
        target.addCoins(1);
    }
    if (target.getRole()->getName() == "Judge") {
        if (getCoins() == 3){
            throw std::runtime_error("Not enough coins he is a Judge! ");
        }
        else {
            removeCoins(1);
        }
    }
    removeCoins(3);
    target.setSanctioned(true);
    lastAction = LastAction::sanction;
}

void Player::coup(Player& target) {
    if (coins < 7) {
        throw std::runtime_error("Not enough coins for coup");
    }
    removeCoins(7);
    target.deactivate();
    lastAction = LastAction::coup;
    lastCoupTarget = &target;
}

void Player::pass() {
    lastAction = LastAction::pass;
}

