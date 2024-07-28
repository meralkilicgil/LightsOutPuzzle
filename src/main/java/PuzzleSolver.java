import java.util.ArrayList;
import java.util.List;

public class PuzzleSolver {

    static List<int[]> solvePuzzle(int[][] initialBoard, List<char[][]> pieces, int depth) {
        List<int[]> placements = new ArrayList<>();
        if (backtrack(initialBoard, pieces, depth, 0, placements)) {
            return placements;
        }
        return null;
    }

    private static boolean backtrack(int[][] board, List<char[][]> pieces, int depth, int pieceIndex, List<int[]> placements) {
        if (pieceIndex == pieces.size()) {
            return isSolved(board);
        }
        char[][] piece = pieces.get(pieceIndex);
        int pieceHeight = piece.length;
        int pieceWidth = piece[0].length;
        int boardHeight = board.length;
        int boardWidth = board[0].length;

        for (int i = 0; i <= boardHeight - pieceHeight; i++) {
            for (int j = 0; j <= boardWidth - pieceWidth; j++) {
                placePiece(board, piece, i, j, depth);
                placements.add(new int[]{i, j});
                if (backtrack(board, pieces, depth, pieceIndex + 1, placements)) {
                    return true;
                }
                placements.remove(placements.size() - 1);
                removePiece(board, piece, i, j, depth);
            }
        }
        return false;
    }

    private static void placePiece(int[][] board, char[][] piece, int startX, int startY, int depth) {
        int pieceHeight = piece.length;
        int pieceWidth = piece[0].length;
        for (int i = 0; i < pieceHeight; i++) {
            for (int j = 0; j < pieceWidth; j++) {
                if (piece[i][j] == 'X') {
                    board[startX + i][startY + j] = (board[startX + i][startY + j] + 1) % depth;
                }
            }
        }
    }

    private static void removePiece(int[][] board, char[][] piece, int startX, int startY, int depth) {
        int pieceHeight = piece.length;
        int pieceWidth = piece[0].length;
        for (int i = 0; i < pieceHeight; i++) {
            for (int j = 0; j < pieceWidth; j++) {
                if (piece[i][j] == 'X') {
                    board[startX + i][startY + j] = (board[startX + i][startY + j] - 1 + depth) % depth;
                }
            }
        }
    }

    private static boolean isSolved(int[][] board) {
        for (int[] row : board) {
            for (int cell : row) {
                if (cell != 0) {
                    return false;
                }
            }
        }
        return true;
    }
}