// roynaor10@gmail.com

#include "SquareMat.h"

namespace matrixlib {

    SquareMat::SquareMat(double** input, int size) {
        if (size <= 0) {
            throw std::invalid_argument("Invalid matrix size");
        }

        this->size = size;

        mat = new double*[size];
        for (int i = 0; i < size; ++i) {
            mat[i] = new double[size];
            for (int j = 0; j < size; ++j) {
                mat[i][j] = input[i][j];
            }
        }
    }
    //-------------------------------------------------------------------
    SquareMat::SquareMat(const SquareMat& other) {
        size = other.size;
        mat = new double*[size];
        for (int i = 0; i < size; ++i) {
            mat[i] = new double[size];
            for (int j = 0; j < size; ++j) {
                mat[i][j] = other.mat[i][j];
            }
        }
    }
    //-------------------------------------------------------------------
    SquareMat::~SquareMat() {
        for (int i = 0; i < size; ++i) {
            delete[] mat[i];
        }
        delete[] mat;
    }
    //-------------------------------------------------------------------
    int SquareMat::getSize() const {
        return size;
    }
    //-------------------------------------------------------------------
    double* SquareMat::operator[](int row) {
        if (row < 0 || row >= size) {
            throw std::out_of_range("Row index out of range");
        }
        return mat[row];
    }
    //-------------------------------------------------------------------
    const double* SquareMat::operator[](int row) const {
        if (row < 0 || row >= size) {
            throw std::out_of_range("Row index out of range");
        }
        return mat[row];
    }
    //-------------------------------------------------------------------
    SquareMat& SquareMat::operator=(const SquareMat& other) {
        if (this == &other) return *this;

        for (int i = 0; i < size; ++i) {
            delete[] mat[i];
        }
        delete[] mat;

        size = other.size;
        mat = new double*[size];
        for (int i = 0; i < size; ++i) {
            mat[i] = new double[size];
            for (int j = 0; j < size; ++j) {
                mat[i][j] = other.mat[i][j];
            }
        }

        return *this;
    }
    //-------------------------------------------------------------------
    SquareMat SquareMat::operator+(const SquareMat& other) {
        if (other.getSize() != getSize()) {
            throw std::invalid_argument("Matrix sizes do not match!");
        }

        double** tempData = new double*[size];
        for (int i = 0; i < size; ++i) {
            tempData[i] = new double[size];
            for (int j = 0; j < size; ++j) {
                tempData[i][j] = mat[i][j] + other.mat[i][j];
            }
        }

        SquareMat result(tempData, size);

        for (int i = 0; i < size; ++i) {
            delete[] tempData[i];
        }
        delete[] tempData;

        return result;
    }
    //-------------------------------------------------------------------
    SquareMat SquareMat::operator-(const SquareMat& other) {
        if (other.getSize() != getSize()) {
            throw std::invalid_argument("Matrix sizes do not match!");
        }

        double** tempData = new double*[size];
        for (int i = 0; i < size; ++i) {
            tempData[i] = new double[size];
            for (int j = 0; j < size; ++j) {
                tempData[i][j] = mat[i][j] - other.mat[i][j];
            }
        }

        SquareMat result(tempData, size);

        for (int i = 0; i < size; ++i) {
            delete[] tempData[i];
        }
        delete[] tempData;

        return result;
    }
    //-------------------------------------------------------------------
    SquareMat SquareMat::operator-() const {
        double** tempData = new double*[size];
        for (int i = 0; i < size; ++i) {
            tempData[i] = new double[size];
            for (int j = 0; j < size; ++j) {
                tempData[i][j] = mat[i][j] * -1;
            }
        }

        SquareMat result(tempData, size);

        for (int i = 0; i < size; ++i) delete[] tempData[i];
        delete[] tempData;

        return result;
    }

