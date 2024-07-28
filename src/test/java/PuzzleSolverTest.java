import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class PuzzleSolverTest {
    public static void main(String[] args) throws IOException {
        List<TestCase> testCases = loadTestCases();
        ExecutorService executor = Executors.newFixedThreadPool(11); // Adjust the pool size as needed

        for (int i = 0; i < testCases.size(); i++) {
            TestRunner runner = new TestRunner(testCases.get(i), i );
            executor.execute(runner);
        }

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static List<TestCase> loadTestCases() throws IOException {
        List<String> caseList = new ArrayList<>(Arrays.asList("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10"));

        List<TestCase> testCases = new ArrayList<>();
        for (String caseNumber : caseList) {
            String fileName = "src/test/resources/cases/" + caseNumber + ".txt";
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                int depth = Integer.parseInt(line.trim());
                int[][] initialBoard = parseBoard(reader.readLine().trim());
                List<char[][]> pieces = parsePieces(reader.readLine().trim());
                testCases.add(new TestCase(depth, initialBoard, pieces));
            }
            reader.close();
        }
        return testCases;
    }

    private static int[][] parseBoard(String line) {
        String[] rows = line.split(",");
        int[][] board = new int[rows.length][rows[0].length()];
        for (int i = 0; i < rows.length; i++) {
            for (int j = 0; j < rows[i].length(); j++) {
                board[i][j] = rows[i].charAt(j) - '0';
            }
        }
        return board;
    }

    private static List<char[][]> parsePieces(String line) {
        String[] piecesStr = line.split(" ");
        List<char[][]> pieces = new ArrayList<>();
        for (String pieceStr : piecesStr) {
            String[] rows = pieceStr.split(",");
            char[][] piece = new char[rows.length][];
            for (int i = 0; i < rows.length; i++) {
                piece[i] = rows[i].toCharArray();
            }
            pieces.add(piece);
        }
        return pieces;
    }
}