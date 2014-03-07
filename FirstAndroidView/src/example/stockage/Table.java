package example.stockage;

public class Table {

	int table[][];

	public Table() {
		this.table = new int[8][8];
	}

	public int getColor(int x,int y) {
		return table[x][y];
	}

	public void setColor(int x, int y, int c) {
		this.table[x][y] = c;
	}
	
	
	
	
}