    //-------------------------------------------------------------------
    SquareMat SquareMat::operator*(const SquareMat& other) {
        if (other.getSize() != size) {
            throw std::invalid_argument("Matrix sizes do not match!");
        }

        double** resultData = new double*[size];
        for (int i = 0; i < size; ++i) {
            resultData[i] = new double[size];
            for (int j = 0; j < size; ++j) {
                resultData[i][j] = 0;
                for (int k = 0; k < size; ++k) {
                    resultData[i][j] += mat[i][k] * other.mat[k][j];
                }
            }
        }

        SquareMat result(resultData, size);

        // Clean up temporary matrix
        for (int i = 0; i < size; ++i) delete[] resultData[i];
        delete[] resultData;

        return result;
    }
    //-------------------------------------------------------------------
    SquareMat SquareMat::operator*(double scalar) const {
        double** tempData = new double*[size];
        for (int i = 0; i < size; ++i) {
            tempData[i] = new double[size];
            for (int j = 0; j < size; ++j) {
                tempData[i][j] = mat[i][j] * scalar;
            }
        }

        SquareMat result(tempData, size);

        for (int i = 0; i < size; ++i) delete[] tempData[i];
        delete[] tempData;

        return result;
    }
    //-------------------------------------------------------------------
    SquareMat operator*(double scalar, const SquareMat& mat) {
        return mat * scalar;
    }
    //-------------------------------------------------------------------
    SquareMat SquareMat::operator%(const SquareMat& other) {
        if (other.getSize() != getSize()) {
            throw std::invalid_argument("Matrix sizes do not match!");
        }

        double** tempData = new double*[size];
        for (int i = 0; i < size; ++i) {
            tempData[i] = new double[size];
            for (int j = 0; j < size; ++j) {
                tempData[i][j] = mat[i][j] * other.mat[i][j];
            }
        }

        SquareMat result(tempData, size);

        for (int i = 0; i < size; ++i) {
            delete[] tempData[i];
        }
        delete[] tempData;

        return result;
    }
    //-------------------------------------------------------------------
    SquareMat SquareMat::operator%(int scalar) const {
        if (scalar == 0) {
            throw std::invalid_argument("Can't modulo with zero.");
        }

        double** tempData = new double*[size];
        for (int i = 0; i < size; ++i) {
            tempData[i] = new double[size];
            for (int j = 0; j < size; ++j) {
                tempData[i][j] = (int)(mat[i][j]) % scalar;
            }
        }

        SquareMat result(tempData, size);

        for (int i = 0; i < size; ++i) delete[] tempData[i];
        delete[] tempData;

        return result;
    }
    //-------------------------------------------------------------------
    SquareMat SquareMat::operator/(double scalar) const {
        if (scalar == 0) {
            throw std::invalid_argument("Can't divide with zero.");
        }

        double** tempData = new double*[size];
        for (int i = 0; i < size; ++i) {
            tempData[i] = new double[size];
            for (int j = 0; j < size; ++j) {
                tempData[i][j] = (mat[i][j]) / scalar;
            }
        }

        SquareMat result(tempData, size);

        for (int i = 0; i < size; ++i) delete[] tempData[i];
        delete[] tempData;

        return result;
    }
    //-------------------------------------------------------------------
    SquareMat SquareMat::operator^(int exponent) const{
        if (exponent < 0) {
            throw std::invalid_argument("Can't do it with negative exponent!!");
        }

        // Identity matrix
        double** identityData = new double*[size];
        for (int i = 0; i < size; ++i) {
            identityData[i] = new double[size];
            for (int j = 0; j < size; ++j) {
                identityData[i][j] = (i == j) ? 1 : 0;
            }
        }

        SquareMat result(identityData, size);

        for (int i = 0; i < size; ++i) delete[] identityData[i];
        delete[] identityData;

        if (exponent == 0) return result;

        SquareMat base(*this);

        for (int i = 1; i <= exponent; ++i) {
            result = result * base;
        }

        return result;
    }
    //-------------------------------------------------------------------
    SquareMat& SquareMat::operator++() {
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                mat[i][j] += 1;
            }
        }
        return *this;
    }
    //-------------------------------------------------------------------
    SquareMat SquareMat::operator++(int) {
        SquareMat old(*this);

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                mat[i][j] += 1;
            }
        }

        return old;
    }
    //-------------------------------------------------------------------
    SquareMat& SquareMat::operator--() {
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                mat[i][j] -= 1;
            }
        }
        return *this;
    }
    //-------------------------------------------------------------------
    SquareMat SquareMat::operator--(int) {
        SquareMat old(*this);

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                mat[i][j] -= 1;
            }
        }

        return old;
    }
    //-------------------------------------------------------------------
    SquareMat& SquareMat::operator~() {
        for (int i = 0; i < size; ++i) {
            for (int j = i + 1; j < size; ++j) {
                double temp = mat[i][j];
                mat[i][j] = mat[j][i];
                mat[j][i] = temp;
            }
        }
        return *this;
    }
    //-------------------------------------------------------------------
    bool SquareMat::operator==(const SquareMat& other) const {
        if (size != other.size) return false;

        double sum1 = 0, sum2 = 0;

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                sum1 += mat[i][j];
                sum2 += other.mat[i][j];
            }
        }

        return sum1 == sum2;
    }
    //-------------------------------------------------------------------
    bool SquareMat::operator!=(const SquareMat& other) const {
        return !(*this == other);
    }
    //-------------------------------------------------------------------
    bool SquareMat::operator>(const SquareMat& other) const {
        if (size != other.size) return false;

        double sum1 = 0, sum2 = 0;

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                sum1 += mat[i][j];
                sum2 += other.mat[i][j];
            }
        }

        return sum1 > sum2;
    }
    //-------------------------------------------------------------------
    bool SquareMat::operator<(const SquareMat& other) const {
        if (size != other.size) return false;

        double sum1 = 0, sum2 = 0;

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                sum1 += mat[i][j];
                sum2 += other.mat[i][j];
            }
        }

        return sum1 < sum2;
    }
    //-------------------------------------------------------------------
    bool SquareMat::operator<=(const SquareMat& other) const {
        return !(*this > other);
    }
    //-------------------------------------------------------------------
    bool SquareMat::operator>=(const SquareMat& other) const {
        return !(*this < other);
    }
    //-------------------------------------------------------------------
    double SquareMat::operator!() const {
        if (size == 1) {
            return mat[0][0];
        }
        if (size == 2) {
            return mat[0][0] * mat[1][1] - mat[0][1] * mat[1][0];
        }

        double det = 0;

        for (int col = 0; col < size; ++col) {
            double** minor = new double*[size - 1];
            for (int i = 0; i < size - 1; ++i) {
                minor[i] = new double[size - 1];
            }

            for (int i = 1; i < size; ++i) {
                int minorCol = 0;
                for (int j = 0; j < size; ++j) {
                    if (j == col) continue;
                    minor[i - 1][minorCol++] = mat[i][j];
                }
            }

            SquareMat subMat(minor, size - 1);
            double cofactor = ((col % 2 == 0) ? 1 : -1) * mat[0][col] * !subMat;
            det += cofactor;

            for (int i = 0; i < size - 1; ++i) delete[] minor[i];
            delete[] minor;
        }

        return det;
    }
    //-------------------------------------------------------------------
    SquareMat& SquareMat::operator+=(const SquareMat& other) {
        *this = *this + other;
        return *this;
    }
    //-------------------------------------------------------------------
    SquareMat& SquareMat::operator-=(const SquareMat& other) {
        *this = *this - other;
        return *this;
    }
    //-------------------------------------------------------------------
    SquareMat& SquareMat::operator*=(const SquareMat& other) {
        *this = *this * other;
        return *this;
    }
    //-------------------------------------------------------------------
    SquareMat& SquareMat::operator*=(double scalar) {
        *this = *this * scalar;
        return *this;
    }
    //-------------------------------------------------------------------
    SquareMat& SquareMat::operator/=(double scalar) {
        *this = *this / scalar;
        return *this;
    }
    //-------------------------------------------------------------------
    SquareMat& SquareMat::operator%=(const SquareMat& other) {
        *this = *this % other;
        return *this;
    }
    //-------------------------------------------------------------------
    SquareMat& SquareMat::operator%=(int scalar) {
        *this = *this % scalar;
        return *this;
    }
    //-------------------------------------------------------------------
    std::ostream& operator<<(std::ostream& os, const SquareMat& mat) {
        for (int i = 0; i < mat.size; ++i) {
            for (int j = 0; j < mat.size; ++j) {
                os << mat.mat[i][j] << " ";
            }
            os << "\n";
        }
        return os;
    }
    //-------------------------------------------------------------------


} // namespace matrixlib
