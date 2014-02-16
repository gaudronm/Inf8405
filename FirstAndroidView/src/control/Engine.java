package control;

import java.util.List;

import stockage.Coordinate;
import stockage.Table;

public class Engine {

	public static final int nbColors = 5;
	Table table;
	List<Coordinate> liste;

	public Engine() {
		this.table = new Table();
	}

	public void initPart() {
		boolean change, sansSolution;
		int c;
		sansSolution = true;
		while (sansSolution) {
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					c = genColAl();
					change = true;
					while (change) {
						change = false;
						if (i >= 2) {
							if ((table.getColor(i - 2, j) == table.getColor(
									i - 1, j)) && c == table.getColor(i - 1, j)) {
								c = genColAl();
							}
						}
						if (j >= 2) {
							if ((table.getColor(i, j - 2) == table.getColor(i,
									j - 1)) && c == table.getColor(i, j - 1)) {
								c = genColAl();
								change = true;
							}
						}
					}
					table.setColor(i, j, c);
				}
			}
			sansSolution = !hasSolution();
		}
	}

	public int playMove(int x, int y, int i, int j) {
		int color, score;
		int scoretemp = 0;
		if (!validMove(x, y, i, j))
			return 0;
		color = table.getColor(x, y);
		table.setColor(x, y, table.getColor(i, j));
		table.setColor(i, j, color);
		score = breakComb(x, y, table.getColor(x, y));
		score = score + breakComb(i, j, color);
		while (score != scoretemp) {
			score = scoretemp;
			regeneration();
			score += searchNewComb();
		}
		return score;
	}

	private int searchNewComb() {
		int score = 0;
		Coordinate coord;
		while (!liste.isEmpty()) {
			coord = liste.remove(0);  //peut etre cause de bug : indice 0
			score += breakComb(coord.getX(), coord.getY(), table.getColor(coord.getX(), coord.getY()));
		}
		return score;
	}

	private void regeneration() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (table.getColor(i, j) == 0) {
					int k = 1;
					while (i - k >= 0) {
						table.setColor(i - k + 1, j, table.getColor(i - k, j));
						liste.add(new Coordinate(i - k + 1, j));
						k++;
					}
					table.setColor(0, j, genColAl());
					liste.add(new Coordinate(0, j));
				}
			}
		}
	}

	private int breakComb(int x, int y, int color) {
		int i;
		int score = 300;
		switch (combination(x, y, color)) {
		case 1:
			table.setColor(x, y, 0);
			table.setColor(x - 1, y, 0);
			table.setColor(x - 2, y, 0);
			i = -3;
			while ((x + i >= 0) && (table.getColor(x + i, y) == color)) {
				table.setColor(x + i, y, color);
				score += 100;
				i--;
			}
			i = 1;
			while ((x + i <= 7) && (table.getColor(x + i, y) == color)) {
				table.setColor(x + i, y, color);
				score += 100;
				i++;
			}
			return score;
		case 2:
			table.setColor(x, y, 0);
			table.setColor(x - 1, y, 0);
			table.setColor(x + 1, y, 0);
			i = 2;
			while ((x + i <= 7) && (table.getColor(x + i, y) == color)) {
				table.setColor(x + i, y, color);
				score += 100;
				i++;
			}
			return score;
		case 3:
			table.setColor(x, y, 0);
			table.setColor(x + 1, y, 0);
			table.setColor(x + 2, y, 0);
			i = 3;
			while ((x + i <= 7) && (table.getColor(x + i, y) == color)) {
				table.setColor(x + i, y, color);
				score += 100;
				i++;
			}
			return score;
		case 4:
			table.setColor(x, y, 0);
			table.setColor(x, y - 1, 0);
			table.setColor(x, y - 2, 0);
			i = -3;
			while ((y + i >= 0) && (table.getColor(x, y + i) == color)) {
				table.setColor(x, y + i, color);
				score += 100;
				i--;
			}
			i = 1;
			while ((y + i <= 7) && (table.getColor(x, y + i) == color)) {
				table.setColor(x, y + i, color);
				score += 100;
				i++;
			}
			return score;
		case 5:
			table.setColor(x, y, 0);
			table.setColor(x, y - 1, 0);
			table.setColor(x, y - 2, 0);
			i = 2;
			while ((y + i <= 7) && (table.getColor(x, y + i) == color)) {
				table.setColor(x, y + i, color);
				score += 100;
				i++;
			}
			return score;
		case 6:
			table.setColor(x, y, 0);
			table.setColor(x, y - 1, 0);
			table.setColor(x, y - 2, 0);
			i = 3;
			while ((y + i <= 7) && (table.getColor(x, y + i) == color)) {
				table.setColor(x, y + i, color);
				score += 100;
				i++;
			}
			return score;
		}

		return 0;
	}

	private boolean hasSolution() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (i >= 2) {
					if (table.getColor(i - 2, j) == table.getColor(i - 1, j)) {
						if (((i < 7) && (table.getColor(i + 1, j) == table
								.getColor(i - 1, j)))
								|| ((j > 0) && (table.getColor(i, j - 1) == table
										.getColor(i - 1, j)))
								|| ((j < 7) && (table.getColor(i, j + 1) == table
										.getColor(i - 1, j)))) {
							return true;
						}
					}
					if (table.getColor(i - 2, j) == table.getColor(i, j)) {
						if (((j > 0) && (table.getColor(i - 1, j - 1) == table
								.getColor(i, j)))
								|| ((j < 7) && (table.getColor(i - 1, j + 1) == table
										.getColor(i, j)))) {
							return true;
						}
					}
					if (table.getColor(i, j) == table.getColor(i - 1, j)) {
						if (((i > 2) && (table.getColor(i - 3, j) == table
								.getColor(i, j)))
								|| ((j > 0) && (table.getColor(i - 2, j - 1) == table
										.getColor(i, j)))
								|| ((j < 7) && (table.getColor(i - 2, j + 1) == table
										.getColor(i, j)))) {
							return true;
						}
					}
				}
				if (j >= 2) {
					if (table.getColor(i, j - 2) == table.getColor(i, j - 1)) {
						if (((j < 7) && (table.getColor(i, j + 1) == table
								.getColor(i, j - 1)))
								|| ((i > 0) && (table.getColor(i - 1, j) == table
										.getColor(i, j - 1)))
								|| ((i < 7) && (table.getColor(i + 1, j) == table
										.getColor(i, j - 1)))) {
							return true;
						}
					}
					if (table.getColor(i, j - 2) == table.getColor(i, j)) {
						if (((i > 0) && (table.getColor(i - 1, j - 1) == table
								.getColor(i, j)))
								|| ((i < 7) && (table.getColor(i + 1, j - 1) == table
										.getColor(i, j)))) {
							return true;
						}
					}
					if (table.getColor(i, j) == table.getColor(i, j - 1)) {
						if (((j > 2) && (table.getColor(i, j - 3) == table
								.getColor(i, j)))
								|| ((i > 0) && (table.getColor(i - 1, j - 2) == table
										.getColor(i, j)))
								|| ((i < 7) && (table.getColor(i + 1, j - 2) == table
										.getColor(i, j)))) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	private int genColAl() {
		return (int) Math.random() * nbColors + 1;
	}

	private boolean validMove(int x, int y, int i, int j) {
		if (((x != i) && (y != j))
				|| ((Math.abs(x - i) != 1) && (Math.abs(y - j) != 1))) {
			return false;
		}
		if ((combination(x, y, table.getColor(i, j)) != 0)
				|| (combination(i, j, table.getColor(x, y))) != 0) {
			return true;
		}
		return false;
	}

	private int combination(int x, int y, int color) {
		if ((x - 1 >= 0) && (table.getColor(x - 1, y) == color)) {
			if ((x - 2 >= 0) && (table.getColor(x - 2, y) == color)) {
				return 1;
			} else if ((x + 1 <= 7) && (table.getColor(x + 1, y) == color)) {
				return 2;
			}
		}
		if ((x + 1 <= 7) && (table.getColor(x + 1, y) == color)) {
			if ((x + 2 <= 7) && (table.getColor(x + 2, y) == color)) {
				return 3;
			}
		}
		if ((y - 1 >= 0) && (table.getColor(x, y - 1) == color)) {
			if ((y - 2 >= 0) && (table.getColor(x, y - 2) == color)) {
				return 4;
			} else if ((y + 1 <= 7) && (table.getColor(x, y + 1) == color)) {
				return 5;
			}
		}
		if ((y + 1 <= 7) && (table.getColor(x, y + 1) == color)) {
			if ((y + 2 <= 7) && (table.getColor(x, y + 2) == color)) {
				return 6;
			}
		}
		return 0;
	}

}
