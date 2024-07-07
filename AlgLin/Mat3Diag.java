package AlgLin;

public class Mat3Diag extends Matrice {

	public Mat3Diag(int dim1, int dim2) {
		super(dim1,dim2);
		if (dim1 != 3 ) {
            throw new IllegalArgumentException("Le tableau doit avoir 3 lignes.");
        }
	}
	
	public Mat3Diag(double tableau[][]) {
		super(tableau);
        if (tableau.length != 3 ) {
            throw new IllegalArgumentException("Le tableau doit avoir 3 lignes");
        }
      }
	public Mat3Diag(int dim) {
		 super(3,dim);
	 }
        	
        
	public String toString() {
	    int taille = this.coefficient[1].length;
	    StringBuilder matr = new StringBuilder();
	    for (int i = 0; i < taille; i++) {
	        for (int j = 0; j < taille; j++) {
	        	if(j!=0) matr.append(" ");
	            if (j == i) {
	                matr.append(this.getCoef(1, j));
	            } else if (j == i - 1 && i != 0) {
	                matr.append(this.getCoef(0, j));
	            } else if (j == i + 1 && i != taille - 1) {
	                matr.append(this.getCoef(2, j-1));
	            } else {
	                matr.append("0");
	            }
	        }
	        matr.append("\n");
	    }
	    return matr.toString();
	}


       
	
	 
	 
	public static Vecteur produit(Matrice matrice, Vecteur vecteur) throws Exception {
	    int taille = matrice.coefficient[1].length;
	    if (taille != vecteur.taille()) {
	        throw new Exception("La taille de la matrice et du vecteur ne correspondent pas.");
	    }

	    double[] resultats = new double[taille];

	    // Calcul du premier élément
	    resultats[0] = matrice.getCoef(1, 0) * vecteur.getCoeffecient(0) + 
	    				matrice.getCoef(2, 0) * vecteur.getCoeffecient(1);

	    // Calcul des éléments intermédiaires
	    for (int i = 1; i < taille - 1; i++) {
	        resultats[i] = matrice.getCoef(0, i - 1) * vecteur.getCoeffecient(i - 1) + 
	                       matrice.getCoef(1, i) * vecteur.getCoeffecient(i) + 
	                       matrice.getCoef(2, i) * vecteur.getCoeffecient(i + 1);
	    }

	    // Calcul du dernier élément
	    resultats[taille - 1] = matrice.getCoef(0, taille - 2) * vecteur.getCoeffecient(taille - 2) + 
	                            matrice.getCoef(1, taille - 1) * vecteur.getCoeffecient(taille - 1);

	    return new Vecteur(resultats);
	}

	
    
    // Autres méthodes spécifiques à la classe Mat3Diag si nécessaire

	public static void main(String[] args) throws Exception {
		double mat[][]= {{-1,-1,-1,0},{2,2,2,2},{-1,-1,-1,0}};
		Mat3Diag a = new Mat3Diag(mat);
		
		System.out.println("construction d'une matrice tridiagonale par affectation d'un tableau de 3 dimension(les trois diagonales):\n"+a);
		
        double[] vect = {1,2,3,4};
		Vecteur v=new Vecteur(vect);
        System.out.println("le produit de \n"+a+"et\n"+v);

		System.out.println("==  \n"+ produit(a,v) );
		
		
	}
}