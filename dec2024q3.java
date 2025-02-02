import java.io.*;

public class dec2024q3 {
    static char[][] grid;
    static int count = 0;
    public static void main(String[] args) throws IOException {

        // general process: find which cells are usable (point in right direction), start from final day and move backward

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] line1 = br.readLine().split(" ");
        int N = Integer.parseInt(line1[0]);
        int Q = Integer.parseInt(line1[1]);
        grid = new char[N][N];
        boolean[][] usable = new boolean[N][N];

        int[] xVal = new int[Q+1];
        int[] yVal = new int[Q+1]; 
        int[] res = new int[Q+1]; 
        int Qcopy = Q;

        for (int i=1; i<Q+1; i++){
            String[] temp = br.readLine().split(" ");
            int x = Integer.parseInt(temp[0]);
            int y = Integer.parseInt(temp[1]);
            char direction = temp[2].charAt(0);
            grid[x-1][y-1] = direction;
            xVal[i] = x-1;
            yVal[i] = y-1;
        }
        
        for (int i = 0; i < N; i++){
            for (int j = 0; j < N; j++){
                makeUsable(i, j, N, usable);
            }
        }
        res[Qcopy] = N * N - count;

        while (Qcopy > 1){
            int row = xVal[Qcopy];
            int col = yVal[Qcopy];
            grid[row][col] = '\u0000';
            makeUsable(row, col, N, usable);
            Qcopy--;
            res[Qcopy] = N * N - count;
        }

        for(int i=1; i<=Q; i++){
            System.out.println(res[i]);
        }
        
    }
    private static void makeUsable(int x, int y, int N, boolean[][] usable) {
        if (usable[x][y] || y < 0 || y >= N || x < 0 || x >= N) {
            return;
        }

        if (
            (grid[x][y] == 'U' && (x == 0   || usable[x-1][y])) ||
            (grid[x][y] == 'D' && (x == N-1 || usable[x+1][y])) ||
            (grid[x][y] == 'L' && (y == 0   || usable[x][y-1])) || 
            (grid[x][y] == 'R' && (y == N-1 || usable[x][y+1]))
        ){
            usable[x][y] = true;
        }
        else if ((grid[x][y] == '\u0000')){
            if (x != 0 && usable[x-1][y]){
                usable[x][y] = true;
            }
            else if (x != N-1 && usable[x+1][y]){
                usable[x][y] = true;
            }
            else if (y != 0 && usable[x][y-1]){
                usable[x][y] = true;
            }
            else if (y != N-1 && usable[x][y+1]){
                usable[x][y] = true;
            }
            else if (y == 0 || y == N-1 || x == 0 || x == N - 1) {
                usable[x][y] = true;
            }
        }

        if (usable[x][y]) {
            count += 1;
            for (int i=0; i<4; i++) {
                int newRow = x + branches[i][0];
                int newCol = y + branches[i][1];
                makeUsable(newRow, newCol, N, usable);
            }
        }
    }
    public static int[][] branches = {
        {0, 1},
        {0, -1}, 
        {1, 0},
        {-1, 0},
    };
}
