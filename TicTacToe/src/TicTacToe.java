/*
	 * The main class, which represents the MovieTicketSystem that users will interact with
	 * The program is run from here. 
	 */

import java.util.Scanner;
import java.util.Random;

public class TicTacToe {

		public static void main(String[] args) {

			Scanner scan = new Scanner(System.in);

			while (true) { 
				System.out.println();
				System.out.println("Welcome to TicTacToe!");
				System.out.println();
				System.out.println("What would you like to do?");
				System.out.println("(1) Start New Game"); 
				System.out.println("(0) Quit");
				
				try {
					int choice = scan.nextInt();
					if (choice == 0) {
						//Quit
						System.out.println("Goodbye!");
						break;
					} else if (choice == 1) {
						System.out.println("Please indicate the position you choose according to this number pad: ");
						System.out.println("7 | 8 | 9");
						System.out.println("4 | 5 | 6");
						System.out.println("1 | 2 | 3");
						Grid grid = new Grid();
						int count = 0;
						Random rand = new Random();
						int whoFirst = rand.nextInt(2);
						int position;
						if (whoFirst == 1) {
							position = rand.nextInt(9)+1;
							grid.place(2, position);
							count++;
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
						continue;
					} else {
						System.out.println("You have entered an invalid input");
						continue;
					}
				} catch (Exception e) {
					System.out.println("You have entered an invalid input");
					System.out.println(e);
					continue;
				}

			}
			scan.close();

	}

}
