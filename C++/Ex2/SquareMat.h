// roynaor10@gmail.com

#ifndef EX2_SQUAREMAT_H
#define EX2_SQUAREMAT_H

#include <stdexcept>
#include <iostream>

namespace matrixlib {

    class SquareMat {
    private:
        double** mat;
        int size;

    public:
        SquareMat(double** input, int size);                        // Constructor

        SquareMat(const SquareMat& other);                          // Copy constructor

        SquareMat& operator=(const SquareMat& other);               // Copy assignment operator

        ~SquareMat();                                               // Destructor

        int getSize() const;

        double* operator[](int row);                                // mat[i][j] writing

        const double* operator[](int row) const;                    // mat[i][j] reading

        SquareMat operator+(const SquareMat& other);                // Matrix addition

        SquareMat operator-(const SquareMat& other);                // Matrix subtraction

        SquareMat operator-() const;                                // Unary minus

        SquareMat operator*(const SquareMat& other);                // Matrix multiplication

        SquareMat operator*(double scalar) const;                   // Scalar multiplication

        friend SquareMat operator*(double scalar, const SquareMat& mat); // Scalar multiplication

        SquareMat operator%(const SquareMat& other);                // Multiplication by index

        SquareMat operator%(int scalar) const;                      // Modulo with an integer scalar

        SquareMat operator/(double scalar) const;                   // Modulo with a double scalar

        SquareMat operator^(int exponent) const;                    // Raises the matrix by

        SquareMat& operator++();                                    // Pre-increment

        SquareMat operator++(int);                                  // Post-increment

        SquareMat& operator--();                                    // Pre-decrement

        SquareMat operator--(int);                                  // Post-decrement

        SquareMat& operator~();                                    // Transpose

        bool operator==(const SquareMat& other) const;             // Equality

        bool operator!=(const SquareMat& other) const;

        bool operator>(const SquareMat& other) const;             // Bigger than

        bool operator<(const SquareMat& other) const;             // Smaller than

        bool operator>=(const SquareMat& other) const;            // Bigger or equal than

        bool operator<=(const SquareMat& other) const;            // Smaller or equal than

        double operator!() const;                                 // Determinant

        SquareMat& operator+=(const SquareMat& other);

        SquareMat& operator-=(const SquareMat& other);

        SquareMat& operator*=(const SquareMat& other);

        SquareMat& operator*=(double scalar);

        SquareMat& operator/=(double scalar);

        SquareMat& operator%=(const SquareMat& other);

        SquareMat& operator%=(int scalar);

        friend std::ostream& operator<<(std::ostream& os, const SquareMat& mat); // Print matrix

    };

} // namespace matrixlib

#endif //EX2_SQUAREMAT_H
