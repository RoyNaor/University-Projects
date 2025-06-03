// roynaor10@gmail.com

#include "Game.hpp"
#include <iostream>
#include <stdexcept>
#include <SFML/Graphics.hpp>

std::string lastArrestedName;

// Constructor
Game::Game()
        : currentTurn(0) {}

// Add a player to the game
void Game::addPlayer(Player* player) {
    players.push_back(player);
}

void Game::cleanup() {
    for (Player* player : players) {
        delete player->getRole();
        delete player;
    }
    players.clear();
}


// Get the current player
Player* Game::currentPlayer() const {
    if (players.empty()) {
        throw std::runtime_error("No players in the game");
    }
    return players[currentTurn];
}

Player* Game::findPlayerByName(const std::string& name) {
    for (auto* p : players) {
        if (p->getName() == name && p->isActive()) {
            return p;
        }
    }
    return nullptr;
}

std::string popupSelectTarget2(sf::RenderWindow& parentWindow, const std::vector<Player*>& players, sf::Font& font) {
    sf::RenderWindow popup(sf::VideoMode(600, 400), "Select Target for coup", sf::Style::Titlebar | sf::Style::Close);

    sf::Text title("You have to coup!", font, 36);
    title.setFillColor(sf::Color::White);
    title.setPosition(180, 20);

    while (popup.isOpen()) {
        sf::Event event;
        while (popup.pollEvent(event)) {
            if (event.type == sf::Event::Closed) {
                popup.close();
                return "";
            }

            if (event.type == sf::Event::MouseButtonPressed) {
                sf::Vector2i mousePos = sf::Mouse::getPosition(popup);
                for (size_t i = 0; i < players.size(); ++i) {
                    sf::FloatRect box(100, 80 + i * 50, 400, 40);
                    if (box.contains(static_cast<float>(mousePos.x), static_cast<float>(mousePos.y))) {
                        popup.close();
                        return players[i]->getName();
                    }
                }
            }
        }

        popup.clear(sf::Color(50, 50, 50));
        popup.draw(title);

        for (size_t i = 0; i < players.size(); ++i) {
            sf::RectangleShape nameBox(sf::Vector2f(400, 40));
            nameBox.setFillColor(sf::Color(100, 100, 200));
            nameBox.setPosition(100, 80 + i * 50);
            popup.draw(nameBox);

            sf::Text nameText(players[i]->getName(), font, 28);
            nameText.setFillColor(sf::Color::White);
            nameText.setPosition(120, 85 + i * 50);
            popup.draw(nameText);
        }

        popup.display();
    }

    return "";
}

// Advance to the next turn
void Game::nextTurn() {
    if (players.empty()) {
        throw std::runtime_error("No players in the game");
    }

    Player* justPlayed = players[currentTurn];
    justPlayed->setArrestBlocked(false);
    justPlayed->setSanctioned(false);

    do {
        currentTurn = (currentTurn + 1) % players.size();
    } while (!players[currentTurn]->isActive());

    Player* current = players[currentTurn];
    if (current->getRole()->getName() == "Merchant" && current->getCoins() >= 3) {
        std::string msg = current->getRole()->specialAbility(*current);
        std::cout << current->getName() << " " << msg << std::endl;
    }

    // Forced Coup if 10 or more coins
    if (current->getCoins() >= 10) {
        sf::RenderWindow dummyWindow(sf::VideoMode(1, 1), "");
        sf::Font font;
        font.loadFromFile("assets/OpenSans_Regular.ttf");

        std::string targetName = popupSelectTarget2(dummyWindow, players, font);
        Player* target = findPlayerByName(targetName);

        if (target && target != current && target->isActive()) {
            std::cout << current->getName() << " Couped " << target->getName() << "!\n";
            current->coup(*target);
        }

        nextTurn();
        return;
    }


}

// Remove a player by name (mark as inactive)
void Game::removePlayer(const std::string& name) {
    for (Player* player : players) {
        if (player->getName() == name) {
            player->deactivate();
            return;
        }
    }
    throw std::runtime_error("Player not found: " + name);
}

Player* Game::findActiveJudge() {
    Player* current = currentPlayer();
    for (Player* p : players) {
        if (p->isActive() && p->getRoleName() == "Judge" && p != current) {
            return p;
        }
    }
    return nullptr;
}

Player* Game::findActiveGovernor() {
    Player* current = currentPlayer();
    for (Player* p : players) {
        if (p->isActive() && p->getRoleName() == "Governor" && p != current) {
            return p;
        }
    }
    return nullptr;
}

// Return the name of the winner (if only one remains)
std::string Game::winner() const {
    std::string winnerName;
    int activeCount = 0;

    for (Player* player : players) {
        if (player->isActive()) {
            winnerName = player->getName();
            activeCount++;
        }
    }

    if (activeCount == 1) {
        return winnerName;
    } else {
        throw std::runtime_error("Game is still ongoing");
    }
}

