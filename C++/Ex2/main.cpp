// roynaor10@gmail.com

#include "SquareMat.h"
#include <iostream>

using namespace matrixlib;

int main() {
    int size = 3;

    double** input = new double*[size];
    for (int i = 0; i < size; ++i) {
        input[i] = new double[size];
        for (int j = 0; j < size; ++j) {
            input[i][j] = i + j + 1;  // 1 2 3 ...
        }
    }

    try {
        SquareMat mat(input, size);
        SquareMat mat2 = mat;
        SquareMat mat3(input, size);

        std::cout << "Original mat:\n" << mat << "\n";

        // +, -, unary -
        std::cout << "mat + mat2:\n" << (mat + mat2) << "\n";
        std::cout << "mat - mat2:\n" << (mat - mat2) << "\n";
        std::cout << "-mat:\n" << (-mat) << "\n";

        // Scalar multiplication / division
        std::cout << "mat * 2:\n" << (mat * 2) << "\n";
        std::cout << "2 * mat:\n" << (2 * mat) << "\n";
        std::cout << "mat / 2:\n" << (mat / 2) << "\n";

        // Modulo
        std::cout << "mat % mat2:\n" << (mat % mat2) << "\n";
        std::cout << "mat % 3:\n" << (mat % 3) << "\n";

        // Power
        std::cout << "mat ^ 2:\n" << (mat ^ 2) << "\n";

        // Combined assignment operators
        mat3 += mat;
        std::cout << "mat3 += mat:\n" << mat3 << "\n";

        mat3 -= mat;
        std::cout << "mat3 -= mat:\n" << mat3 << "\n";

        mat3 *= mat;
        std::cout << "mat3 *= mat:\n" << mat3 << "\n";

        mat3 *= 2;
        std::cout << "mat3 *= 2:\n" << mat3 << "\n";

        mat3 /= 2;
        std::cout << "mat3 /= 2:\n" << mat3 << "\n";

        mat3 %= mat;
        std::cout << "mat3 %= mat:\n" << mat3 << "\n";

        mat3 %= 3;
        std::cout << "mat3 %= 3:\n" << mat3 << "\n";

        // Transpose
        ~mat3;
        std::cout << "Transpose of mat3 (~mat3):\n" << mat3 << "\n";

        // Determinant
        std::cout << "Determinant of mat: " << !mat << "\n";

        // Comparison
        std::cout << "mat == mat2? " << (mat == mat2 ? "true" : "false") << "\n";
        std::cout << "mat != mat2? " << (mat != mat2 ? "true" : "false") << "\n";
        std::cout << "mat > mat2? "  << (mat > mat2 ? "true" : "false") << "\n";
        std::cout << "mat >= mat2? " << (mat >= mat2 ? "true" : "false") << "\n";
        std::cout << "mat < mat2? "  << (mat < mat2 ? "true" : "false") << "\n";
        std::cout << "mat <= mat2? " << (mat <= mat2 ? "true" : "false") << "\n";

        // Increment / Decrement
        std::cout << "mat++:\n" << mat++ << "\n";
        std::cout << "After mat++:\n" << mat << "\n";

        std::cout << "++mat:\n" << ++mat << "\n";

        std::cout << "mat--:\n" << mat-- << "\n";
        std::cout << "After mat--:\n" << mat << "\n";

        std::cout << "--mat:\n" << --mat << "\n";

        // Indexing
        std::cout << "mat[1][2] before = " << mat[1][2] << "\n";
        mat[1][2] = 99;
        std::cout << "mat[1][2] after = " << mat[1][2] << "\n";
        std::cout << "Updated mat:\n" << mat << "\n";

    } catch (const std::exception& e) {
        std::cerr << "Exception: " << e.what() << "\n";
    }

    for (int i = 0; i < size; ++i) {
        delete[] input[i];
    }
    delete[] input;

    return 0;
}
