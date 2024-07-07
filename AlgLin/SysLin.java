package AlgLin;

public abstract class SysLin {
    private int ordre;
    protected Matrice matriceSystem;
    protected Vecteur secondMembre;

    public SysLin(Matrice A, Vecteur b) throws IrregularSysLinException {
       // if (A.nbColonne() == A.nbLigne() && A.nbLigne() == b.taille()) {
            ordre = b.nbLigne();
            this.matriceSystem = A;
            this.secondMembre = b;
        //}
       /* else {
            throw new IrregularSysLinException("la matrice doit etre carre et de meme taille que le second membre");
    }*/
        }

    
    public int getOrdre() {
        return ordre;
    }

    public Matrice getMatriceSystem() {
        return matriceSystem;
    }

    public Vecteur getSecondMembre() {
        return secondMembre;
    }
    public Vecteur produit(Matrice matrice, Vecteur vecteur) throws Exception {
        int taille = matrice.nbLigne();
        if (taille != vecteur.taille()) {
            throw new Exception("La taille de la matrice et du vecteur ne correspond pas.");
        }
        
        double[] resultats = new double[taille];
        
        for (int i = 0; i < taille; i++) {
            double somme = 0;
            for (int j = 0; j < taille; j++) {
                somme += matrice.getCoef(i, j) * vecteur.getCoeffecient(j);
            }
            resultats[i] = somme;
        }
        
        return new Vecteur(resultats);
    }

  
    public abstract Vecteur resolution() throws IrregularSysLinException;
}
