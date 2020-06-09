public interface IMazeSolverWithPower extends IMazeSolver {
	/**
	 * Finds the shortest path from a given starting coordinate to an ending
	 * coordinate with a fixed usage limit of superpowers given.
	 *
	 * @param startRow the row index of the starting coordinate
	 * @param startCol the column index of the starting coordinate
	 * @param endRow the row index of the target coordinate
	 * @param endCol the column index of the target coordinate
	 * @param superpowers the usage limit of the superpower
	 * @return null if there is no path from start to end
	 * @throws Exception
	 */
	Integer pathSearch(int startRow, int startCol, int endRow, int endCol, int superpowers) throws Exception;
}
