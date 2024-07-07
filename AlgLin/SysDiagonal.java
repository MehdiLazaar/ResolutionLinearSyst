package AlgLin;


public class SysDiagonal extends SysLin {

    public SysDiagonal(Matrice matriceSystem, Vecteur secondMembre) throws IrregularSysLinException {
        super(matriceSystem, secondMembre);
    }

   
    public boolean estDiagonale(Matrice matrice) {
        int lignes = matrice.nbLigne();
        int colonnes = matrice.nbColonne();
        
        // Vérifier si la matrice est carrée
        if (lignes != colonnes) {
            return false; // Une matrice diagonale doit être carrée
        }
        // Parcourir la matrice pour vérifier si les éléments hors de la diagonale sont nuls
        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < colonnes; j++) {
                if (i != j && matrice.getCoef(i, j) != 0) {
                    return false; // Les éléments hors de la diagonale ne doivent pas être nuls
                }
            }
        }
        
        return true;
    }

    @Override
    public Vecteur resolution() throws IrregularSysLinException {
        int n = getOrdre();
        Vecteur solution = new Vecteur(n);
        if (!estDiagonale(matriceSystem)) {
            throw new IrregularSysLinException("La matrice système n'est pas Diagonale");
        }
        for (int i = 0; i < n; i++) {
            double dii = getMatriceSystem().getCoef(i, i);
            if (dii == 0) {
                throw new IrregularSysLinException("Resolution impossible ,les coefficient de la diagonales doivent etre non nuls (division par zero impossible )");
            }
            solution.remplaceCoef(i, secondMembre.getCoeffecient(i) / dii);
        }

        return solution;
    }
    

    
    public static void main(String[] args) throws Exception {
        // Définir l'epsilon numérique

        // Créer la matrice et le vecteur du système
        double matrice[][] = {{1, 0, 0}, {0, 2, 0}, {0, 0, 3}};
        Matrice a = new Matrice(matrice);
        double vecteur[] = {1, 4, 9};
        Vecteur b = new Vecteur(vecteur);

      
        // Résoudre le système
        SysDiagonal systeme = new SysDiagonal(a, b);
        Vecteur solution = systeme.resolution();
        
        System.out.println("matrice A :\n"+a);
        System.out.println("Vecteur B :\n"+b);
        System.out.println("Resolution du systeme diagonale Ax=B est: \n"+systeme.resolution()) ;


        // Calculer Ax - b
        Vecteur ax = systeme.produit(a, solution);
        int n = systeme.getOrdre();
        Vecteur difference = new Vecteur(n);
       

        for (int i = 0; i < b.taille(); i++) {
            difference.remplaceCoef(i, ax.getCoeffecient(i) - b.getCoeffecient(i));
        }

        // Calculer la norme de la différence
        double normeL2 = difference.normeL2();
        double normeL1 = difference.normeL1();
        double normeLinfini = difference.normeLinfini();

        // Vérifier si la norme est négligeable
        if (normeL2 < Matrice.EPSILON && normeL1 < Matrice.EPSILON && normeLinfini < Matrice.EPSILON) {
            System.out.println("La solution est précise avec une norme inférieure à epsilon.");
        } else {
            System.out.println("La solution n'est pas suffisamment précise.");
        }
    }
}