// Get list of active player names
std::vector<std::string> Game::activePlayers() const {
    std::vector<std::string> names;
    for (Player* player : players) {
        if (player->isActive()) {
            names.push_back(player->getName());
        }
    }
    return names;
}

bool Game::simplePopupYesNo(const std::string& question) {
    sf::RenderWindow popup(sf::VideoMode(560, 200), "Question", sf::Style::Titlebar | sf::Style::Close);

    sf::Font font;
    if (!font.loadFromFile("assets/OpenSans_Regular.ttf")) {
        std::cerr << "Failed to load font\n";
        return false;
    }

    sf::Text questionText(question, font, 28);
    questionText.setFillColor(sf::Color::White);
    questionText.setPosition(50, 50);

    sf::RectangleShape yesButton(sf::Vector2f(100, 50));
    yesButton.setFillColor(sf::Color::Green);
    yesButton.setPosition(70, 120);

    sf::Text yesText("Yes", font, 24);
    yesText.setFillColor(sf::Color::Black);
    yesText.setPosition(95, 135);

    sf::RectangleShape noButton(sf::Vector2f(100, 50));
    noButton.setFillColor(sf::Color::Red);
    noButton.setPosition(230, 120);

    sf::Text noText("No", font, 24);
    noText.setFillColor(sf::Color::Black);
    noText.setPosition(265, 135);

    while (popup.isOpen()) {
        sf::Event event;
        while (popup.pollEvent(event)) {
            if (event.type == sf::Event::Closed) {
                popup.close();
                return false;
            }
            if (event.type == sf::Event::MouseButtonPressed) {
                sf::Vector2i mousePos = sf::Mouse::getPosition(popup);
                if (yesButton.getGlobalBounds().contains(mousePos.x, mousePos.y)) {
                    popup.close();
                    return true;
                }
                if (noButton.getGlobalBounds().contains(mousePos.x, mousePos.y)) {
                    popup.close();
                    return false;
                }
            }
        }

        popup.clear(sf::Color(50, 50, 50));
        popup.draw(questionText);
        popup.draw(yesButton);
        popup.draw(yesText);
        popup.draw(noButton);
        popup.draw(noText);
        popup.display();
    }
    return false;
}

std::string Game::handleAction(int actionType, const std::string& selectedTargetName){
    Player* current = currentPlayer();

    try {
        if (actionType == 1) {
            current->gather();
            return " chose gather.";
        }
        if (actionType == 2) {
            Player* Governor = findActiveGovernor();
            if (Governor) {
                bool judgeCancels = simplePopupYesNo(Governor->getName() + " (Governor): Cancel tax?");
                if (judgeCancels) {
                    return "Chose tax, but Governor cancelled!";
                }
            }

            current->tax();
            return " chose tax.";
        }
        if (actionType == 3) {
            current->bribe();

            Player* judge = findActiveJudge();
            if (judge) {
                bool judgeCancels = simplePopupYesNo(judge->getName() + " (Judge): Cancel extra turn?");
                if (judgeCancels) {
                    return "Chose bribe, but Judge cancelled!";
                }
            }

            return "Chose bribe (extra action granted).";
        }
        if (actionType == 8) {
            return " chose to Pass.";
        }

        if (actionType >= 4 && actionType <= 6) {
            Player* target = findPlayerByName(selectedTargetName);
            if (!target || target == current || !target->isActive()) {
                return "Invalid target selection!";
            }

            if (actionType == 4) {

                if (lastArrestedName == selectedTargetName) {
                    throw std::runtime_error("Cannot arrest twice in a row");
                }

                current->arrest(*target);
                lastArrestedName = target->getName();
                return " arrested " + target->getName();

            }

            else if (actionType == 5) {
                current->sanction(*target);
                return " sanctioned " + target->getName();
            }

            else if (actionType == 6) {
                current->coup(*target);

                if (target->getRoleName() == "General" && !target->isActive()) {
                    bool wantsToSave = simplePopupYesNo("General: Do you want to save yourself?");

                    if (wantsToSave) {
                        if (target->getCoins() >= 5) {
                            target->removeCoins(5);
                            target->reactivate();
                            nextTurn();
                            return target->getName() + " saved himself from coup!";
                        } else {
                            nextTurn();
                            return target->getName() + " tried to save himself but had no coins.";
                        }
                    }
                }
                return " performed a coup on " + target->getName();
            }
        }

        if (actionType == 7) {
            std::string result;
            if (!selectedTargetName.empty()) {
                Player* target = findPlayerByName(selectedTargetName);
                if (!target || !target->isActive()) {
                    return "Invalid target for special ability!";
                }
                result = current->getRole()->specialAbility(*current, *target);
            } else {
                result = current->getRole()->specialAbility(*current);
            }

            return result;
        }
    } catch (const std::exception& e) {
        return std::string("Action failed: ") + e.what();
    }

    return "Unknown action.";
}
