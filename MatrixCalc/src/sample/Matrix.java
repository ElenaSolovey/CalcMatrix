package sample;

public class Matrix {
    private int size;
    private double[][] matrix;

    public Matrix(int size) {
        if (size < 1) {
            System.out.println("Размерность матрицы не может быть меньше 1. Будет задана размерность n = 3");
            size = 3;
        }
        this.size = size;
        matrix = new double[size][size];
    }

    public Matrix(Matrix matrix) {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                this.matrix[i][j] = matrix.matrix[i][j];
    }

    public static Matrix createUnitMatrix(int size) {
        Matrix unitMatrix = new Matrix(size);
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                unitMatrix.matrix[i][j] = (i == j) ? 1 : 0;
        return unitMatrix;
    }

    public int getSize() {
        return size;
    }

    public void setIJ(double value, int i, int j) {
        matrix[i][j] = value;
    }

    public double getIJ(int i, int j) {
        return matrix[i][j];
    }

    public Matrix plus (Matrix secondMatrix) {
        if (this.size != secondMatrix.size) {
            System.out.println("Нельзя сложить матрицы разных размерностей");
            return this;
        }
        Matrix result = new Matrix(this.size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result.matrix[i][j] = this.matrix[i][j] + secondMatrix.matrix[i][j];
            }
        }
        return result;
    }

    public Matrix minus (Matrix secondMatrix) {
        if (this.size != secondMatrix.size) {
            System.out.println("Нельзя вычесть матрицу другой размерности");
            return this;
        }
        Matrix result = new Matrix(this.size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result.matrix[i][j] = this.matrix[i][j] - secondMatrix.matrix[i][j];
            }
        }
        return result;
    }

    public Matrix mult(Matrix secondMatrix) {
        if (this.size != secondMatrix.size) {
            System.out.println("Нельзя перемножать матрицы разных размерностей");
            return this;
        }
        Matrix result = new Matrix(this.size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    result.matrix[i][j] += this.matrix[i][k] * secondMatrix.matrix[k][j];
                }
            }
        }
        return result;
    }

    public Matrix mult(double num) {
        Matrix result = new Matrix(size);
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                result.matrix[i][j] = matrix[i][j] * num;
        return result;
    }

    public Matrix transpose() {
        Matrix result = new Matrix(size);
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                result.matrix[i][j] = matrix[j][i];
        return result;
    }

    public Matrix inverse() {
        if (this.det() == 0) {
            System.out.println("Определитель матрицы равен нулю, значит обратной матрицы не существует");
            return null;
        }
        Matrix inverseMatrix = new Matrix(size);
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                Matrix tmp = new Matrix(size);
                for (int k = 0; k < size; k++)
                    for (int l = 0; l < size; l++)
                        if (k != i && l != j)
                            tmp.matrix[k][l] = this.matrix[k][l];
                        else if (k != i || l != j)
                            tmp.matrix[k][l] = 0;
                        else
                            tmp.matrix[k][l] = 1;
                inverseMatrix.matrix[i][j] = tmp.det();
            }
        inverseMatrix = inverseMatrix.transpose();
        inverseMatrix = inverseMatrix.mult(1 / det());
        return inverseMatrix;
    }

    public double det() {
        double det = 1;
        Matrix tmpM = createUnitMatrix(size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tmpM.matrix[i][j] = matrix[i][j];
            }
        }
        for (int k = 0; k < size; k++) {
            if (tmpM.matrix[k][k] == 0)
                for (int i = k + 1; i < size; i++) {
                    double[] tmpL = tmpM.matrix[k];
                    tmpM.matrix[k] = tmpM.matrix[i];
                    tmpM.matrix[i] = tmpL;
                    det *= -1;
                    if (tmpM.matrix[k][k] != 0)
                        break;
                    else if (i == size - 1)
                        return 0;
                }
            for (int i = 1 + k; i < size; i++)
                for (int j = 1; j <= size - k; j++)
                    tmpM.matrix[i][size - j] -= (tmpM.matrix[k][size - j] * tmpM.matrix[i][k] / tmpM.matrix[k][k]);
        }
        for (int i = 0; i < size; i++)
            det *= tmpM.matrix[i][i];
        show();
        return det;
    }

    public Matrix power(int power) {
        Matrix result = createUnitMatrix(size);
        if (power < 0) {
            for (int i = 0; i < -power; i++) {
                result = result.mult(this.inverse());
            }
        } else if (power > 0) {
            for (int i = 0; i < power; i++) {
                result = result.mult(this);
            }
        }
        return result;
    }

    public void show() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}