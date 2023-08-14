package sudoku;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class SudokuView {
	private Sudoku s;
	private int size;
	// used to make borders work in SudokuGrid constructor
	// index [0] represents large grid width, the other two are regular grid width
	private static int[] border = { 2, 1, 1 };
	private SudokuSquare[][] grid;

	public SudokuView(Sudoku s) {
		this.s = s;
		size = 50;
		SwingUtilities.invokeLater(() -> createWindow("Sudoku"));
	}

	private void createWindow(String title) {
		JFrame frame = new JFrame(title);
		Container pane = frame.getContentPane();
		Container board = new Container();
		grid = new SudokuSquare[9][9];
		board.setPreferredSize(new Dimension(size * 9, size * 9));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		Font font = new Font("Serif", Font.PLAIN, 2 * size / 3);
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				SudokuSquare g = new SudokuSquare(i, j, s);
				grid[i][j] = g;
				g.text.setFont(font);
				g.text.setBounds(j * size, i * size, size, size);
				board.add(g.text);
			}
		}

		JPanel buttons = new JPanel();
		JButton clear = new JButton("Clear");
		JButton solve = new JButton("Solve");
		clear.addActionListener(e -> clearAll());
		solve.addActionListener(e -> solve());
		buttons.add(clear);
		buttons.add(solve);

		pane.add(board);
		pane.add(buttons, BorderLayout.SOUTH);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
	}

	public void drawBoard(int[][] values) {
		Color bg = new Color(240, 240, 240);
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				JTextField text = grid[i][j].text;
				if (values[i][j] != 0)
					text.setText(String.valueOf(values[i][j]));
				else
					text.setText("");
				if (s.isValid(i, j))
					text.setBackground(bg);
				else
					text.setBackground(Color.RED);
			}
		}
	}

	public void drawSolution(int[][] values) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				JTextField text = grid[i][j].text;
				if (values[i][j] != 0)
					text.setText(String.valueOf(values[i][j]));
				else {
					text.setText("");
					text.setBackground(Color.RED);
				}
			}
		}
	}

	public void clearAll() {
		s.clearAll();
		drawBoard(s.getGrid());
	}

	public void solve() {
		s.solve();
		drawSolution(s.getGrid());
	}

	private static class SudokuSquare {
		private int row;
		private int col;
		private JTextField text;

		public SudokuSquare(int row, int col, Sudoku s) {
			this.row = row;
			this.col = col;
			this.text = new JTextField();
			text.setEditable(false);
			text.setFocusable(true);
			text.setOpaque(true);
			text.setHorizontalAlignment(JLabel.CENTER);
			text.setBorder(BorderFactory.createMatteBorder(border[this.row % 3], border[this.col % 3],
					border[(this.row + 1) % 3], border[(this.col + 1) % 3], Color.BLACK));
			text.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					char ch = e.getKeyChar();
					if (ch >= '1' && ch <= '9') {
						text.setText(String.valueOf(ch));
						s.set(row, col, Integer.parseInt(String.valueOf(ch)));
						if (!s.isValid(row, col))
							text.setBackground(Color.ORANGE);
					} else if (ch == KeyEvent.VK_BACK_SPACE) {
						text.setText("");
						text.setBackground(Color.YELLOW);
						s.clear(row, col);
					}
				}
			});
			text.addFocusListener(new FocusListener() {
				@Override
				public void focusGained(FocusEvent e) {
					if (s.isValid(row, col))
						e.getComponent().setBackground(Color.YELLOW);
					else
						e.getComponent().setBackground(Color.ORANGE);
				}

				@Override
				public void focusLost(FocusEvent e) {
					if (s.isValid(row, col))
						e.getComponent().setBackground(new Color(240, 240, 240));
					else
						e.getComponent().setBackground(Color.RED);
				}
			});
		}
	}
}
