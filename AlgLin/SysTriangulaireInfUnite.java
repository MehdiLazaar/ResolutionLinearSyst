package AlgLin;

public class SysTriangulaireInfUnite extends SysTriangInf {

    public SysTriangulaireInfUnite(Matrice matriceSystem, Vecteur secondMembre) throws IrregularSysLinException {
        super(matriceSystem, secondMembre);
    }

    protected boolean estTriangInfUnite(Matrice matrice) {
        if (!this.estTriangInf(matrice)) return false;
        else {
            int n = matrice.nbLigne();
            for (int i = 0; i < n; i++) {
                if (matrice.getCoef(i, i) != 1) {
                    return false;
                }
            }
            return true;
        }
    }

    @Override
    public Vecteur resolution() throws IrregularSysLinException {
        Matrice matriceSystem = getMatriceSystem();

        // Vérifier si la matrice est triangulaire inférieure
        if (!estTriangInfUnite(matriceSystem)) {
            throw new IrregularSysLinException("La matrice système n'est pas triangulaire inférieure unite.");
        }
        return super.resolution();
    }
    

    public static void main(String[] args) throws Exception {

        // Créer la matrice et le vecteur du système
        double matrice[][] = {{1, 0, 0}, {4, 1, 0}, {3, 2, 1}};
        Matrice a = new Matrice(matrice);
        double vecteur[] = {2, 4, 1};
        Vecteur b = new Vecteur(vecteur);


        // Résoudre le système
        SysTriangulaireInfUnite systeme = new SysTriangulaireInfUnite(a, b);
        Vecteur solution = systeme.resolution();

        System.out.println("matrice A :\n" + a);
        System.out.println("Vecteur B :\n" + b);
        System.out.println("Resolution du systeme TriangSup Ax=B est: \n" + systeme.resolution());


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
 //System.out.println("Ax ="+ax);
        // Vérifier si la norme est négligeable
        if (normeL2 < Matrice.EPSILON && normeL1 < Matrice.EPSILON && normeLinfini < Matrice.EPSILON) {
            System.out.println("La solution est précise avec une norme inférieure à epsilon .");
        } else {
            System.out.println("La solution n'est pas suffisamment précise.");
        }
    }
}
