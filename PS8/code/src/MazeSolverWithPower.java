
import java.util.*;

public class MazeSolverWithPower implements IMazeSolverWithPower {
	private Maze maze;
	private Map<Integer, Set<Coordinate>> steps;

	public MazeSolverWithPower() {
		// TODO: Initialize variables.
		steps = new HashMap<>();
	}

	@Override
	public void initialize(Maze maze) {
		// TODO: Initialize the solver.
		this.maze = maze;
	}

	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		// TODO: Find shortest path.
		if (maze == null) {
			throw new Exception("Oh no! You cannot call me without initializing the maze!");
		}

		if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() ||
				endRow < 0 || endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns()) {
			throw new IllegalArgumentException("Invalid start/end coordinate");
		}

		reset();

		Queue<Coordinate> queue = new LinkedList<>();
		boolean[][][] visited = new boolean[maze.getRows()][maze.getColumns()][1];
		Coordinate start = new Coordinate(startCol, startRow, maze.getRoom(startRow, startCol), 0);
		Coordinate end = null;
		visited[start.y][start.x][0] = true;
		queue.add(start);
		updateStep(start);
		addStep(start);

		if (startRow == endRow && startCol == endCol) {
			end = start;
		}

		while (!queue.isEmpty()) {
			Coordinate curr = queue.poll();
			Room room = curr.room;
			int[][] possibleCoords = {{curr.x + 1, curr.y, room.hasEastWall() ? curr.powerUsed + 1 : curr.powerUsed},
						{curr.x - 1, curr.y, room.hasWestWall() ? curr.powerUsed + 1 : curr.powerUsed},
					    {curr.x, curr.y + 1, room.hasSouthWall() ? curr.powerUsed + 1 : curr.powerUsed},
						{curr.x, curr.y - 1, room.hasNorthWall() ? curr.powerUsed + 1 : curr.powerUsed}};

			for (int[] possible: possibleCoords) {
				int row = possible[1];
				int col = possible[0];
				int powerUsed = possible[2];
				if (canGo(possible, 0)) {
					if (!visited[row][col][powerUsed]) {
						Coordinate next = new Coordinate(col, row, maze.getRoom(row, col), powerUsed);
						next.parent = curr;
						visited[row][col][powerUsed] = true;
						queue.add(next);
						updateStep(next);
						addStep(next);
						if (row == endRow && col == endCol &&
								(end == null || shortestPath(start, next) < shortestPath(start, end))) {
							end = next;
						}
					}
				}
			}
		}
		createPath(start, end);

		return shortestPath(start, end);
	}



	private void reset() {
		steps.clear();
		for (int row = 0; row < maze.getRows(); row++) {
			for (int col = 0; col < maze.getColumns(); col++) {
				maze.getRoom(row, col).onPath = false;
			}
		}
	}

	private void updateStep(Coordinate c) {
		c.step = c.parent == null ? 0 : c.parent.step + 1;
	}

	private void addStep(Coordinate c) {
		if (!steps.containsKey(c.step)) {
			Set<Coordinate> xs = new HashSet<>();
			xs.add(c);
			steps.put(c.step, xs);
		} else {
			steps.get(c.step).add(c);
		}
	}

	private boolean canGo(int[] arr, int superpowers) {
		int col = arr[0];
		int row = arr[1];
		int powerUsed = arr[2];
		return row >= 0 && row < maze.getRows()
						&& col >= 0 && col < maze.getColumns()
						&& powerUsed <= superpowers;
	}

	private Integer shortestPath(Coordinate start, Coordinate end) {
		if (end == null) {
			return null;
		}
		int result = 0;
		while (end != start) {
			result++;
			end = end.parent;
		}
		return result;
	}

	private void createPath(Coordinate start, Coordinate end) {
		if (end == null) {
			return;
		}
		maze.getRoom(start.y, start.x).onPath = true;
		while (end != start) {
			maze.getRoom(start.y, start.x).onPath = true;
			end = end.parent;
		}
	}

	@Override
	public Integer numReachable(int k) throws Exception {
		// TODO: Find number of reachable rooms.
		return steps.get(k) == null ? 0 : steps.get(k).size();
	}

	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow,
							  int endCol, int superpowers) throws Exception {
		// TODO: Find shortest path with powers allowed.
		if (maze == null) {
			throw new Exception("Oh no! You cannot call me without initializing the maze!");
		}

		if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() ||
				endRow < 0 || endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns()) {
			throw new IllegalArgumentException("Invalid start/end coordinate");
		}

		reset();

		Queue<Coordinate> queue = new LinkedList<>();
		boolean[][][] visited = new boolean[maze.getRows()][maze.getColumns()][superpowers + 1];
		boolean[][] step = new boolean[maze.getRows()][maze.getColumns()];
		Coordinate start = new Coordinate(startCol, startRow, maze.getRoom(startRow, startCol), 0);
		Coordinate end = null;
		visited[start.y][start.x][0] = true;
		step[start.y][start.x] = true;
		queue.add(start);
		updateStep(start);
		addStep(start);

		if (startRow == endRow && startCol == endCol)
			end = start;

		while (!queue.isEmpty()) {
			Coordinate curr = queue.poll();
			Room room = curr.room;
			int[][] possibleCoords = {{curr.x + 1, curr.y, room.hasEastWall() ? curr.powerUsed + 1 : curr.powerUsed},
					{curr.x - 1, curr.y, room.hasWestWall() ? curr.powerUsed + 1 : curr.powerUsed},
					{curr.x, curr.y + 1, room.hasSouthWall() ? curr.powerUsed + 1 : curr.powerUsed},
					{curr.x, curr.y - 1, room.hasNorthWall() ? curr.powerUsed + 1 : curr.powerUsed}};
			for (int[] possible : possibleCoords) {
				int row = possible[1];
				int col = possible[0];
				int powerUsed = possible[2];
				if (canGo(possible, superpowers)) {
					if (!visited[row][col][powerUsed]) {
						Coordinate next = new Coordinate(col, row,
								maze.getRoom(row, col), powerUsed);
						next.parent = curr;
						visited[row][col][powerUsed] = true;
						queue.add(next);
						updateStep(next);
						if (!step[row][col]) {
							step[row][col] = true;
							addStep(next);
						}
						if (row == endRow && col == endCol &&
								(end == null || shortestPath(start, next) < shortestPath(start, end)))
							end = next;
					}
				}
			}
		}
		createPath(start, end);
		return shortestPath(start, end);
	}


	public static void main(String[] args) {
		try {
			Maze maze = Maze.readMaze("code/maze-sample.txt");
			IMazeSolverWithPower solver = new MazeSolverWithPower();
			solver.initialize(maze);

			System.out.println(solver.pathSearch(0, 0, 4, 1, 2));
			MazePrinter.printMaze(maze);

			for (int i = 0; i <= 9; ++i) {
				System.out.println("Steps " + i + " Rooms: " + solver.numReachable(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
