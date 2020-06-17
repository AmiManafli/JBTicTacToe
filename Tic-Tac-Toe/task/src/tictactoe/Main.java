package tictactoe;
import java.lang.reflect.Field;
import java.util.*;

import static java.lang.Math.abs;

enum FieldState {
    ANALYZING, X_WINS, O_WINS, DRAW, GAME_NOT_FINISHED, IMPOSSIBLE,
}

public class Main {
    static char X = 'X';
    static char O = 'O';
    static char EMPTY = '_';
    private static FieldState state = FieldState.ANALYZING;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char[][] field = new char[3][3];
        String input = scanner.next();
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                field[i][j] = input.charAt(i*3 + j);
            }
        }

        while (analyzeField(field));

        printFieldState(field);
    }

    private static void printFieldState(char[][] field) {
        showField(field);
        switch (state) {
            case X_WINS:
                System.out.println("X wins");
                break;
            case O_WINS:
                System.out.println("O wins");
                break;
            case DRAW:
                System.out.println("Draw");
                break;
            case IMPOSSIBLE:
                System.out.println("Impossible");
                break;
            case GAME_NOT_FINISHED:
                System.out.println("Game not finished");
                break;
        }
    }

    public static boolean analyzeField(char[][] field) {
        return isPossible(field) &&
                checkWinHorizontal(field) &&
                checkWinVertical(field) &&
                checkWinDiagonal(field) &&
                checkGameIsOver(field);
    }

    private static boolean checkGameIsOver(char[][] field) {
        for (char[] chars : field) {
            for (int j = 0; j < field[0].length; j++) {
                if (chars[j] == EMPTY) {
                    state = FieldState.GAME_NOT_FINISHED;
                    return false;
                }
            }
        }
        state = FieldState.DRAW;
        return false;
    }

    private static boolean checkWinDiagonal(char[][] field) {
        int i, j;
        int countX = 0;
        int countO = 0;

        for (i = 0; i < field.length; i++) {
            if (field[i][i] == X) {
                countX++;
            } else if (field[i][i] == O) {
                countO++;
            } else {
                break;
            }
        }

        if (countX == 3) {
            state = FieldState.X_WINS;
            return false;
        } else if (countO == 3) {
            state = FieldState.O_WINS;
            return false;
        }

        countO = 0;
        countX = 0;

        for (i = 0; i < field.length; i++) {
            if (field[i][field.length - 1 - i] == X) {
                countX++;
            } else if (field[i][field.length - 1 - i] == O) {
                countO++;
            } else {
                break;
            }
        }

        if (countX == 3) {
            state = FieldState.X_WINS;
            return false;
        } else if (countO == 3) {
            state = FieldState.O_WINS;
            return false;
        }

        return true;
    }

    private static boolean checkWinVertical(char[][] field) {
        boolean threeInAColX = false;
        boolean threeInAColO = false;
        int countX = 0;
        int countO = 0;

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                if (field[j][i] == X) {
                    countX++;
                } else if (field[j][i] == O) {
                    countO++;
                } else {
                    break;
                }
            }

            if (countX == 3) {
                threeInAColX = true;
            } else if (countO == 3) {
                threeInAColO = true;
            }

            countO = 0;
            countX = 0;
        }

        if (threeInAColO && threeInAColX) {
            state = FieldState.IMPOSSIBLE;
            return false;
        } else if (threeInAColX) {
            state = FieldState.X_WINS;
            return false;
        } else if (threeInAColO) {
            state = FieldState.O_WINS;
            return false;
        } else {
            return true;
        }
    }

    private static boolean checkWinHorizontal(char[][] field) {
        boolean threeInARowX = false;
        boolean threeInARowO = false;
        int countX = 0;
        int countO = 0;

        for (char[] chars : field) {
            for (int j = 0; j < field[0].length; j++) {
                if (chars[j] == X) {
                    countX++;
                } else if (chars[j] == O) {
                    countO++;
                }
            }

            if (countX == 3) {
                threeInARowX = true;
            } else if (countO == 3) {
                threeInARowO = true;
            }

            countO = 0;
            countX  = 0;
        }

        if (threeInARowO && threeInARowX) {
            state = FieldState.IMPOSSIBLE;
            return false;
        } else if (threeInARowX) {
            state = FieldState.X_WINS;
            return false;
        } else if (threeInARowO) {
            state = FieldState.O_WINS;
            return false;
        } else {
            return true;
        }
    }

    private static boolean isPossible(char[][] field) {
        int countX = 0;
        int countO = 0;
        for (char[] chars : field) {
            for (int j = 0; j < field[0].length; j++) {
                if (chars[j] == X) {
                    countX++;
                } else if (chars[j] == O) {
                    countO++;
                }
            }
        }
        if (abs(countX - countO) >= 2) {
            state = FieldState.IMPOSSIBLE;
            return false;
        }
        return true;
    }

    public static void showField(char[][] field) {
        System.out.println("---------");
        for (int i = 0; i < field.length; i++) {
            System.out.print("| ");
            for (int j = 0; j < field[0].length; j++) {
                System.out.print(field[i][j] + " ");
            }
            System.out.println(" |");
        }
        System.out.println("---------");
    }

}
