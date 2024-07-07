package AlgLin;

public class Thomas extends SysLin {

    public Thomas(Matrice a, Vecteur b) throws IrregularSysLinException {
        super(a, b);        
    }

    public Thomas(Mat3Diag matriceSystem, Vecteur secondMembre) throws IrregularSysLinException {
        super(matriceSystem, secondMembre);
    }

    protected static boolean estTriDiag(Matrice matrice) {
        int n = matrice.nbLigne();

        // Vérifier les éléments en dehors des diagonales principale, supérieure et inférieure
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j && i != j + 1 && i != j - 1 && matrice.getCoef(i, j) != 0) {
                    return false;
                }
            }
        }

        return true;
    }


    public Vecteur resolution() throws IrregularSysLinException {

    	int n = this.matriceSystem.nbColonne();

		double[] p = new double[n];

		double[] q = new double[n];

		double[] x = new double[n];

		p[0] = -this.matriceSystem.getCoef(0, 0) / this.matriceSystem.getCoef(1, 0);

		q[0] =  this.secondMembre.getCoeffecient(0) / this.matriceSystem.getCoef(1, 0);

		for (int k = 1; k < n - 1; k++) {

		double beta_k = this.matriceSystem.getCoef(1, k) + this.matriceSystem.getCoef(0, k - 1) * p[k - 1];

		if (beta_k == 0) {

		throw new IrregularSysLinException("Le système est irrégulier : β est égal à zéro.");

		}

		p[k] = -this.matriceSystem.getCoef(2, k) / beta_k;

		q[k] = ( this.secondMembre.getCoeffecient(k) - this.matriceSystem.getCoef(0, k - 1) * q[k - 1]) / beta_k;

		}

		double beta_n = this.matriceSystem.getCoef(1, n - 1) + this.matriceSystem.getCoef(0, n - 2) * p[n - 2];

		if (beta_n == 0) {

		throw new IrregularSysLinException("Le système est irrégulier : β est égal à zéro.");

		}

		x[n - 1] = ( this.secondMembre.getCoeffecient(n - 1) - this.matriceSystem.getCoef(0, n - 2) * q[n - 2]) / beta_n;
		
		for (int k = n - 2; k >= 0; k--) {

		x[k] = p[k] * x[k + 1] + q[k];

		

		}

		return new Vecteur(x);

		}
    public static void main(String[] args) throws Exception {
        // Créer la matrice et le vecteur du système
        double matrice[][] = { { -1,-1, -1,0}, {2,2,2,2}, {-1, -1, -1, 0}};
        double vecteur[] = {-2,-2,-2,23};
        Vecteur b = new Vecteur(vecteur);
        Mat3Diag a = new Mat3Diag(matrice);
        
        // Résoudre le système
        Thomas systeme = new Thomas(a, b);
        Vecteur solution = systeme.resolution();

        System.out.println("matrice A :\n" + a);
        System.out.println("Vecteur B :\n" + b);
        System.out.println("Resolution du systeme TriangSup Ax=B est: \n" + solution);
        
        // Calculer Ax - b
        Vecteur ax = Mat3Diag.produit(a, solution);
        
        // Initialize the difference vector with the correct size
        int n = b.taille();
        Vecteur difference = new Vecteur(n);

        for (int i = 0; i < n; i++) {
            difference.remplaceCoef(i, ax.getCoeffecient(i) - b.getCoeffecient(i));
        }

        // Calculer la norme de la différence
       
        NormeGenerale laNorme1=new Norme_1();
        NormeGenerale laNormeInf=new Norme_inf();

        double normeA_1=laNorme1.norme(difference);
		double normeA_inf=laNormeInf.norme(difference);
        // Vérifier si la norme est négligeable
        if (normeA_1 < Matrice.EPSILON && normeA_inf < Matrice.EPSILON) {
            System.out.println("La solution est précise avec une norme inférieure à epsilon .");
        } else {
            System.out.println("La solution n'est pas suffisamment précise.");
        }

    }

}