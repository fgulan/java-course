package hr.fer.zemris.java.tecaj.hw4.grafika;

import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

/**
 * Razred Linija je dijete razred razreda GeometrijskiLik.
 * Svaka linija je definirana s koordinatom početne i krajnje točke.
 * 
 * <p>Razred Linija implementira svoju metodu popuniLik() koja je
 * učinkovitija od one dane u razredu GeometrijskiLik.
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class Linija extends GeometrijskiLik {
	
	/** X koordinata početne točke */
	private int x0;
	/** Y koordinata početne točke */
	private int y0;
	/** X koordinata završne točke */
	private int x1;
	/** Y koordinata završne točke */
	private int y1;
	/** Nagib pravca */
	private double nagibPravca;
	
	/**
	 * Konstruktor razreda Linija. Stvara geometrijski lik liniju izemđu
	 * dviju danih točaka.
	 * @param x0 X koordinata početne točke.
	 * @param y0 Y koordinata početne točke.
	 * @param x1 X koordinata završne točke.
	 * @param y1 Y koordinata završne točke.
	 */
	public Linija(int x0, int y0, int x1, int y1) {
		this.x0 = x0;
		this.y0 = y0;
		this.x1 = x1;
		this.y1 = y1;
		this.nagibPravca = ((double) y1 - y0)/(x1-x0);
	}
	
	/**
	 * Izračunava koordinatu na y-osi za danu vrijednost koordinate na x-osi
	 * za zadanu jednadžbu pravca.
	 * @param x X koordinata točke.
	 * @return Y koordinatu točke.
	 */
	private double jednadzbaY(int x) {
		return  nagibPravca*(x-x0)+y0;
	}
	
	/**
	 * Izračunava koordinatu na x-osi za danu vrijednost koordinate na y-osi
	 * za zadanu jednadžbu pravca.
	 * @param y Y koordinata točke.
	 * @return X koordinatu točke.
	 */
	private double jednadzbaX(int y) {
		return (y - y0 )/nagibPravca + x0;
	}
	
	/**
	 * Provjerava da li trenutna linija sadržava danu koordinatnu točku.
	 * @param x Kooridinatna točka na x-osi.
	 * @param y Kooridinatna točka na y-osi.
	 * @return Vraća <code>true</code> ukoliko trenutna linija sadržava danu
	 * točku, <code>false</code> inače.
	 */
	@Override
	public boolean sadrziTocku(int x, int y) {
		return y == (int) Math.round(jednadzbaY(x));
	}
	
	/**
	 * Popopunjava danu sliku s trenutnom linijom. Metoda nadjačava 
	 * metodu popuniLik() iz razreda geometrijski lik s učinkovitijim
	 * i bržim algoritmom za iscrtavanje linije.
	 * @param slika Slika na koju iscrtavamo trenutnu liniju.
	 */
	@Override
	public void popuniLik(Slika slika) {
		int yStart = Math.min(y0, y1);
		int yKraj = Math.min(Math.max(y0, y1), slika.getVisina()-1);
		int xStart = Math.min(x0, x1);
		int xKraj = Math.min(Math.max(x0, x1), slika.getSirina()-1);
		
		if(yKraj-yStart < xKraj-xStart) {
			int y;
			for (int x = xStart; x <= xKraj; x++) {
				y = (int) Math.round(jednadzbaY(x));
				if(x >= 0 && y >= 0 && y <= yKraj) {
					slika.upaliTocku(x, y);
				}
			}
		} else {
			int x;
			for (int y = yStart; y <= yKraj; y++) {
				x = (int) Math.round(jednadzbaX(y));
				if(y >= 0 && x >= 0 && x <= xKraj) {
					slika.upaliTocku(x, y);
				}
			}
		}
	}	
	
	/** Konstanta stvaratelja lika */
	public static final StvarateljLika STVARATELJ = new LinijaStvaratelj();
	
	/**
	 * Privatni razred koji implementira sučelje StvarateljLika.
	 * Dani razred stvara lik linije s imenom "LINIJA"
	 * i parametrima danim u metodi stvoriIzStringa();
	 * 
	 * @author Filip Gulan
	 * @version 1.0
	 * 
	 */
	private static class LinijaStvaratelj implements StvarateljLika {
		
		/**
		 * Vraća naziv geometrijskog lika linija kao "LINIJA".
		 * @return Niz znakova "LINIJA".
		 */
		@Override
		public String nazivLika() {
			return "LINIJA";
		}
		
		/**
		 * Stvara lik linije s danim arugumentima. Metoda kao argument prima
		 * string koji sadrži dvije koordinatne točke, početnom i završnom
		 * za liniju, npr. u obliku "20 30 80 90". Ukoliko točke prelaze dimenzije
		 * slike iscrtat će se samo onaj dio koji bi bio vidljiv na dijelu slike od
		 * točke (0, 0) do točke (sirina-1, visina -1).
		 * @param parametri Početna i završna točka linije.
		 * @return Stvoreni geometrijski lik razreda Linija.
		 * @throws IllegalArgumentException Ukoliko su ulazni parametri neispravni.
		 */
		public GeometrijskiLik stvoriIzStringa(String parametri) {
			ParserArgumenata parser = new ParserArgumenata(parametri);
			if(parser.brojArgumenata() != 4) {
				throw new IllegalArgumentException("Neispravan broj argumenata za lik linije!");
			}
			int[] arg = parser.getPoljeArgumenata();
			return new Linija(arg[0], arg[1], arg[2], arg[3]);
		}
	}

}
