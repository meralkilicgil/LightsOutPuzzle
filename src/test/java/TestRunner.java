import java.util.List;

class TestRunner implements Runnable {
    private TestCase testCase;
    private int testCaseNumber;

    TestRunner(TestCase testCase, int testCaseNumber) {
        this.testCase = testCase;
        this.testCaseNumber = testCaseNumber;
    }

    @Override
    public void run() {
        List<int[]> solution = PuzzleSolver.solvePuzzle(testCase.initialBoard, testCase.pieces, testCase.depth);
        if (solution == null) {
            System.out.println("Case " + testCaseNumber + ": No solution found.");
        } else {
            System.out.print("Case " + testCaseNumber + ": ");
            for (int[] coord : solution) {
                System.out.print("(" + coord[0] + "," + coord[1] + ") ");
            }
            System.out.println();
        }
    }
}
class TestCase {
    int depth;
    int[][] initialBoard;
    List<char[][]> pieces;

    TestCase(int depth, int[][] initialBoard, List<char[][]> pieces) {
        this.depth = depth;
        this.initialBoard = initialBoard;
        this.pieces = pieces;
    }
}
