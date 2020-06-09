import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Maze {
    private final static char WALL = '#';

	private int rows, columns; // size of the maze
	private Room[][] rooms;

	/**
	 * Creates an empty maze.
	 */
	private Maze() {
		rows = columns = 0;
		rooms = null;
	}

	/**
	 * Creates a maze consisting of the given grid of rooms.
	 */
	Maze(Room[][] rooms) {
		assert rooms.length > 0;

		this.rooms = rooms;
		rows = rooms.length * 2 + 1;
		columns = rooms[0].length * 2 + 1;
	}

	/**
	 * Retrieves a room given the row and column (both 0-indexed).
	 *
	 * @param row the row index of the room
	 * @param column the column index of the room
	 * @return room the room at the requested coordinate
	 */
	public Room getRoom(int row, int column) {
		if (row >= rows || column >= columns || row < 0 || column < 0) {
			throw new IllegalArgumentException();
		}

		return rooms[row][column];
	}

	/**
	 * @return the number of rows in the maze
	 */
	public int getRows() {
		return rows / 2;
	}

	/**
	 * @return the number of columns in the maze
	 */
	public int getColumns() {
		return columns / 2;
	}

	/**
	 * Reads in an ASCII description of a maze and returns the
	 * created maze object.
	 *
	 * @param fileName
	 * @return maze
	 * @throws IOException if the input format is invalid
	 */
	public static Maze readMaze(String fileName) throws IOException {
		FileReader fin = new FileReader(fileName);
		BufferedReader bin = new BufferedReader(fin);

		Maze maze = new Maze();

		List<String> input = new ArrayList<>();
		String line;
		while ((line = bin.readLine()) != null) {
			if (line.isEmpty()) {
				break; // end of input
			}
			if (maze.columns > 0 && line.length() != maze.columns) {
				throw new IOException("Invalid input format");
			}
			maze.columns = line.length();
			maze.rows++;
			input.add(line);
		}

		if (maze.rows % 2 == 0 || maze.columns % 2 == 0) {
			throw new IOException("Invalid input format");
		}

		maze.rooms = new Room[maze.rows / 2][maze.columns / 2];
		for (int i = 1; i < maze.rows - 1; i += 2) {
			for (int j = 1; j < maze.columns - 1; j += 2) {
				maze.rooms[i / 2][j / 2] = new Room(
						input.get(i - 1).charAt(j) == WALL, // north: i-1
						input.get(i + 1).charAt(j) == WALL, // south: i+1
						input.get(i).charAt(j + 1) == WALL, // east: j+1
						input.get(i).charAt(j - 1) == WALL  // west: j-1
				);
			}
		}

		assert (!bin.ready());
		bin.close();

		return maze;
	}
}
