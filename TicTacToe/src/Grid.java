/*
 * Author: Chun Cheng
 * 
 * Grid - records information on the current game
 * player 1 places O. Player 2 places X
 */

import java.util.*;

public class Grid {
	/*
	 * grid - stores the current game board as a 3x3 integer array 
	 * (0 = empty, 1 = placed by player 1, 2 = placed by player 2)
	 */
	private int[][] grid;
	
	/*
	 * horizontal - a 2x3 integer array, horizontal[0][j] stores the 
	 * number of Os player 1 placed in row j so far. horizontal[1][j] stores the
	 * number of Xs player 2 placed in row j so far.
	 */
	private int[][] row; 
	
	/*
	 * vertical - a 2x3 integer array, vertical[0][j] stores the 
	//number of Os player 1 placed in column j so far. horizontal[1][j] stores the
	//number of Xs player 2 placed in column j so far.
	 */
	private int[][] column; 
	
	/*
	 * diagonal - a 2x2 array, diagonal[0][0] stores the 
	 * number of Os player 1 placed in the main diagonal so far. 
	 * horizontal[1][0] stores the number of Xs player 2 placed 
	 * in the main diagonal so far. diagonal[i][1] stores information about the
	 * anti-diagonal in the same manner.
	 */
	private int[][] diagonal; 
	
	/*
	 * potentialScore - a 2x3x3 array. potentialScore[0][i][j] stores the number of 
	 * winning row player 1 can possibly create if it places at row i column j. 
	 * potentialScore[1][i][j] stores the number of winning row player 2 can 
	 * possibly create if it places at row i column j.
	 */
	private int[][][] potential;
	
	/*
	 * winner - an integer that stores who has won. winner=1 means player 1 has won.
	 * winner=2 means player 2 has won. winner=0 means nobody has won.
	 */
	private int winner;
	
	/*
	 * Constructor
	 */
	public Grid() {
		grid = new int[3][3];
		row = new int[2][3];
		column = new int[2][3];
		diagonal = new int[2][2];
		potential = new int[2][3][3];
		potential[0] = new int[][]{{3, 2, 3},
									 {2, 4, 2},
									 {3, 2, 3}};
		potential[1] = new int[][]{{3, 2, 3},
										 {2, 4, 2},
										 {3, 2, 3}};
		winner = 0;
	}
	
	/*
	 * place - takes in a player, a row number, and a column number and 
	 * places the player's move in the grid at such row and column
	 */
	public boolean place(int player, int r, int c) {
		if (player != 1 && player != 2) {
			throw new IllegalArgumentException("Invalid player input.");
		}
		if (r > 2 || r < 0) {
			throw new IllegalArgumentException("Invalid row input.");
		}
		if (c > 2 || c < 0) {
			throw new IllegalArgumentException("Invalid column input.");
		}
		if (grid[r][c] != 0) {
			return false;
		} else {
			grid[r][c] = player;
			
			//update potentialScore
			potential[player-1][r][c] = 0;
			if (row[player-1][r] == 0) {
				for (int i=0; i < potential[0][0].length; i++) {
					if (potential[2-player][r][i] > 0) {
						potential[2-player][r][i]--;
					}
				}
			}
			if (column[player-1][c] == 0) {
				for (int i=0; i < potential[0].length; i++) {
					if (potential[2-player][i][c] > 0) {
						potential[2-player][i][c]--;
					}
				}
			}
			
			//update row and column
			row[player-1][r]++;
			column[player-1][c]++;
			
			if (row[player-1][r] == 3 || column[player-1][c] == 3) {
				winner = player;
			}
			
			if (r == c) {
				if (diagonal[player-1][0] == 0) {
					for (int i=0; i < potential[0].length; i++) {
						if (potential[2-player][i][i] > 0) {
							potential[2-player][i][i]--;
						}
					}
				}
				diagonal[player-1][0]++;
				
				if (diagonal[player-1][0] == 3) {
					winner = player;
				}
			}
			
			if (r+c == 2) {
				if (diagonal[player-1][1] == 0) {
					for (int i=0; i < potential[0].length; i++) {
						if (potential[2-player][i][2-i] > 0) {
							potential[2-player][i][2-i]--;
						}
					}
				}
				diagonal[player-1][1]++;
				
				if (diagonal[player-1][1] == 3) {
					winner = player;
				}
			}
			return true;
		}
	}
	
	/*
	 * scanGrid - returns who won
	 * 0 = nobody won, 1 = player won, 2 = player 2 won
	 */
	public int scanGrid() {
		return winner;
	}
	
