// roynaor10@gmail.com

#ifndef COUP_JUDGE_HPP
#define COUP_JUDGE_HPP

#include "../Role.hpp"
#include <string>

class Judge : public Role {
public:
    std::string getName() const override;
};


#endif //COUP_JUDGE_HPP
