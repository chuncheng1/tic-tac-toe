/*
 * Author: Chun Cheng
 * 
 * Player - provide algorithms for choosing where to place
 */

public abstract class Player {
	/*
	 * priorityCheck - used by computer player. Uses the following strategy:
	 * if there exist a position that allows computer player win in one step,
	 * place X at such position and return true. Else if there exist a position
	 *  that allows human player win in one step, place X at such position and 
	 *  return true. Otherwise, return false.
	 */
	public static boolean priorityCheck(Grid grid, int player) {
		int[] priority = grid.getPriority1(player);
		if (priority == null) {
			priority = grid.getPriority1(3-player);
		}
		if (priority == null) {
			return false;
		} else {
			grid.place(player, priority[0], priority[1]);
			return true;
		}
	}
	
	public static boolean placeChoice(Grid grid, int player, int whoFirst) {
		if (! priorityCheck(grid, player)) {
			int[] priority;
			if (whoFirst+1 == player) {
				priority = grid.getPriority2A(player);
				if (priority == null) {
					priority = grid.getPriority3(player);
				}
			} else {
				priority = grid.getPriority2D(player);
				if (priority == null) {
					priority = grid.getPriority3(3-player);
				}
			}
			
			if (priority == null) {
				return false;
			} else {
				grid.place(player, priority[0], priority[1]);
			}
		}
		return true;
	}
}
