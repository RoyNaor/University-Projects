// roynaor10@gmail.com

#define DOCTEST_CONFIG_IMPLEMENT_WITH_MAIN
#include <doctest/doctest.h>
#include "SquareMat.h"
using namespace matrixlib;

double** createData(int size, double base = 0) {
    double** data = new double*[size];
    for (int i = 0; i < size; ++i) {
        data[i] = new double[size];
        for (int j = 0; j < size; ++j) {
            data[i][j] = base + i + j;
        }
    }
    return data;
}

void cleanup(double** data, int size) {
    for (int i = 0; i < size; ++i) delete[] data[i];
    delete[] data;
}

TEST_CASE("Constructor, copy, assignment, and equality") {
    int size = 2;
    double** data = createData(size);
    SquareMat mat(data, size);

    SUBCASE("Copy constructor") {
        SquareMat copy(mat);
        CHECK(copy == mat);
    }

    SUBCASE("Assignment") {
        SquareMat other = mat;
        CHECK(other == mat);
    }

    cleanup(data, size);
}

TEST_CASE("Operator + and -") {
    int size = 2;
    double** d1 = createData(size, 1);
    double** d2 = createData(size, 2);

    SquareMat m1(d1, size);
    SquareMat m2(d2, size);
    SquareMat sum = m1 + m2;
    SquareMat diff = m2 - m1;

    CHECK(sum[0][0] == doctest::Approx(3));  
    CHECK(diff[1][1] == doctest::Approx(1)); 

    cleanup(d1, size);
    cleanup(d2, size);
}

TEST_CASE("Scalar multiplication, division, negation") {
    int size = 2;
    double** d1 = createData(size, 1);
    SquareMat m(d1, size);

    SquareMat scaled = m * 2;
    SquareMat divided = m / 2;
    SquareMat neg = -m;

    CHECK(scaled[0][0] == doctest::Approx(2));
    CHECK(divided[1][1] == doctest::Approx(1.5)); 
    CHECK(neg[0][0] == doctest::Approx(-1));      

    cleanup(d1, size);
}

TEST_CASE("Modulo operator % with scalar") {
    int size = 2;
    double** d = createData(size, 3);
    SquareMat m(d, size);

    SquareMat modded = m % 2;
    CHECK(modded[0][0] == doctest::Approx(1));
    CHECK(modded[1][1] == doctest::Approx(1));

    cleanup(d, size);
}

TEST_CASE("Matrix multiplication and exponentiation") {
    int size = 2;
    double** d = createData(size, 1);
    SquareMat m(d, size);

    SquareMat mult = m * m;
    SquareMat pow = m ^ 2;

    CHECK(mult == pow);

    cleanup(d, size);
}

TEST_CASE("Increment and decrement") {
    int size = 2;
    double** d = createData(size);
    SquareMat m(d, size);

    m++;
    ++m;
    CHECK(m[0][0] == doctest::Approx(2));
    m--;
    --m;
    CHECK(m[0][0] == doctest::Approx(0));

    cleanup(d, size);
}

TEST_CASE("Transpose and determinant") {
    int size = 2;
    double** d = createData(size, 1);
    SquareMat m(d, size);

    ~m;
    CHECK(m[0][1] == doctest::Approx(2));
    CHECK(m[1][0] == doctest::Approx(2));

    double det = !m;
    CHECK(det == doctest::Approx(-1)); 

    cleanup(d, size);
}

TEST_CASE("Out of range access throws") {
    int size = 2;
    double** d = createData(size);
    SquareMat m(d, size);

    CHECK_THROWS_AS(m[-1], std::out_of_range);
    CHECK_THROWS_AS(m[size], std::out_of_range);

    cleanup(d, size);
}