	/*
	 * getPriority1 - takes in a player. If there exists a position that 
	 * allows the player to win in one step, return such position.
	 */
	public int[] getPriority1(int player) {
		if (player != 1 && player != 2) {
			throw new IllegalArgumentException("Invalid player input.");
		}
		int[] priority = new int[2];
		for (int i=0; i<grid.length; i++) {
			if (row[player-1][i] == 2 && row[2-player][i] == 0) {
				for (int j=0; j<grid[0].length;j++) {
					if (grid[i][j] == 0) {
						priority[0] = i;
						priority[1] = j;
						return priority;
					}
				}
			}
		}
		
		for (int j=0; j<grid[0].length; j++) {
			if (column[player-1][j] == 2 && column[2-player][j] == 0) {
				for (int i=0; i<grid.length;i++) {
					if (grid[i][j] == 0) {
						priority[0] = i;
						priority[1] = j;
						return priority;
					}
				}
			}
		}
		
		if (diagonal[player-1][0] == 2 && diagonal[2-player][0] == 0) {
			for (int i=0; i<grid.length;i++) {
				if (grid[i][i] == 0) {
					priority[0] = i;
					priority[1] = i;
					return priority;
				}
			}
		}
		
		if (diagonal[player-1][1] == 2 && diagonal[2-player][1] == 0) {
			for (int i=0; i<grid.length;i++) {
				if (grid[i][2-i] == 0) {
					priority[0] = i;
					priority[1] = 2-i;
					return priority;
				}
			}
		}
		return null;
	}
	
	/*
	 * getPriority2A - if there exists a position that allows the player to 
	 * win in two step (a position that makes two Os or Xs in a row), return 
	 * such position. If there are multiple such positions, return the one 
	 * with the highest potential.
	 */
	public int[] getPriority2A(int player) {
		if (player != 1 && player != 2) {
			throw new IllegalArgumentException("Invalid player input.");
		}
		Random rand = new Random();
		int score = 0;
		int[] priority = null;
		for (int i=0; i<grid.length; i++) {
			if (row[player-1][i] == 1 && row[2-player][i] == 0) {
				int emptyCount = 0;
				int[] empty = new int[2];
				for (int j=0; j<grid[0].length;j++) {
					if (grid[i][j] == 0) {
						empty[emptyCount] = j;
						emptyCount++;
					}
				}
				priority = new int[2];
				priority[0] = i;
				if (potential[player-1][i][empty[0]] > score) {
					priority[1] = empty[0];
					score = potential[player-1][i][empty[0]];
				}
				if (potential[player-1][i][empty[1]] > score) {
					priority[1] = empty[1];
					score = potential[player-1][i][empty[1]];
				} 
				if (potential[player-1][i][empty[1]] == score) {
					if (rand.nextInt(2) == 1) {
						priority[1] = empty[1];
						score = potential[player-1][i][empty[1]];
					}
				}
				break;
			}
		}
		
		for (int j=0; j<grid[0].length; j++) {
			if (column[player-1][j] == 1 && column[2-player][j] == 0) {
				int emptyCount = 0;
				int[] empty = new int[2];
				for (int i=0; i<grid.length;i++) {
					if (grid[i][j] == 0) {
						empty[emptyCount] = i;
						emptyCount++;
					}
				}
				if (priority == null) {
					priority = new int[2];
				}
				if (potential[player-1][empty[0]][j] > score) {
					priority[1] = j;
					priority[0] = empty[0];
					score = potential[player-1][empty[0]][j];
				} 
				if (potential[player-1][empty[0]][j] == score) {
					if (rand.nextInt(2) == 1) {
						priority[1] = j;
						priority[0] = empty[0];
						score = potential[player-1][empty[0]][j];
					}
				}
				if (potential[player-1][empty[1]][j] > score) {
					priority[1] = j;
					priority[0] = empty[1];
					score = potential[player-1][empty[1]][j];
				} 
				if (potential[player-1][empty[1]][j] == score) {
					if (rand.nextInt(2) == 1) {
						priority[1] = j;
						priority[0] = empty[1];
						score = potential[player-1][empty[1]][j];
					}
				}
				break;
			}
		}
		
		if (diagonal[player-1][0] == 1 && diagonal[2-player][0] == 0) {
			int emptyCount = 0;
			int[] empty = new int[2];
			for (int i=0; i<grid.length;i++) {
				if (grid[i][i] == 0) {
					empty[emptyCount] = i;
					emptyCount++;
				}
			}
			if (priority == null) {
				priority = new int[2];
			}
			if (potential[player-1][empty[0]][empty[0]] > score) {
				priority[0] = empty[0];
				priority[1] = empty[0];
				score = potential[player-1][empty[0]][empty[0]];
			} 
			if (potential[player-1][empty[0]][empty[0]] == score) {
				if (rand.nextInt(2) == 1) {
					priority[0] = empty[0];
					priority[1] = empty[0];
					score = potential[player-1][empty[0]][empty[0]];
				}
			}
			if (potential[player-1][empty[1]][empty[1]] > score) {
				priority[0] = empty[1];
				priority[1] = empty[1];
				score = potential[player-1][empty[1]][empty[1]];
			} 
			if (potential[player-1][empty[1]][empty[1]] == score) {
				if (rand.nextInt(2) == 1) {
					priority[0] = empty[1];
					priority[1] = empty[1];
					score = potential[player-1][empty[1]][empty[1]];
				}
			}
		}
		
		if (diagonal[player-1][1] == 1 && diagonal[2-player][1] == 0) {
			int emptyCount = 0;
			int[] empty = new int[2];
			for (int i=0; i<grid.length;i++) {
				if (grid[i][2-i] == 0) {
					empty[emptyCount] = i;
					emptyCount++;
				}
			}
			if (priority == null) {
				priority = new int[2];
			}
			if (potential[player-1][empty[0]][2-empty[0]] > score) {
				priority[0] = empty[0];
				priority[1] = 2-empty[0];
				score = potential[player-1][empty[0]][2-empty[0]];
			} 
			if (potential[player-1][empty[0]][2-empty[0]] == score) {
				if (rand.nextInt(2) == 1) {
					priority[0] = empty[0];
					priority[1] = 2-empty[0];
					score = potential[player-1][empty[0]][2-empty[0]];
				}
			}
			if (potential[player-1][empty[1]][2-empty[1]] > score) {
				priority[0] = empty[1];
				priority[1] = 2-empty[1];
				score = potential[player-1][empty[1]][2-empty[1]];
			} 
			if (potential[player-1][empty[1]][2-empty[1]] == score) {
				if (rand.nextInt(2) == 1) {
					priority[0] = empty[1];
					priority[1] = 2-empty[1];
					score = potential[player-1][empty[1]][2-empty[1]];
				}
			}
		}
		return priority;
	}
	
