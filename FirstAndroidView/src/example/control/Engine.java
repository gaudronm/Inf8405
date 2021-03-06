package example.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import example.stockage.Coordinate;
import example.stockage.Table;

public class Engine {

	public static final int nbColors = 5;
	Table table;
	List<Coordinate> liste;

	public Engine() {
		this.table = new Table();
	}

	public Table getTable(){
		return this.table;
	}
	/**
	 * Permet d'initialiser une partie. On parcourt la table et pour chaque case on
	 * genere une couleur aleatoirement. On verfie egalement pour chaque case le pouvant
	 * si elle ne "fini" pas une combinaison de 3.  
	 */
	public void initPart() {
		boolean change, sansSolution;
		int c;
		sansSolution = true;
		while (sansSolution) {
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					c = genColAl();
					System.out.println("couleur " + c);
					change = true;
					/* si on change la couleur dans la boucle while, il faut tester la nouvelle
					 couleur. Pour ce faire, on passe le bool change a true pour refaire
					 un passage de boucle.*/
					while (change) {
						change = false;
						if (i >= 2) {
							if ((table.getColor(i - 2, j) == table.getColor(
									i - 1, j)) && c == table.getColor(i - 1, j)) {
								c = genColAl();
								change = true;
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

	/**
	 * Va permettre de jouer un coup si celui ci est valide et de comptabiliser
	 * toutes les combinaisons creee lors de la regeneration de la table.
	 * @param x abscisse de la premiere case a changer
	 * @param y ordonee de la premiere case a changer
	 * @param i abscisse de la deuxieme case a changer
	 * @param j ordonnee de la deuxieme case a changer
	 * @return le score fait avec ce coup.
	 */
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
			scoretemp = score;
			regeneration();
			score += searchNewComb();
		}
		return score;
	}

	/**
	 * Deroule la liste des modifications pour voir si elles provoquent de nouvelles combinaions.
	 * @return le score fait par ces possibles combinaisons.
	 */
	private int searchNewComb() {
		int score = 0;
		int color;
		Coordinate coord;
		while (!liste.isEmpty()) {
			coord = liste.remove(0);  //peut etre cause de bug : indice 0
			color = table.getColor(coord.getX(), coord.getY());
			if (color != 0) {
				score += breakComb(coord.getX(), coord.getY(), color);
			}
		}
		return score;
	}

	/**
	 * Fait "tomber" les gemmes et en genere de nouvelles
	 */
	private void regeneration() {
		liste = new ArrayList<Coordinate>();
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

	/**
	 * Fait appel a la methode combination pour connaitre le type de combinaison
	 * puis selon le type, cherche a voir si d'autre cases sont impliquees
	 * dans la combinaison.
	 * @param x abscisse de la case a tester
	 * @param y ordonne de la case a tester
	 * @param color couleur avec laquelle on souhaite tester la case
	 * @return le score de la combinaison
	 */
	private int breakComb(int x, int y, int color) {
		int i;
		int score = 100;
		switch (combination(x, y, color)) {
		case 1:
			//Les 2 cases précédentes en x sont de la même couleur.
			table.setColor(x, y, 0);
			table.setColor(x - 1, y, 0);
			table.setColor(x - 2, y, 0);
			i = -3;
			//Il faut tester si les cases encore précédentes sont également de la même couleur.
			while ((x + i >= 0) && (table.getColor(x + i, y) == color)) {
				table.setColor(x + i, y, 0);
				score += 50;
				i--;
			}
			i = 1;
			//Idem pour les cases directement après
			while ((x + i <= 7) && (table.getColor(x + i, y) == color)) {
				table.setColor(x + i, y, 0);
				score += 50;
				i++;
			}
			return score;
		case 2:
			//La case précédente et la suivante en x sont de la même couleur.
			table.setColor(x, y, 0);
			table.setColor(x - 1, y, 0);
			table.setColor(x + 1, y, 0);
			i = 2;
			//Par construction, les cases encore précédentes ne sont pas de la même couleur.
			//On ne test alors que les cases suivantes
			while ((x + i <= 7) && (table.getColor(x + i, y) == color)) {
				table.setColor(x + i, y, 0);
				score += 50;
				i++;
			}
			return score;
		case 3:
			//Les 2 cases suivantes en x son de la même couleur.
			table.setColor(x, y, 0);
			table.setColor(x + 1, y, 0);
			table.setColor(x + 2, y, 0);
			i = 3;
			//Idem, on ne test que les suivantes 
			while ((x + i <= 7) && (table.getColor(x + i, y) == color)) {
				table.setColor(x + i, y, 0);
				score += 50;
				i++;
			}
			return score;
			
			//Idem en y.
		case 4:
			table.setColor(x, y, 0);
			table.setColor(x, y - 1, 0);
			table.setColor(x, y - 2, 0);
			i = -3;
			while ((y + i >= 0) && (table.getColor(x, y + i) == color)) {
				table.setColor(x, y + i, 0);
				score += 50;
				i--;
			}
			i = 1;
			while ((y + i <= 7) && (table.getColor(x, y + i) == color)) {
				table.setColor(x, y + i, 0);
				score += 50;
				i++;
			}
			return score;
		case 5:
			table.setColor(x, y, 0);
			table.setColor(x, y - 1, 0);
			table.setColor(x, y + 1, 0);
			i = 2;
			while ((y + i <= 7) && (table.getColor(x, y + i) == color)) {
				table.setColor(x, y + i, 0);
				score += 50;
				i++;
			}
			return score;
		case 6:
			table.setColor(x, y, 0);
			table.setColor(x, y + 1, 0);
			table.setColor(x, y + 2, 0);
			i = 3;
			while ((y + i <= 7) && (table.getColor(x, y + i) == color)) {
				table.setColor(x, y + i, 0);
				score += 50;
				i++;
			}
			return score;
		}

		return 0;
	}

	/**
	 * Test les differentes solutions possibles pour chaque case.
	 * S'arrete a la premiere solution trouvee.
	 * @return true si une solution a ete trouvee
	 */
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

	/**
	 * 
	 * @return un nombre entre 1 et 5 correspondant a une couleur
	 */
	private int genColAl() {
		Random rand = new Random();
		int nombreAleatoire = rand.nextInt(5) + 1;
		return nombreAleatoire;
	}

	/**
	 * Test si un coup est valide. i.e. Les cases a echanger sont
	 * voisines et une combinaison est creee grace a ce coup.
	 * @param x abscisse de la premiere case a changer
	 * @param y ordonee de la premiere case a changer
	 * @param i abscisse de la deuxieme case a changer
	 * @param j ordonnee de la deuxieme case a changer
	 * @return true si le coup est valide
	 */
	public boolean validMove(int x, int y, int i, int j) {
		if (((x != i) && (y != j))
				|| ((Math.abs(x - i) != 1) && (Math.abs(y - j) != 1))) {
			return false;
		}
		int tempColor = table.getColor(i, j);
		table.setColor(i, j, table.getColor(x, y));
		table.setColor(x, y, tempColor);
		if ((combination(x, y, table.getColor(x, y)) != 0)
				|| (combination(i, j, table.getColor(i, j))) != 0) {
			table.setColor(x, y, table.getColor(i, j));
			table.setColor(i, j, tempColor);
			return true;
		}
		table.setColor(x, y, table.getColor(i, j));
		table.setColor(i, j, tempColor);
		return false;
	}

	/**
	 * Regarde si une combinaison est possible sur la case selectionnee
	 * @param x abscisse de la case
	 * @param y ordonne de la case
	 * @param color couleur avec laquelle on veut tester
	 * @return un entier correspondant a un type de combinaison ou
	 * 0 si aucune combinaison n'est possible
	 */
	private int combination(int x, int y, int color) {
		if ((x - 1 >= 0) && (table.getColor(x - 1, y) == color)) {
			if ((x - 2 >= 0) && (table.getColor(x - 2, y) == color)) {
				//les 2 cases précédentes en x (verticale) sont de la même couleur que la case en argument
				return 1;
			} else if ((x + 1 <= 7) && (table.getColor(x + 1, y) == color)) {
				//la case précédente et la suivante en x sont de la même couleur que la case en argument
				return 2;
			}
		}
		if ((x + 1 <= 7) && (table.getColor(x + 1, y) == color)) {
			if ((x + 2 <= 7) && (table.getColor(x + 2, y) == color)) {
				//les 2 cases suivantes en x sont de la même couleur que la case en argument
				return 3;
			}
		}
		if ((y - 1 >= 0) && (table.getColor(x, y - 1) == color)) {
			if ((y - 2 >= 0) && (table.getColor(x, y - 2) == color)) {
				//les 2 cases précédentes en y (horizontale) sont de la même couleur que la case en argument
				return 4;
			} else if ((y + 1 <= 7) && (table.getColor(x, y + 1) == color)) {
				//la case précédente et la suivante en y sont de la même couleur que la case en argument
				return 5;
			}
		}
		if ((y + 1 <= 7) && (table.getColor(x, y + 1) == color)) {
			if ((y + 2 <= 7) && (table.getColor(x, y + 2) == color)) {
				//les 2 cases suivantes en y sont de la même couleur que la case en argument
				return 6;
			}
		}
		return 0;
	}

}
