import java.util.*;
class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int rows = scanner.nextInt();
        int cols = scanner.nextInt();
        int[][] matrix = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }
        int swap1 = scanner.nextInt();
        int swap2 = scanner.nextInt();

        int[] temp = new int[rows];

        for (int i = 0; i < rows; i++) {
            temp[i] = matrix[i][swap1];
            matrix[i][swap1] = matrix[i][swap2];
            matrix[i][swap2] = temp[i];
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.print("\n");
        }

    }
}