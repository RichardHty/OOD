public class MazeData {
    public static final char ROAD = ' ';
    public static final char WALL = '#';
    private int row;
    private int col;
    public char[][] maze;

    private int entranceX, entranceY;
    private int exitX, exitY;
    public MazeData(int n, int m){
        if(n %2 == 0 || m %2 == 0){
            throw new IllegalArgumentException("Our Maze needs odd number for row count and col count.");
        }
        row = n;
        col = m;
        maze = new char[n][m];
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(i%2 == 1 && j %2 == 1){
                    maze[i][j] = ROAD;
                } else {
                    maze[i][j] = WALL;
                }
            }
        }
        entranceX = 1;
        entranceY = 0;
        exitX = n - 2;
        exitY = m - 1;
        maze[entranceX][entranceY] = ROAD;
        maze[exitX][exitY] = ROAD;
    }

    public int getEntranceX() {
        return entranceX;
    }

    public int getEntranceY() {
        return entranceY;
    }

    public int getExitX() {
        return exitX;
    }

    public int getExitY() {
        return exitY;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
