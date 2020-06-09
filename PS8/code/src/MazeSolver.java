import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MazeSolver implements IMazeSolver {

	private Maze maze;
	private boolean solved = false;
	private boolean[][] visited;
	private List<Integer> step;

	public MazeSolver() {
		// TODO: Initialize variables.
		solved = false;
		maze = null;
	}

	@Override
	public void initialize(Maze maze) {
		// TODO: Initialize the solver.
		this.maze = maze;
		visited = new boolean[maze.getRows()][maze.getColumns()];
		solved = false;
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

		solved = false;
		step = new ArrayList<>();

		// set all visited flag to false
		// before we begin our search
		for (int i = 0; i < maze.getRows(); ++i) {
			for (int j = 0; j < maze.getColumns(); ++j) {
				this.visited[i][j] = false;
				maze.getRoom(i, j).onPath = false;
			}
		}

		// this is the pair that will be replaced by the end point and used to trace back to the start point
		// via the parents to mark out the shortest path
		Pair posToMark = null;
		// this counter will increase indefinitely until pathSearch stops
		int counter = 0;
		// this result will be assigned to the value of counter the moment we have found the end point so to
		// capture the minimum no. of steps required to reach the end point
		int result = 0;
		// starting point, which will have its parent set as null
		Pair startPos = new Pair(startRow, startCol);
		startPos.setParent(null);
		Queue<Pair> currFrontier = new LinkedList<>();
		// adds starting point to frontier
		currFrontier.add(startPos);
		while (!currFrontier.isEmpty()) {
			// adds the number of rooms reachable by each frontier/step to a global arraylist for
			// numReachable method to query later on
			int numOfRooms = currFrontier.size();
			step.add(counter, numOfRooms);
			// initialisation of next frontier
			Queue<Pair> nextFrontier = new LinkedList<>();
			for (Pair pos : currFrontier) {

				// found end point
				if (pos.getX() == endRow && pos.getY() == endCol) {
					solved = true;
					posToMark = pos;
					result = counter;
				}

				// visit and mark each room as visited respectively, if room has no wall in that direction,
				// add the room in that direction to next frontier and mark that room as visited, set the parent of
				// that new room to the current room
				visited[pos.getX()][pos.getY()] = true;
				Room currRoom = maze.getRoom(pos.getX(), pos.getY());
				if (!currRoom.hasNorthWall() && !visited[pos.getX() - 1][pos.getY()]) {
					Pair northPos = new Pair(pos.getX() - 1, pos.getY());
					nextFrontier.add(northPos);
					northPos.setParent(pos);
					visited[northPos.getX()][northPos.getY()] = true;
				}
				if (!currRoom.hasSouthWall() && !visited[pos.getX() + 1][pos.getY()]) {
					Pair southPos = new Pair(pos.getX() + 1, pos.getY());
					nextFrontier.add(southPos);
					southPos.setParent(pos);
					visited[southPos.getX()][southPos.getY()] = true;
				}
				if (!currRoom.hasEastWall() && !visited[pos.getX()][pos.getY() + 1]) {
					Pair eastPos = new Pair(pos.getX(), pos.getY() + 1);
					nextFrontier.add(eastPos);
					eastPos.setParent(pos);
					visited[eastPos.getX()][eastPos.getY()] = true;
				}
				if (!currRoom.hasWestWall() && !visited[pos.getX()][pos.getY() - 1]) {
					Pair westPos = new Pair(pos.getX(), pos.getY() - 1);
					nextFrontier.add(westPos);
					westPos.setParent(pos);
					visited[westPos.getX()][westPos.getY()] = true;
				}
			}
			currFrontier = nextFrontier;
			counter++;
		}

		// if pathSearch manages to reach the end point, maze is solved and we can mark the shortest path by
		// backtracking from end point of maze to start point by recursing on the parent of each point
		if (solved) {
			maze.getRoom(startPos.getX(), startPos.getY()).onPath = true;
			while (posToMark.getParent() != null) {
				maze.getRoom(posToMark.getX(), posToMark.getY()).onPath = true;
				posToMark = posToMark.getParent();
			}
			return result;
		}
		// if end point isn't found, maze is unsolved and hence null is returned
		return null;
	}

	@Override
	public Integer numReachable(int k) throws Exception {
		// TODO: Find number of reachable rooms.
		// if k is transcends the boundaries of the global arraylist, each kth step will return 0 reachable rooms
		if (k > step.size() - 1) {
			return 0;
		}
		// otherwise, we will simply query from the arraylist
		return step.get(k);
	}

	public static void main(String[] args) {
		try {
			Maze maze = Maze.readMaze("C:\\Users\\kswk\\IdeaProjects\\ps8\\maze-empty.txt");
			IMazeSolver solver = new MazeSolver();
			solver.initialize(maze);

			System.out.println(solver.pathSearch(0, 0, 2, 3));
			MazePrinter.printMaze(maze);

			for (int i = 0; i <= 9; ++i) {
				System.out.println("Steps " + i + " Rooms: " + solver.numReachable(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
