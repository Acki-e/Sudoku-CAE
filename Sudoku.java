/**
 * 
 */
package sudoku;

public class Sudoku implements SudokuSolver {

	int[][] s;

	public Sudoku() {
		s = new int[9][9];
	}

	@Override
	public boolean solve() {
		if (isAllValid())
			return solve(0, 0);
		return false;
	}

	private boolean solve(int row, int column) {
		// basfall, metoden har gått igenom alla rader
		if (row == 9)
			return true;
		// ökar radnummret med 1 när metoden nått slutet av en rad
		else if (column == 9)
			return solve(row + 1, 0);
		// första loopen går igenom varje kolumn i raden
		for (int col = column; col < 9;) {
			// kolla att det inte redan finns ett insatt värde
			if (get(row, col) == 0) {
				// denna loop går igenom alla möjliga heltal som skulle kunna sättas in
				for (int i = 1; i <= 9; i++) {
					set(row, col, i);
					// ifall inga problem uppstår efter att värdet lagts till
					if (isValid(row, col)) {
						// fortsätt rektursivt till nästa kolumn (eller rad). När solve returnerar true
						// (basfallet) kommer alla tidigare rekursiva anrop även att returnera true
						if (solve(row, col + 1))
							return true;
					}
					// om värdet inte fungerar eller om efterföljande rekursiva anrop returnerar
					// false rensas rutans värde
					clear(row, col);
				}
			} else {
				// detta är ifall värdet är insatt av användaren. Det räcker då att testa ifall
				// värdet fungerar med den nuvarande försöksgrenen
				if (isValid(row, col)) {
					if (solve(row, col + 1))
						return true;
				}
			}

			// om inga värden 1-9 kan sättas in returneras false och metoden backtrackar
			return false;
		}
		// denna del borde aldrig nås, men kompilatorn blir arg annars
		return false;
	}

	@Override
	public void set(int row, int col, int digit) {
		s[row][col] = digit;

	}

	@Override
	public int get(int row, int col) {
		return s[row][col];
	}

	@Override
	public void clear(int row, int col) {
		s[row][col] = 0;

	}

	@Override
	public void clearAll() {
		s = new int[9][9];
	}

	@Override
	public boolean isValid(int row, int col) {
		int n = s[row][col];
		if (n == 0)
			return true;
		s[row][col] = -1;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				// detta testar om (i,j) är i samma rad, kolonn eller ruta som (row, col)
				if (((i == row || j == col || (i / 3 == row / 3 && j / 3 == col / 3))) && s[i][j] == n) {
					s[row][col] = n;
					return false;
				}
			}
		}
		s[row][col] = n;
		return true;
	}

	@Override
	public boolean isAllValid() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++)
				if (!isValid(i, j))
					return false;
		}
		return true;
	}

	@Override
	public void setGrid(int[][] m) {
		s = m;
	}

	@Override
	public int[][] getGrid() {
		return s;
	}

}
