package AlgLin;

public class Norme_1 implements NormeGenerale{

	@Override
	public double norme(Matrice matrice) {
		double norme=0;
		int n=matrice.nbColonne();
		for(int i=0;i<n;i++) {
			int somme =0;
			for(int j=0;j<n;j++) {
				somme+= Math.abs(matrice.getCoef(j, i));
				
			}
			if (norme<somme) norme=somme; 
		}
		return norme;
	}

}
