package AlgLin;

public class SysTriangInf extends SysLin {

    public SysTriangInf(Matrice matriceSystem, Vecteur secondMembre) throws IrregularSysLinException {
        super(matriceSystem, secondMembre);
       
    }

    protected boolean estTriangInf(Matrice matrice) {
        int n = matrice.nbLigne();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (matrice.getCoef(i, j) != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    
    @Override
    public Vecteur resolution() throws IrregularSysLinException {
        Matrice matriceSystem = getMatriceSystem();

        // Vérifier si la matrice est triangulaire inférieure
        if (!estTriangInf(matriceSystem)) {
            throw new IrregularSysLinException("La matrice système n'est pas triangulaire inférieure.");
        }

        int n = getOrdre();
        Vecteur solution = new Vecteur(n);

        for (int i = 0; i < n; i++) {
            double somme = 0;
            for (int j = 0; j < i; j++) {
                somme += matriceSystem.getCoef(i, j) * solution.getCoeffecient(j);
            }
            double diagonalValue = matriceSystem.getCoef(i, i);
            if (diagonalValue == 0) {
                throw new IrregularSysLinException("Resolution impossible (division par zero impossible )");
            }
            solution.remplaceCoef(i, (secondMembre.getCoeffecient(i) - somme) / diagonalValue);
        }

        return solution;
    }
    

    public static void main(String[] args) throws Exception {
      

        // Créer la matrice et le vecteur du système
        double matrice[][] = {{5, 0, 0}, {4, 2, 0}, {3, 2, 3}};
        Matrice a = new Matrice(matrice);
        double vecteur[] = {2, 4, 1};
        Vecteur b = new Vecteur(vecteur);

      
        // Résoudre le système
        SysTriangInf systeme = new SysTriangInf(a, b);
        Vecteur solution = systeme.resolution();
        
        System.out.println("matrice A :\n"+a);
        System.out.println("Vecteur B :\n"+b);
        System.out.println("Resolution du systeme TriangSup Ax=B est: \n"+systeme.resolution()) ;


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
            System.out.println("La solution est précise avec une norme inférieure à epsilon .");
        } else {
            System.out.println("La solution n'est pas suffisamment précise.");
        }
    }
}