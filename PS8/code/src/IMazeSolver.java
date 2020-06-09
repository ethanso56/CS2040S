public interface IMazeSolver {
	/**
	 * (Re-)Initializes the solver with a maze.
	 *
	 * @param maze the maze to initialize the solver with
	 */
	void initialize(Maze maze);

	/**
	 * Finds the shortest path from a given starting coordinate to an
	 * ending coordinate.
	 *
	 * @param startRow the row index of the starting coordinate
	 * @param startCol the column index of the starting coordinate
	 * @param endRow the row index of the target coordinate
	 * @param endCol the column index of the target coordinate
	 * @return null if there is no path from start to end
	 * @throws Exception
	 */
	Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception;

	/**
	 * Returns the number of rooms that require a minimum of exactly k steps to
     * reach it, from the starting coordinate as defined in the most recent
     * call to pathSearch.
	 *
	 * @param k the number of steps
	 * @return the number of rooms such that the minimum number of steps
     *         required to reach it is k
	 * @throws Exception
	 */
	Integer numReachable(int k) throws Exception;
}
