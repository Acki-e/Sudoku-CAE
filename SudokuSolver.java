package sudoku;

public interface SudokuSolver {
	/**
	 * Solves the sudoku.
	 * 
	 * @return true if the sudoku is solveable
	 */
	boolean solve();

	/**
	 * Puts digit in the box row, col.
	 * 
	 * @param row   The row
	 * @param col   The column
	 * @param digit The digit to insert in box row, col
	 * @throws IllegalArgumentException if row, col or digit is outside the range
	 *                                  [0..9]
	 */
	void set(int row, int col, int digit);

	/**
	 * Gets value of the box row, col.
	 * 
	 * @param row The row
	 * @param col The column
	 * @throws IllegalArgumentException if row, col or digit is outside the range
	 * @return The value of the specified box [0..9]
	 */
	int get(int row, int col);

	/**
	 * Empties the box row, col.
	 * 
	 * @param row   The row
	 * @param col   The column
	 * @param digit The digit to insert in box row, col
	 * @throws IllegalArgumentException if row, col or digit is outside the range
	 *                                  [0..9]
	 */
	void clear(int row, int col);

	/**
	 * Clears all digits in the sudoku.
	 */
	void clearAll();

	/**
	 * Checks that the digit in the box row, col follows the sudoku rules.
	 * 
	 * @param row The row
	 * @param col The column
	 * @throws IllegalArgumentException if row, col or digit is outside the range
	 *                                  [0..9]
	 * @return true if the digit in the box row, col follows the sudoku rules.
	 */
	boolean isValid(int row, int col);

	/**
	 * Checks if all filled in boxes follow the sudoku rules.
	 * 
	 * @return true if all filled in boxes follow the sudoku rules.
	 */
	boolean isAllValid();

	/**
	 * Fills the grid with the digits in m. The digit 0 represents an empty box.
	 * 
	 * @param m the matrix with the digits to insert
	 * @throws IllegalArgumentException if m has the wrong dimension or contains
	 *                                  values outside the range [0..9]
	 */
	void setGrid(int[][] m);

	/**
	 * Gets the current state of the grid.
	 * 
	 * @return a matrix of int that represents the grid
	 */
	int[][] getGrid();
}