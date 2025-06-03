#define DOCTEST_CONFIG_IMPLEMENT_WITH_MAIN
#include "doctest/doctest.h"
#include "Player.hpp"
#include "Player.hpp"
#include "Roles/Baron.hpp"
#include "Roles/Governor.hpp"
#include "Roles/Judge.hpp"
#include "Roles/General.hpp"
#include "Roles/Spy.hpp"
#include "Roles/Merchant.hpp"


TEST_CASE("Player basic functionality") {
    Role* role = new Baron();
    Player p("Roy", role);

    CHECK(p.getName() == "Roy");
    CHECK(p.getCoins() == 0);
    CHECK(p.isActive());
    CHECK(!p.isSanctioned());
    CHECK(p.getRoleName() == "Baron");

    p.addCoins(5);
    CHECK(p.getCoins() == 5);

    p.removeCoins(2);
    CHECK(p.getCoins() == 3);

    p.setSanctioned(true);
    CHECK(p.isSanctioned());

    p.deactivate();
    CHECK(!p.isActive());
    p.reactivate();
    CHECK(p.isActive());

    delete role; 
}

TEST_CASE("Player gather and tax") {
    Role* role = new Baron();
    Player p("Eden", role);

    p.gather();
    CHECK(p.getCoins() == 1);

    p.tax();
    CHECK(p.getCoins() == 3);  

    delete role;
}

TEST_CASE("Player arrest") {
    Role* role = new Baron();
    Player p("Eden", role);
    Role* role2 = new Baron();
    Player p2("Roy", role);

    p2.addCoins(1);

    p.arrest(p2);
    CHECK(p2.getCoins() == 0);

    CHECK(p.getCoins() == 1);  

    delete role;
    delete role2;
}

TEST_CASE("Player bribe and pass") {
    Role* role = new Baron();
    Player p("Dan", role);
    p.addCoins(5);
    p.tax();


    p.bribe();
    p.tax();
    CHECK(p.getCoins() == 5);

    p.pass();
    CHECK(true); 

    delete role;
}

TEST_CASE("Player performs a coup") {
    Role* role1 = new Baron();
    Role* role2 = new Governor();

    Player attacker("Roy", role1);
    Player target("Eden", role2);

    attacker.addCoins(7);
    attacker.coup(target);

    CHECK(attacker.getCoins() == 0);
    CHECK(!target.isActive());
    CHECK(attacker.getLastAction() == LastAction::coup);
    CHECK(attacker.getLastCoupTarget() == &target);

    delete role1;
    delete role2;
}

TEST_CASE("Player invalid coin operations") {
    Role* role = new Baron();
    Player p("Roy", role);

    CHECK_THROWS_AS(p.removeCoins(1), std::invalid_argument);
    CHECK_THROWS_AS(p.addCoins(-1), std::invalid_argument);
    p.addCoins(2);
    CHECK_THROWS_AS(p.removeCoins(3), std::invalid_argument);

    delete role;
}

TEST_CASE("Baron special ability - success and failure cases") {
    Baron* baron = new Baron();
    Player p("Roy", baron);

    SUBCASE("Fails when player has less than 3 coins") {
        p.addCoins(2);
        std::string msg = baron->specialAbility(p);
        CHECK(msg == "Roy does not have enough coins to invest (needs 3).");
        CHECK(p.getCoins() == 2);  
    }

    SUBCASE("Succeeds when player has enough coins") {
        p.addCoins(3);
        std::string msg = baron->specialAbility(p);
        CHECK(msg == "Roy invested 3 coins and received 6 coins!");
        CHECK(p.getCoins() == 6);  
    }

    delete baron;
}

TEST_CASE("General blocks a coup and revives the target") {
    General* generalRole = new General();
    Baron* baron = new Baron();

    Player general("GeneralGuy", generalRole);
    Player attacker("Attacker", baron);
    Player target("Target", baron);

    attacker.addCoins(7);
    attacker.coup(target);

    CHECK(!target.isActive()); 

    std::string msg = generalRole->specialAbility(general, attacker);
    CHECK(msg == " blocked Attacker's coup. Target is saved!");
    CHECK(target.isActive());  
    CHECK(attacker.getLastAction() == LastAction::None);

    delete generalRole;
    delete baron;
}

