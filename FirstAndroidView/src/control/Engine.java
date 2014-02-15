package control;

import stockage.Table;

public class Engine {

	public static final int nbColors = 5;
	Table table;

	public Engine() {
		this.table = new Table();
	}

	public void InitPart() {
		boolean change, sansSolution;
		int c;
		sansSolution = true;
		while (sansSolution) {
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					c = GenColAl();
					change = true;
					while (change) {
						change = false;
						if (i >= 2) {
							if ((table.getColor(i - 2, j) == table.getColor(i - 1, j))
									&& c == table.getColor(i - 1, j)) {
								c = GenColAl();
							}
						}
						if (j >= 2) {
							if ((table.getColor(i, j - 2) == table.getColor(i, j - 1))
									&& c == table.getColor(i, j - 1)) {
								c = GenColAl();
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

	private boolean hasSolution() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (i >= 2) {
					if (table.getColor(i-2,j) == table.getColor(i-1,j)) {
						if (((i < 7) && (table.getColor(i+1,j) == table.getColor(i-1,j)))
								|| ((j > 0) && (table.getColor(i,j-1) == table.getColor(i-1,j)))
								|| ((j < 7) && (table.getColor(i,j+1) == table.getColor(i-1,j)))) {
							return true;
						}
					}
					if (table.getColor(i-2,j) == table.getColor(i,j)) {
						if (((j > 0) && (table.getColor(i-1,j-1) == table.getColor(i,j)))
								|| ((j < 7) && (table.getColor(i-1,j+1) == table.getColor(i,j)))) {
							return true;
						}
					}
					if (table.getColor(i,j) == table.getColor(i-1,j)) {
						if (((i > 2) && (table.getColor(i-3,j) == table.getColor(i,j)))
								|| ((j > 0) && (table.getColor(i-2,j-1) == table.getColor(i,j)))
								|| ((j < 7) && (table.getColor(i-2,j+1) == table.getColor(i,j)))) {
							return true;
						}
					}
				}
				if (j >= 2) {
					if (table.getColor(i,j-2) == table.getColor(i,j-1)) {
						if (((j < 7) && (table.getColor(i,j+1) == table.getColor(i,j-1)))
								|| ((i > 0) && (table.getColor(i-1,j) == table.getColor(i,j-1)))
								|| ((i < 7) && (table.getColor(i+1,j) == table.getColor(i,j-1)))) {
							return true;
						}
					}
					if (table.getColor(i,j-2) == table.getColor(i,j)) {
						if (((i > 0) && (table.getColor(i-1,j-1) == table.getColor(i,j)))
								|| ((i < 7) && (table.getColor(i+1,j-1) == table.getColor(i,j)))) {
							return true;
						}
					}
					if (table.getColor(i,j) == table.getColor(i,j-1)) {
						if (((j > 2) && (table.getColor(i,j-3) == table.getColor(i,j)))
								|| ((i > 0) && (table.getColor(i-1,j-2) == table.getColor(i,j)))
								|| ((i < 7) && (table.getColor(i+1,j-2) == table.getColor(i,j)))) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	private int GenColAl() {		
		return (int)Math.random()*nbColors;
	}

}
