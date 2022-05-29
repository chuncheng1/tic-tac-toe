/*
 * Author: Chun Cheng
 * 
 * The main class
 * The program is run from here. 
 */

import java.util.*;

public class TicTacToe {

		public static void main(String[] args) {

			Scanner scan = new Scanner(System.in);

			while (true) { 
				System.out.println();
				System.out.println("Welcome to TicTacToe!");
				System.out.println("What would you like to do?");
				System.out.println("(1) Start New Game (Easy Mode)");
				System.out.println("(2) Start New Game (Hard Mode)");
				System.out.println("(3) Start New Game (PvP Mode)");
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
						int r;
						int c;
						if (whoFirst == 1) {
							System.out.println("I'll go first");
							position = (rand.nextInt(5)+1)*2-1;
							r = 2 - (position-1)/3;
							c = (position-1)%3;
							grid.place(2, r, c);
							count++;
						} else {
							System.out.println("You're first.");
						}
						while (grid.scanGrid() == 0 && count<9) {
							do {
								grid.printGrid();
								System.out.print("Enter your choice: ");
								position = scan.nextInt();
								r = 2 - (position-1)/3;
								c = (position-1)%3;
							} while (! grid.place(1, r, c));
							count++;
							
							if (grid.scanGrid() == 1) {
								grid.printGrid();
								System.out.println("You won!");
								continue;
							}
							if (count == 9) {
								break;
							}
							
							if (! Player.priorityCheck(grid, 2)) {
								boolean placeResult;
								int i=0;
								do {
									position = rand.nextInt(9)+1;
									r = 2 - (position-1)/3;
									c = (position-1)%3;
									placeResult = grid.place(2, r, c);
									i++;
								} while (! placeResult && i<9);
							}
							count++;
							
							if (grid.scanGrid() == 2) {
								grid.printGrid();
								System.out.println("You lost.");
								continue;
							}
						}
						
						if (grid.scanGrid() == 0) {
							grid.printGrid();
							System.out.println("It's a tie!");
						}
						continue;
						
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
						int r;
						int c;
						if (whoFirst == 1) {
							System.out.println("I'll go first");
							Player.placeChoice(grid, 2, whoFirst);
							count++;
						} else {
							System.out.println("You're first.");
						}
						while (grid.scanGrid() == 0 && count<9) {
							do {
								grid.printGrid();
								System.out.print("Enter your choice: ");
								position = scan.nextInt();
								r = 2 - (position-1)/3;
								c = (position-1)%3;
							} while (! grid.place(1, r, c));
							count++;
							
							if (grid.scanGrid() == 1) {
								grid.printGrid();
								System.out.println("You won!");
								continue;
							}
							if (count == 9) {
								break;
							}
							
							Player.placeChoice(grid, 2, whoFirst);
							count++;
							
							if (grid.scanGrid() == 2) {
								grid.printGrid();
								System.out.println("You lost.");
								continue;
							}
						}
						
						if (grid.scanGrid() == 0) {
							grid.printGrid();
							System.out.println("It's a tie!");	
						}
						continue;
					} else if (choice == 3) {
						System.out.println();
						System.out.println("Please indicate the position you choose according to this number pad: ");
						System.out.println("7 8 9");
						System.out.println("4 5 6");
						System.out.println("1 2 3");
						System.out.println("O = player 1, X = player 2");
						Grid grid = new Grid();
						int count = 0;
						Random rand = new Random();
						int whoFirst = rand.nextInt(2);
						int position;
						int r;
						int c;
						if (whoFirst == 1) {
							System.out.println("Player 2 is first.");
							do {
								grid.printGrid();
								System.out.print("Player 2's turn. Enter your choice: ");
								position = scan.nextInt();
								r = 2 - (position-1)/3;
								c = (position-1)%3;
							} while (! grid.place(2, r, c));
							count++;
						} else {
							System.out.println("Player 1 is first.");
						}
						while (grid.scanGrid() == 0 && count<9) {
							do {
								grid.printGrid();
								System.out.print("Player 1's turn. Enter your choice: ");
								position = scan.nextInt();
								r = 2 - (position-1)/3;
								c = (position-1)%3;
							} while (! grid.place(1, r, c));
							count++;
							
							if (grid.scanGrid() == 1) {
								grid.printGrid();
								System.out.println("Player 1 won!");
								continue;
							}
							
							if (count == 9) {
								break;
							}
							
							do {
								grid.printGrid();
								System.out.print("Player 2's turn. Enter your choice: ");
								position = scan.nextInt();
								r = 2 - (position-1)/3;
								c = (position-1)%3;
							} while (! grid.place(2, r, c));
							count++;
							
							if (grid.scanGrid() == 2) {
								grid.printGrid();
								System.out.println("Player 2 won!");
								continue;
							}
						}
						
						if (grid.scanGrid() == 0) {
							grid.printGrid();
							System.out.println("It's a tie!");
						}
						continue;
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
