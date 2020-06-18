package tictactoe;
import javafx.util.Pair;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.abs;

enum FieldState {
    ANALYZING, X_WINS, O_WINS, DRAW, GAME_NOT_FINISHED, IMPOSSIBLE,
}

public class Main {
    static char X = 'X';
    static char O = 'O';
    static char EMPTY = '_';
    private static FieldState state = FieldState.ANALYZING;
    static Scanner scanner = new Scanner(System.in);
    static boolean isInputValid = false;
    static Pattern twoIntegers = Pattern.compile("(\\d+) (\\d+)");

    public static void main(String[] args) {
        char[][] field = new char[3][3];
        System.out.println("Enter cells:");
        String input = scanner.nextLine();
        System.out.println(input);
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                field[i][j] = input.charAt(i*3 + j);
            }
        }
        printFieldState(field);
        Pair<Integer, Integer> targetCoord = null;
        while(!isInputValid) {
            try {
            targetCoord = getMove(field);
            } catch (TicTacToeException e) {
                System.out.println(e.getMessage());
            }
        }

        updateField(field, targetCoord);
        printFieldState(field);
    }

    private static void updateField(char[][] field, Pair<Integer, Integer> targetCoord) {
        int row = targetCoord.getKey();
        int col = targetCoord.getValue();
        field[row][col] = X;
    }


    private static Pair<Integer, Integer> getMove(char[][] field) throws TicTacToeException {
        System.out.println("Enter the coordinates:");
        String input = scanner.nextLine();
        Matcher m = twoIntegers.matcher(input);

        if(m.matches()) {
            int targetCol = Integer.parseInt(m.group(1));
            int targetRow = Integer.parseInt(m.group(2));
            if(targetCol > 3 || targetCol < 1 || targetRow > 3 || targetRow < 1) {
                throw new TicTacToeException("Coordinates should be from 1 to 3!");
            }
            int row = Math.abs(targetRow - 3);
            int col = Math.abs(targetCol - 1);
            if (field[row][col] != EMPTY) {
                throw new TicTacToeException("This cell is occupied! Choose another one!");
            }
            isInputValid = true;
            return new Pair<>(row, col);
        } else {
            throw new TicTacToeException("You should enter numbers!");
        }
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
