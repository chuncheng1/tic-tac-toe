
public class Grid {
	private int[][] grid;
	private int[][] horizontal;
	private int[][] vertical;
	private int[][] diagonal;
	private boolean[] winner;
	// 0 = empty, 1 = player, 2 = computer
	public Grid() {
		grid = new int[3][3];
		horizontal = new int[2][3];
		vertical = new int[2][3];
		diagonal = new int[2][2];
		winner = new boolean[2];
	}
	
	public boolean place(int player, int position) {
		int r = 2 - (position-1)/3;
		int c = (position-1)%3;
		if (grid[r][c] != 0) {
			return false;
		} else {
			grid[r][c] = player;
			horizontal[player-1][r]++;
			vertical[player-1][c]++;
			if (horizontal[player-1][r] == 3 || vertical[player-1][c] == 3) {
				winner[player-1] = true;
			}
			if (r == c) {
				diagonal[player-1][0]++;
				if (diagonal[player-1][0] == 3) {
					winner[player-1] = true;
				}
			}
			if (r+c == 2) {
				diagonal[player-1][1]++;
				if (diagonal[player-1][1] == 3) {
					winner[player-1] = true;
				}
			}
			return true;
		}
	}
	
	//0 = nobody won, 1 = player won, 2 = computer won
	public int scanGrid() {
		if (winner[0] == true) {
			return 1;
		} else if (winner[1] == true) {
			return 2;
		} else {
			return 0;
		}
	}
	
	public void printGrid() {
		for (int i=0; i<grid.length; i++) {
			for (int j=0; j<grid[0].length; j++) {
				if (grid[i][j] == 0) {
					System.out.print("  ");
				} else if (grid[i][j] == 1) {
					System.out.print("O ");
				} else {
					System.out.print("X ");
				}
			}
			System.out.println();
		}
	}
}
