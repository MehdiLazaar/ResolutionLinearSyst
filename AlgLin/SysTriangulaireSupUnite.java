package AlgLin;

public class SysTriangulaireSupUnite extends SysTriangSup {

    public SysTriangulaireSupUnite(Matrice matriceSystem, Vecteur secondMembre) throws IrregularSysLinException {
        super(matriceSystem, secondMembre);
    }

    protected boolean estTriangSupUnite(Matrice matrice) {
        if (!this.estTriangSup(matrice)) return false;
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

        // Vérifier si la matrice est triangulaire supérieure
        if (!estTriangSupUnite(matriceSystem)) {
            throw new IrregularSysLinException("La matrice système n'est pas triangulaire supérieure unité.");
        }
        return super.resolution();
    }

    public static void main(String[] args) throws Exception {


        // Créer la matrice et le vecteur du système
        double matrice[][] = {{1, 2, 3}, {0, 1, 4}, {0, 0, 1}};
        Matrice a = new Matrice(matrice);
        double vecteur[] = {6, 7, 8};
        Vecteur b = new Vecteur(vecteur);

        // Résoudre le système
        SysTriangulaireSupUnite systeme = new SysTriangulaireSupUnite(a, b);
        Vecteur solution = systeme.resolution();

        System.out.println("Matrice A :\n" + a);
        System.out.println("Vecteur B :\n" + b);
        System.out.println("Resolution du système TriangSup Ax=B est: \n" + systeme.resolution());

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
System.out.println("Ax="+ax);
        // Vérifier si la norme est négligeable
        if (normeL2 < Matrice.EPSILON && normeL1 < Matrice.EPSILON && normeLinfini < Matrice.EPSILON) {
            System.out.println("La solution est précise avec une norme inférieure à epsilon .");
        } else {
            System.out.println("La solution n'est pas suffisamment précise.");
        }
    }
}
