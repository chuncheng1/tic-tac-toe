/*
	 * The main class, which represents the MovieTicketSystem that users will interact with
	 * The program is run from here. 
	 */

import java.util.*;

public class TicTacToe {
	
		public static void placeChoice(Grid grid) {
			Random rand = new Random();
			int[] corners = {1, 3, 7, 9}; 
			int[] sides = {2, 4, 6, 8};
			int position = 5;
			boolean placeResult = grid.place(2, position);
			if (placeResult == true) {
				return;
			} else {
					int[] priority = grid.getPriority(2);
					if (priority != null) {
						grid.place(2, priority[0], priority[1]);
					} else {
						priority = grid.getPriority(1);
						if (priority != null) {
							grid.place(2, priority[0], priority[1]);
						} else {
							Set<Integer> hs = new HashSet<Integer>();
							int i=0;
							do {
								do {
									position = corners[rand.nextInt(4)];
								} while (! hs.add(position));
								placeResult = grid.place(2, position);
								i++;
							} while (! placeResult && i<corners.length);
							
							if (! placeResult) {
								i=0;
								do {
									do {
										position = sides[rand.nextInt(4)];
									} while (! hs.add(position));
									placeResult = grid.place(2, position);
									i++;
								} while (! placeResult && i<sides.length);
							}
						}
					}
			}
		}

		public static void main(String[] args) {

			Scanner scan = new Scanner(System.in);

			while (true) { 
				System.out.println();
				System.out.println("Welcome to TicTacToe!");
				System.out.println("What would you like to do?");
				System.out.println("(1) Start New Game (Easy Mode)");
				System.out.println("(2) Start New Game (Hard Mode)");
				System.out.println("(0) Quit");
				
				try {
					int choice = scan.nextInt();
					if (choice == 0) {
						//Quit
						System.out.println("Goodbye!");
						break;
					} else if (choice == 1) {
						System.out.println();
						System.out.println("Please indicate the position you choose according to this number pad: ");
						System.out.println("7 8 9");
						System.out.println("4 5 6");
						System.out.println("1 2 3");
						System.out.println("O = player, X = computer");
						Grid grid = new Grid();
						int count = 0;
						Random rand = new Random();
						int whoFirst = rand.nextInt(2);
						int position;
						if (whoFirst == 1) {
							System.out.println("I'll go first");
							position = rand.nextInt(9)+1;
							grid.place(2, position);
							count++;
						} else {
							System.out.println("You're first.");
						}
						while (grid.scanGrid() == 0 && count<9) {
							do {
								grid.printGrid();
								System.out.print("Enter your choice: ");
								position = scan.nextInt();
							} while (! grid.place(1, position));
							count++;
							
							if (grid.scanGrid() == 1) {
								grid.printGrid();
								System.out.println("You won!");
								continue;
							}
							
							do {
								position = rand.nextInt(9)+1;
							} while (! grid.place(2, position));
							count++;
							
							if (grid.scanGrid() == 2) {
								grid.printGrid();
								System.out.println("You lost.");
								continue;
							}
						}
						
						if (grid.scanGrid() == 0 && count >= 9) {
							grid.printGrid();
							System.out.println("It's a tie!");
							continue;
						}
						
					} else if (choice == 2) {
						System.out.println();
						System.out.println("Please indicate the position you choose according to this number pad: ");
						System.out.println("7 8 9");
						System.out.println("4 5 6");
						System.out.println("1 2 3");
						System.out.println("O = player, X = computer");
						Grid grid = new Grid();
						int count = 0;
						Random rand = new Random();
						int whoFirst = rand.nextInt(2);
						int position;
						if (whoFirst == 1) {
							System.out.println("I'll go first");
							placeChoice(grid);
							count++;
						} else {
							System.out.println("You're first.");
						}
						while (grid.scanGrid() == 0 && count<9) {
							do {
								grid.printGrid();
								System.out.print("Enter your choice: ");
								position = scan.nextInt();
							} while (! grid.place(1, position));
							count++;
							
							if (grid.scanGrid() == 1) {
								grid.printGrid();
								System.out.println("You won!");
								continue;
							}
							
							placeChoice(grid);
							count++;
							
							if (grid.scanGrid() == 2) {
								grid.printGrid();
								System.out.println("You lost.");
								continue;
							}
						}
						
						if (grid.scanGrid() == 0 && count >= 9) {
							grid.printGrid();
							System.out.println("It's a tie!");
							continue;
						}
					} else {
						System.out.println("You have entered an invalid input");
						continue;
					}
				} catch (Exception e) {
					System.out.println("You have entered an invalid input");
					continue;
				}

			}
			scan.close();

	}

}
