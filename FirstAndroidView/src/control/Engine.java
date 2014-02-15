package control;

import stockage.Table;

public class Engine {

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
						if (i >= 3) {
							if ((table.getColor(i - 2, j) == table.getColor(
									i - 1, j)) && c == table.getColor(i - 1, j)) {
								c = GenColAl();
							}
						}
						if (j >= 3) {
							if ((table.getColor(i, j - 2) == table.getColor(i,
									j - 1)) && c == table.getColor(i, j - 1)) {
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
		// TODO Auto-generated method stub
		return false;
	}

	private int GenColAl() {
		// TODO Auto-generated method stub
		return 0;
	}

}