	/*
	 * getPriority2D - if there exists a position that allows the player 
	 * to win in two step (a position that makes two Os or Xs in a row), 
	 * return such position. If there are multiple such positions, return 
	 * the one that forces the player's opponent to put in a position
	 * with the lowest potential.
	 */
	public int[] getPriority2D(int player) {
		if (player != 1 && player != 2) {
			throw new IllegalArgumentException("Invalid player input.");
		}
		Random rand = new Random();
		int score = 5;
		int[] priority = null;
		for (int i=0; i<grid.length; i++) {
			if (row[player-1][i] == 1 && row[2-player][i] == 0) {
				int emptyCount = 0;
				int[] empty = new int[2];
				for (int j=0; j<grid[0].length;j++) {
					if (grid[i][j] == 0) {
						empty[emptyCount] = j;
						emptyCount++;
					}
				}
				priority = new int[2];
				priority[0] = i;
				if (potential[2-player][i][empty[0]] < score) {
					priority[1] = empty[1];
					score = potential[2-player][i][empty[0]];
				}
				if (potential[2-player][i][empty[1]] < score) {
					priority[1] = empty[0];
					score = potential[2-player][i][empty[1]];
				} 
				if (potential[2-player][i][empty[1]] == score) {
					if (rand.nextInt(2) == 1) {
						priority[1] = empty[0];
						score = potential[2-player][i][empty[1]];
					}
				}
				break;
			}
		}
		
		for (int j=0; j<grid[0].length; j++) {
			if (column[player-1][j] == 1 && column[2-player][j] == 0) {
				int emptyCount = 0;
				int[] empty = new int[2];
				for (int i=0; i<grid.length;i++) {
					if (grid[i][j] == 0) {
						empty[emptyCount] = i;
						emptyCount++;
					}
				}
				if (priority == null) {
					priority = new int[2];
				}
				if (potential[2-player][empty[0]][j] < score) {
					priority[1] = j;
					priority[0] = empty[1];
					score = potential[2-player][empty[0]][j];
				} 
				if (potential[2-player][empty[0]][j] == score) {
					if (rand.nextInt(2) == 1) {
						priority[1] = j;
						priority[0] = empty[1];
						score = potential[2-player][empty[0]][j];
					}
				}
				if (potential[2-player][empty[1]][j] < score) {
					priority[1] = j;
					priority[0] = empty[0];
					score = potential[2-player][empty[1]][j];
				} 
				if (potential[2-player][empty[1]][j] == score) {
					if (rand.nextInt(2) == 1) {
						priority[1] = j;
						priority[0] = empty[0];
						score = potential[2-player][empty[1]][j];
					}
				}
				break;
			}
		}
		
		if (diagonal[player-1][0] == 1 && diagonal[2-player][0] == 0) {
			int emptyCount = 0;
			int[] empty = new int[2];
			for (int i=0; i<grid.length;i++) {
				if (grid[i][i] == 0) {
					empty[emptyCount] = i;
					emptyCount++;
				}
			}
			if (priority == null) {
				priority = new int[2];
			}
			if (potential[2-player][empty[0]][empty[0]] < score) {
				priority[0] = empty[1];
				priority[1] = empty[1];
				score = potential[2-player][empty[0]][empty[0]];
			} 
			if (potential[2-player][empty[0]][empty[0]] == score) {
				if (rand.nextInt(2) == 1) {
					priority[0] = empty[1];
					priority[1] = empty[1];
					score = potential[2-player][empty[0]][empty[0]];
				}
			}
			if (potential[2-player][empty[1]][empty[1]] < score) {
				priority[0] = empty[0];
				priority[1] = empty[0];
				score = potential[2-player][empty[1]][empty[1]];
			} 
			if (potential[2-player][empty[1]][empty[1]] == score) {
				if (rand.nextInt(2) == 1) {
					priority[0] = empty[0];
					priority[1] = empty[0];
					score = potential[2-player][empty[1]][empty[1]];
				}
			}
		}
		
		if (diagonal[player-1][1] == 1 && diagonal[2-player][1] == 0) {
			int emptyCount = 0;
			int[] empty = new int[2];
			for (int i=0; i<grid.length;i++) {
				if (grid[i][2-i] == 0) {
					empty[emptyCount] = i;
					emptyCount++;
				}
			}
			if (priority == null) {
				priority = new int[2];
			}
			if (potential[2-player][empty[0]][2-empty[0]] < score) {
				priority[0] = empty[1];
				priority[1] = 2-empty[1];
				score = potential[2-player][empty[0]][2-empty[0]];
			} 
			if (potential[2-player][empty[0]][2-empty[0]] == score) {
				if (rand.nextInt(2) == 1) {
					priority[0] = empty[1];
					priority[1] = 2-empty[1];
					score = potential[2-player][empty[0]][2-empty[0]];
				}
			}
			if (potential[2-player][empty[1]][2-empty[1]] < score) {
				priority[0] = empty[0];
				priority[1] = 2-empty[0];
				score = potential[2-player][empty[1]][2-empty[1]];
			} 
			if (potential[2-player][empty[1]][2-empty[1]] == score) {
				if (rand.nextInt(2) == 1) {
					priority[0] = empty[0];
					priority[1] = 2-empty[0];
					score = potential[2-player][empty[1]][2-empty[1]];
				}
			}
		}
		return priority;
	}
	
	/*
	 * getPriority3 - returns an empty position with the highest potential score
	 */
	public int[] getPriority3(int player) {
		if (player != 1 && player != 2) {
			throw new IllegalArgumentException("Invalid player input.");
		}
		int score = 0;
		int[] priority = null;
		for (int i=0; i<grid.length; i++) {
			for (int j=0; j<grid[0].length; j++) {
				if (grid[i][j] == 0) {
					if (priority == null) {
						priority = new int[2];
						score = potential[player-1][i][j];
						priority[0] = i;
						priority[1] = j;
						continue;
					}
					if (potential[player-1][i][j] == score) {
						Random rand = new Random();
						int choice = rand.nextInt(2);
						if (choice == 1) {
							score = potential[player-1][i][j];
							priority[0] = i;
							priority[1] = j;
						}
						continue;
					}
					if (potential[player-1][i][j] > score) {
						score = potential[player-1][i][j];
						priority[0] = i;
						priority[1] = j;
					}
				}
			}
		}
		return priority;
	}
	
	/*
	 * printGrid - prints the current game board
	 */
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
