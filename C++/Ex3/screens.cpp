// roynaor10@gmail.com

#include <SFML/Graphics.hpp>
#include <iostream>
#include <vector>
#include <string>
#include "Game.hpp"
#include "Player.hpp"
#include <random>
#include "Game.hpp"
#include "Player.hpp"
#include "Roles/Governor.hpp"
#include "Roles/Spy.hpp"
#include "Roles/Baron.hpp"
#include "Roles/General.hpp"
#include "Roles/Judge.hpp"
#include "Roles/Merchant.hpp"

std::string popupSelectTarget(sf::RenderWindow& parentWindow, const std::vector<Player*>& players, sf::Font& font) {
    sf::RenderWindow popup(sf::VideoMode(450, 300), "Select Target", sf::Style::Titlebar | sf::Style::Close);

    sf::Text title("Select a Player", font, 30);
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
                    sf::FloatRect box(100, 80 + i * 50, 300, 40);
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
            sf::RectangleShape nameBox(sf::Vector2f(300, 40));
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
//----------------------------------------------------------------------------------------------------
bool popupYesNo(sf::RenderWindow& parentWindow, sf::Font& font, const std::string& question) {
    sf::RenderWindow popup(sf::VideoMode(450, 200), "Need a target?", sf::Style::Titlebar | sf::Style::Close);

    sf::Text questionText(question, font, 24);
    questionText.setFillColor(sf::Color::White);
    questionText.setPosition(50, 50);

    sf::RectangleShape yesButton(sf::Vector2f(80, 40));
    yesButton.setFillColor(sf::Color::Green);
    yesButton.setPosition(70, 120);

    sf::Text yesText("Yes", font, 20);
    yesText.setFillColor(sf::Color::Black);
    yesText.setPosition(95, 135);

    sf::RectangleShape noButton(sf::Vector2f(80, 40));
    noButton.setFillColor(sf::Color::Red);
    noButton.setPosition(230, 120);

    sf::Text noText("No", font, 20);
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

//----------------------------------------------------------------------------------------------------
void runWinnerScreen(const std::string& winnerName) {
    sf::RenderWindow window(sf::VideoMode(900, 550), "Winner!");

    sf::Font font;
    if (!font.loadFromFile("assets/OpenSans-Bold.ttf")){
        std::cerr << "Failed to load bold font\n";
        return;
    }

    sf::Texture backgroundTexture;
    if (!backgroundTexture.loadFromFile("assets/winner.png")) {
        std::cerr << "Failed to load winner.png\n";
        return;
    }
    sf::Sprite backgroundSprite(backgroundTexture);
    float scaleX = (float)window.getSize().x / backgroundTexture.getSize().x;
    float scaleY = (float)window.getSize().y / backgroundTexture.getSize().y;
    backgroundSprite.setScale(scaleX, scaleY);

    sf::Text winnerText(winnerName, font, 60);
    winnerText.setStyle(sf::Text::Bold);
    winnerText.setFillColor(sf::Color::Black);
    winnerText.setPosition(window.getSize().x / 2 - winnerText.getLocalBounds().width / 2, window.getSize().y / 2 - 50);

    while (window.isOpen()) {
        sf::Event event;
        while (window.pollEvent(event)) {
            if (event.type == sf::Event::Closed || event.type == sf::Event::KeyPressed) {
                window.close();
            }
        }

        window.clear();
        window.draw(backgroundSprite);
        window.draw(winnerText);
        window.display();
    }
}
//-----------------------------------------------------------------------------------------------------
bool runOpeningScreen() {
    sf::RenderWindow window(sf::VideoMode(900, 550), "Coup Game");

    // Load font
    sf::Font font;
    if (!font.loadFromFile("assets/OpenSans-Bold.ttf")) {
        std::cerr << "Failed to load font\n";
        return false;
    }

    // Colors
    sf::Color backgroundColor(205, 133, 63); 
    sf::Color buttonColor(0, 255, 0);        
    sf::Color buttonTextColor = sf::Color::Black;

    // Title: "Coup Game"
    sf::Text title("Coup Game", font, 60);
    title.setFillColor(sf::Color::White);
    title.setPosition((window.getSize().x - title.getLocalBounds().width) / 2, 100);

    // Subtitle: "Ex3 in C++"
    sf::Text subtitle("Ex3 in C++", font, 30);
    subtitle.setFillColor(sf::Color::White);
    subtitle.setPosition((window.getSize().x - subtitle.getLocalBounds().width) / 2, 200);

    // Button
    sf::Vector2f buttonSize(230, 55);
    float buttonX = (window.getSize().x - buttonSize.x) / 2;
    float buttonY = window.getSize().y - 200;
    sf::RectangleShape startButton(buttonSize);
    startButton.setFillColor(buttonColor);
    startButton.setPosition(buttonX, buttonY);

    // Button text
    sf::Text startText("Start", font, 26);
    startText.setFillColor(buttonTextColor);
    startText.setStyle(sf::Text::Bold);
    sf::FloatRect textBounds = startText.getLocalBounds();
    startText.setPosition(
        buttonX + (buttonSize.x - textBounds.width) / 2,
        buttonY + (buttonSize.y - textBounds.height) / 2 - 10
    );

    // Main loop
    while (window.isOpen()) {
        sf::Event event;
        while (window.pollEvent(event)) {
            if (event.type == sf::Event::Closed)
                return false;

            if (event.type == sf::Event::MouseButtonPressed) {
                sf::Vector2i mousePos = sf::Mouse::getPosition(window);
                if (startButton.getGlobalBounds().contains(static_cast<float>(mousePos.x), static_cast<float>(mousePos.y))) {
                    window.close();
                    return true;
                }
            }
        }

        window.clear(backgroundColor);
        window.draw(title);
        window.draw(subtitle);
        window.draw(startButton);
        window.draw(startText);
        window.display();
    }

    return false;
}
//----------------------------------------------------------------------------------------------------------
bool runAddPlayersScreen(Game& game) {
    sf::RenderWindow window(sf::VideoMode(900, 550), "Add Players");

    sf::Font font;
    if (!font.loadFromFile("assets/OpenSans-Bold.ttf")) {
        std::cerr << "Failed to load font\n";
        return false;
    }

    std::vector<std::string> playerNames;
    std::string currentInput;

    sf::Text title("Add Players", font, 48);
    title.setFillColor(sf::Color::White);
    title.setPosition(330, 20);

    sf::Text instruction("(type name + press ENTER)", font, 28);
    instruction.setFillColor(sf::Color::White);
    instruction.setPosition(250, 80);

    sf::Text inputText("", font, 24);
    inputText.setFillColor(sf::Color::Black);
    sf::RectangleShape inputBox(sf::Vector2f(400, 40));
    inputBox.setFillColor(sf::Color::White);
    inputBox.setPosition(250, 130);
    inputText.setPosition(260, 135);

    sf::Text playersLabel("Players:", font, 30);
    playersLabel.setFillColor(sf::Color::Yellow);
    playersLabel.setPosition(250, 190);

    sf::RectangleShape startButton(sf::Vector2f(200, 60));
    startButton.setFillColor(sf::Color(128, 128, 128));
    startButton.setPosition(350, 460);

    sf::Text startText("Start Game", font, 28);
    startText.setFillColor(sf::Color::Black);
    startText.setPosition(375, 470);

    while (window.isOpen()) {
        sf::Event event;
        while (window.pollEvent(event)) {
            if (event.type == sf::Event::Closed) {
                window.close();
                return false;
            }

            if (event.type == sf::Event::TextEntered) {
                if (event.text.unicode == '\b') {
                    if (!currentInput.empty()) currentInput.pop_back();
                } else if (event.text.unicode == '\r') {
                    if (!currentInput.empty()) {
                        bool exists = false;
                        for (const auto& name : playerNames) {
                            if (name == currentInput) {
                                exists = true;
                                break;
                            }
                        }
                        if (!exists) {
                            playerNames.push_back(currentInput);
                        }
                        currentInput.clear();
                    }
                } else if (event.text.unicode < 128) {
                    currentInput += static_cast<char>(event.text.unicode);
                }
            }

            if (event.type == sf::Event::MouseButtonPressed && event.mouseButton.button == sf::Mouse::Left) {
                sf::Vector2i mousePos = sf::Mouse::getPosition(window);
                if (startButton.getGlobalBounds().contains(static_cast<float>(mousePos.x), static_cast<float>(mousePos.y))) {
                    if (playerNames.size() >= 2 && playerNames.size() <= 6) {
                        for (const auto& name : playerNames) {
                            game.addPlayer(new Player(name, nullptr));
                        }
                        window.close();
                        return true;
                    }
                }

                float listStartY = 230;
                for (size_t i = 0; i < playerNames.size(); ++i) {
                    float boxX = 600;
                    float boxY = listStartY + i * 40;
                    sf::FloatRect removeBox(boxX, boxY, 25, 25);
                    if (removeBox.contains(static_cast<float>(mousePos.x), static_cast<float>(mousePos.y))) {
                        playerNames.erase(playerNames.begin() + i);
                        break;
                    }
                }
            }
        }

        inputText.setString(currentInput);
        window.clear(sf::Color(150, 90, 40));
        window.draw(title);
        window.draw(instruction);
        window.draw(inputBox);
        window.draw(inputText);
        window.draw(playersLabel);

        float listStartY = 230;
        for (size_t i = 0; i < playerNames.size(); ++i) {
            sf::Text playerText("- " + playerNames[i], font, 24);
            playerText.setFillColor(sf::Color::White);
            playerText.setPosition(250, listStartY + i * 40);
            window.draw(playerText);

            sf::RectangleShape removeBox(sf::Vector2f(25, 25));
            removeBox.setFillColor(sf::Color::Red);
            removeBox.setPosition(600, listStartY + i * 40);
            window.draw(removeBox);

            sf::Text xText("X", font, 20);
            xText.setFillColor(sf::Color::White);
            xText.setPosition(607, listStartY + i * 40 + 2);
            window.draw(xText);
        }

        startButton.setFillColor((playerNames.size() >= 2 && playerNames.size() <= 6) ? sf::Color(0, 200, 0) : sf::Color(128, 128, 128));
        window.draw(startButton);
        window.draw(startText);
        window.display();
    }

    return false;
}
//-----------------------------------------------------------------------------------------------------------
bool runAssignRolesScreen(Game& game) {
    sf::RenderWindow window(sf::VideoMode(900, 550), "Assign Roles");

    sf::Font font;
    if (!font.loadFromFile("assets/OpenSans-Bold.ttf")) {
        std::cerr << "Failed to load bold font\n";
        return false;
    }

    sf::Texture backgroundTexture;
    if (!backgroundTexture.loadFromFile("assets/Roles.png")) {
        std::cerr << "Failed to load Roles.png\n";
        return false;
    }
    sf::Sprite backgroundSprite(backgroundTexture);
    float scaleX = static_cast<float>(window.getSize().x) / backgroundTexture.getSize().x;
    float scaleY = static_cast<float>(window.getSize().y) / backgroundTexture.getSize().y;
    backgroundSprite.setScale(scaleX, scaleY);

    sf::RectangleShape textBackground(sf::Vector2f(700, 400));
    textBackground.setFillColor(sf::Color(50, 50, 50, 150));
    textBackground.setPosition(100, 30);

    sf::Text title("Assigning Roles", font, 40);
    title.setFillColor(sf::Color::White);
    title.setPosition(270, 40);

    sf::Text message("", font, 24);
    message.setFillColor(sf::Color::Yellow);
    message.setPosition(120, 100);

    sf::Text roleMessage("", font, 24);
    roleMessage.setFillColor(sf::Color::Yellow);
    roleMessage.setPosition(120, 140);

    sf::Text playersLabel("Players:", font, 28);
    playersLabel.setFillColor(sf::Color::White);
    playersLabel.setPosition(120, 190);

    std::vector<Player*>& players = game.getPlayers();
    size_t currentPlayerIndex = 0;

    std::random_device rd;
    std::mt19937 gen(rd());

    std::vector<Role*> roles{
        new Governor(),
        new Spy(),
        new Baron(),
        new General(),
        new Judge(),
        new Merchant()
    };

    while (window.isOpen()) {
        sf::Event event;
        while (window.pollEvent(event)) {
            if (event.type == sf::Event::Closed)
                window.close();

            if (event.type == sf::Event::KeyPressed && event.key.code == sf::Keyboard::Enter) {
                if (currentPlayerIndex < players.size()) {
                    std::uniform_int_distribution<> dis(0, roles.size() - 1);
                    Role* randomRole = roles[dis(gen)];
                    players[currentPlayerIndex]->setRole(randomRole);

                    roleMessage.setString(players[currentPlayerIndex]->getName() + ", you are a: " + randomRole->getName());
                    currentPlayerIndex++;
                } else {
                    window.close();
                    return true;
                }
            }
        }

        window.clear();
        window.draw(backgroundSprite);
        window.draw(textBackground);
        window.draw(title);

        if (currentPlayerIndex < players.size()) {
            message.setString("It's " + players[currentPlayerIndex]->getName() + "'s turn! Press ENTER to assign a role.");
        } else {
            message.setString("All players have roles! Press Enter to start!");
        }

        window.draw(message);

        if (currentPlayerIndex > 0 && currentPlayerIndex <= players.size()) {
            window.draw(roleMessage);
        }

        window.draw(playersLabel);

        float yOffset = 230;
        for (const auto& p : players) {
            sf::Text playerText("- " + p->getName(), font, 20);
            playerText.setFillColor(sf::Color::Yellow);
            playerText.setPosition(140, yOffset);
            window.draw(playerText);
            yOffset += 30;
        }

        window.display();
    }

    return false;
}
//----------------------------------------------------------------------------------
void runGameLoopScreen(Game& game) {
    sf::RenderWindow window(sf::VideoMode(900, 550), "Coup Game Board");

    sf::Font font;
    if (!font.loadFromFile("assets/OpenSans_Regular.ttf")) {
        std::cerr << "Failed to load font\n";
        return;
    }

    sf::Texture backgroundTexture;
    if (!backgroundTexture.loadFromFile("assets/boardGame.png")) {
        std::cerr << "Failed to load boardGame.png\n";
        return;
    }
    sf::Sprite backgroundSprite(backgroundTexture);
    float scaleX = (float)window.getSize().x / backgroundTexture.getSize().x;
    float scaleY = (float)window.getSize().y / backgroundTexture.getSize().y;
    backgroundSprite.setScale(scaleX, scaleY);

    std::map<std::string, std::string> lastActions;
    std::string firstPlayerName = game.currentPlayer()->getName();

    bool turnEnded = false;

    while (window.isOpen()) {
        Player* current = game.currentPlayer();

        sf::Event event;
        while (window.pollEvent(event)) {
            if (event.type == sf::Event::Closed)
                window.close();

            if (event.type == sf::Event::KeyPressed) {
                if (event.key.code == sf::Keyboard::Enter && turnEnded) {
                    game.nextTurn();
                    turnEnded = false;

                    try {
                        std::string winnerName = game.winner();
                        runWinnerScreen(winnerName);
                        window.close();
                        game.cleanup();
                        exit(0);
                    } catch (...) {
                        // game still ongoing
                    }

                    firstPlayerName = game.currentPlayer()->getName();
                    continue;
                }

                if (!turnEnded || event.key.code == sf::Keyboard::Num3) {
                    int actionType = 0;
                    if (event.key.code == sf::Keyboard::Num1) actionType = 1;
                    else if (event.key.code == sf::Keyboard::Num2) actionType = 2;
                    else if (event.key.code == sf::Keyboard::Num3) actionType = 3;
                    else if (event.key.code == sf::Keyboard::Num4) actionType = 4;
                    else if (event.key.code == sf::Keyboard::Num5) actionType = 5;
                    else if (event.key.code == sf::Keyboard::Num6) actionType = 6;
                    else if (event.key.code == sf::Keyboard::Num7) actionType = 7;
                    else if (event.key.code == sf::Keyboard::Num8) actionType = 8;

                    if (actionType >= 1 && actionType <= 8) {
                        std::string targetName = "";

                        if ((actionType >= 4 && actionType <= 6) || actionType == 7) {
                            bool needsTarget = popupYesNo(window, font, "Do you want to select a target?");
                            if (needsTarget) {
                                targetName = popupSelectTarget(window, game.getPlayers(), font);
                            }
                        }

                        try {
                            std::string actionResult = game.handleAction(actionType, targetName);

                            if (actionResult.find("Action failed") != std::string::npos) {
                                std::cout << actionResult << std::endl;
                            } else {
                                lastActions[current->getName()] = actionResult;

                                if (actionType == 3 && actionResult.find("Judge cancelled") != std::string::npos) {
                                    turnEnded = true;
                                } else if (actionType == 2 && actionResult.find("Governor cancelled") != std::string::npos) {
                                    turnEnded = false;
                                } else if (actionType == 7 && !current->getRole()->endsTurnOnSpecial()) {
                                    turnEnded = false;
                                } else {
                                    turnEnded = true;
                                }
                            }
                        } catch (const std::exception& e) {
                            std::cout << "Exception: " << e.what() << std::endl;
                        }
                    }
                }
            }
        }

        window.clear();
        window.draw(backgroundSprite);

        sf::Text title("COUP GAME", font, 36);
        title.setStyle(sf::Text::Bold);
        title.setFillColor(sf::Color::White);
        title.setPosition(window.getSize().x / 2 - title.getLocalBounds().width / 2, 10);
        window.draw(title);

        // Left Info Box
        sf::RectangleShape leftBox(sf::Vector2f(300, 460));
        leftBox.setFillColor(sf::Color(50, 50, 50, 150));
        leftBox.setPosition(10, 50);
        window.draw(leftBox);

        // Right Players Box
        sf::RectangleShape rightBox(sf::Vector2f(200, 300));
        rightBox.setFillColor(sf::Color(50, 50, 50, 150));
        rightBox.setPosition(window.getSize().x - 210, 50);
        window.draw(rightBox);

        float yOffset = 70;
        std::vector<std::pair<std::string, std::string>> info = {
            {"Current Player: ", current->getName()},
            {"Role: ", current->getRoleName()},
            {"Coins: ", std::to_string(current->getCoins())},
            {"Sanction: ", current->isSanctioned() ? "Yes" : "No"},
            {"Blocked: ", current->isArrestBlocked() ? "Yes" : "No"},
        };

        for (const auto& pair : info) {
            sf::Text key(pair.first, font, 22);
            key.setFillColor(sf::Color::Yellow);
            key.setPosition(20, yOffset);

            sf::Text val(pair.second, font, 22);
            val.setFillColor(sf::Color::White);
            val.setPosition(25 + key.getLocalBounds().width, yOffset);

            window.draw(key);
            window.draw(val);
            yOffset += 30;
        }

        yOffset += 15;
        sf::Text prompt(turnEnded ? "Press ENTER to end turn" : "Press action number:", font, 22);
        prompt.setFillColor(sf::Color(255, 215, 0));
        prompt.setPosition(20, yOffset);
        window.draw(prompt);
        yOffset += 35;

        std::vector<std::string> actions = {
            "1 - Gather", "2 - Tax", "3 - Bribe", "4 - Arrest",
            "5 - Sanction", "6 - Coup", "7 - Special Ability", "8 - Pass"
        };

        for (const auto& action : actions) {
            sf::Text aText(action, font, 22);
            aText.setFillColor(sf::Color(255, 215, 0));
            aText.setPosition(20, yOffset);
            window.draw(aText);
            yOffset += 30;
        }

        // Player list
        float playerY = 70;
        for (const Player* p : game.getPlayers()) {
            sf::Text pText(p->getName() + (p->isActive() ? " (Active)" : " (Out)"), font, 18);
            pText.setFillColor(p->isActive() ? sf::Color::Green : sf::Color::Red);
            pText.setPosition(window.getSize().x - 200, playerY);
            window.draw(pText);
            playerY += 30;
        }

        // Last Actions box
        if (!lastActions.empty()) {
            float xPos = 450;
            float yPos = 350;

            sf::Text label("Last Actions:", font, 24);
            label.setFillColor(sf::Color::Yellow);
            label.setPosition(xPos, yPos);
            window.draw(label);
            yPos += 30;

            for (const Player* p : game.getPlayers()) {
                auto it = lastActions.find(p->getName());
                if (it != lastActions.end()) {
                    sf::Text actionText(p->getName() + ": " + it->second, font, 20);
                    actionText.setFillColor(sf::Color::White);
                    actionText.setPosition(xPos, yPos);
                    window.draw(actionText);
                    yPos += 26;
                }
            }
        }

        window.display();
    }
}

//---------------------------------------------------------------------------------------------------------------------

