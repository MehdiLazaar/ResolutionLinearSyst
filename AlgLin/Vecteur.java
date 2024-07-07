package AlgLin;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Vecteur extends Matrice {

    /** Définir ici les constructeurs de la classe **/
    Vecteur(int nbligne) {
        super(nbligne, 1);
    }

    Vecteur(double[] tableau) {
        super(new double[tableau.length][1]);
        for (int i = 0; i < tableau.length; i++) {
            this.coefficient[i][0] = tableau[i];
        }
    }

  
    Vecteur(String fichier) throws FileNotFoundException {
        super(0, 1);
        try {
            Scanner sc = new Scanner(new File(fichier));
            int ligne = sc.nextInt(); // Lire le nombre de lignes
            this.coefficient = new double[ligne][1]; // Initialiser le tableau de coefficients
            for (int i = 0; i < ligne; i++) {
                this.coefficient[i][0] = sc.nextDouble(); // Lire chaque coefficient
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Fichier absent");
        }
    }


    
    int taille() {
    	 return this.nbLigne();
    }
    
    public double getCoeffecient(int position) {
        return this.coefficient[position][0];
    }
    
    public void remplaceCoef(int position, double value) {
        this.coefficient[position][0] = value;
    }
    @Override
    public String toString() {
        StringBuilder vecteurString = new StringBuilder();
        int ligne = this.nbLigne();
        for (int i = 0; i < ligne; i++) {
            vecteurString.append(this.getCoeffecient(i));
         
                vecteurString.append("\n");
            
        }
        return vecteurString.toString();
    }
    public static double produitScalaire(Vecteur v1, Vecteur v2) throws Exception {
        if (v1.taille() != v2.taille()) {
            throw new Exception("Les deux vecteurs n'ont pas la même taille.");
        }

        double produit = 0;
        for (int i = 0; i < v1.taille(); i++) {
            produit += v1.getCoeffecient(i) * v2.getCoeffecient(i);
        }
        return produit;
    }
    public double normeL1() {
        double norme = 0;
        for (int i = 0; i < this.taille(); i++) {
            norme += Math.abs(this.getCoeffecient(i));
        }
        return norme;
    }

    public double normeL2() {
        double norme = 0;
        for (int i = 0; i < this.taille(); i++) {
            double coef = this.getCoeffecient(i);
            norme += coef * coef;
        }
        return Math.sqrt(norme);
    }

    public double normeLinfini() {
        double norme = 0;
        for (int i = 0; i < this.taille(); i++) {
            double coef = Math.abs(this.getCoeffecient(i));
            norme = Math.max(norme, coef);
        }
        return norme;
    }

    

    public static void main(String[] args) throws Exception {
        double[] vectArray = {1, 2, 3};
        Vecteur v1 = new Vecteur(vectArray);
        System.out.println("Vecteur créé à partir d'un tableau :\n" + v1);

        Vecteur v2 = new Vecteur("fichier.txt");
        System.out.println("Vecteur créé à partir d'un fichier :\n" + v2);

        Vecteur v3 = new Vecteur(3);
        v3.recopie(v1);
        System.out.println("Recopie du vecteur v1 :\n" + v3);

        System.out.println("Taille du vecteur v1 : " + v1.taille());
        System.out.println("Coefficient à la position 1 du vecteur v1 : " + v1.getCoeffecient(1));
        System.out.println("Remplacement du coefficient à la position 1 du vecteur v1 par 5");
        v1.remplaceCoef(1, 5);
        System.out.println("Nouveau vecteur v1 :\n" + v1);

        System.out.println("Produit_scalaire(v1,v2) = " + (int)produitScalaire(v1, v3));
    }
    
    
}