TEST_CASE("General blocks a coup on himself") {
    General* generalRole = new General();
    Baron* baron = new Baron();

    Player general("GeneralGuy", generalRole);
    Player attacker("Attacker", baron);

    general.addCoins(1);

    attacker.addCoins(5);
    attacker.arrest(general);

    CHECK(general.getCoins() == 1); 

    delete generalRole;
    delete baron;
}

TEST_CASE("Governor tax gives 3 coins") {
    Governor* govRole = new Governor();
    Player gov("Governor", govRole);

    gov.tax();  
    CHECK(gov.getCoins() == 3);

    delete govRole;
}

TEST_CASE("Governor can undo another player's tax") {
    Governor* govRole = new Governor();
    Governor* otherGovRole = new Governor(); 

    Player gov("Gov", govRole);
    Player other("Other", otherGovRole);

    other.tax();  
    CHECK(other.getCoins() == 3);

    std::string result = govRole->specialAbility(gov, other);
    CHECK(result == " undo Other's tax action.");
    CHECK(other.getCoins() == 0);  
    CHECK(other.getLastAction() == LastAction::None);

    delete govRole;
    delete otherGovRole;
}

TEST_CASE("Sanctioning a Judge costs 1 extra coin") {
    Judge* judgeRole = new Judge();
    Role* spy = new Spy();  

    Player judge("Judge", judgeRole);
    Player attacker("Attacker", spy);

    attacker.addCoins(4); 

    attacker.sanction(judge);
    CHECK(attacker.getCoins() == 0);  
    CHECK(judge.isSanctioned());

    delete judgeRole;
    delete spy;
}

TEST_CASE("Sanction fails if attacker has only 3 coins vs Judge") {
    Judge* judgeRole = new Judge();
    Role* spy = new Spy();  

    Player judge("Judge", judgeRole);
    Player attacker("Attacker", spy);

    attacker.addCoins(3);  

    CHECK_THROWS_WITH(attacker.sanction(judge), "Not enough coins he is a Judge! ");

    delete judgeRole;
    delete spy;
}

TEST_CASE("Merchant gains 1 coin at start of turn if has 3 or more") {
    Merchant* mRole = new Merchant();
    Player merchant("Maya", mRole);

    merchant.addCoins(3);  
    std::string msg = mRole->specialAbility(merchant);

    CHECK(merchant.getCoins() == 4);
    CHECK(msg == "Got 1 free coin ");

    delete mRole;
}

TEST_CASE("Merchant loses 2 coins when arrested") {
    Merchant* mRole = new Merchant();
    Role* attackerRole = new Spy();  

    Player merchant("Maya", mRole);
    Player attacker("Roy", attackerRole);

    merchant.addCoins(3);
    attacker.arrest(merchant);

    CHECK(merchant.getCoins() == 1);  
    CHECK(attacker.getCoins() == 0);   

    delete mRole;
    delete attackerRole;
}

TEST_CASE("Arrest fails if Merchant has less than 2 coins") {
    Merchant* mRole = new Merchant();
    Role* attackerRole = new Spy(); 

    Player merchant("Maya", mRole);
    Player attacker("Roy", attackerRole);

    merchant.addCoins(1);

    CHECK_THROWS_WITH(attacker.arrest(merchant), "Target has no coins to take");

    delete mRole;
    delete attackerRole;
}

TEST_CASE("Spy reveals coin amount and blocks arrest") {
    Spy* spyRole = new Spy();
    Role* dummyRole = new Baron();  

    Player spy("Roy", spyRole);
    Player target("Maya", dummyRole);
    target.addCoins(4);

    std::string result = spyRole->specialAbility(spy, target);

    CHECK(result == "Maya has 4 coins and arrest blocked");
    CHECK(target.getCoins() == 4);             
    CHECK(target.isActive());                  
    CHECK_THROWS_WITH(target.arrest(spy), "You are blocked from using arrest this turn");

    delete spyRole;
    delete dummyRole;
}