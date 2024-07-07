package AlgLin;


import java.io.*;
import java.util.*;

public class Matrice {
    final static double EPSILON =1.0E-06;
    /** Définir ici les attributs de la classe **/
    protected double coefficient[][];

    /** Définir ici les constructeur de la classe **/
    Matrice (int nbligne, int nbcolonne){
        this.coefficient = new double[nbligne][nbcolonne];
    }

    Matrice(double[][] tableau){
        coefficient = tableau;
    }

    Matrice(String fichier){
        try {
            Scanner sc = new Scanner(new File(fichier));
            int ligne = sc.nextInt();
            int colonne = sc.nextInt();
            this.coefficient = new double[ligne][colonne];
            for(int i=0; i<ligne;i++)
                for(int j=0; j< colonne; j++)
                    this.coefficient[i][j]=sc.nextDouble();
            sc.close();

        }
        catch(FileNotFoundException e) {
            System.out.println("Fichier absent");
        }
    }
    /** Definir ici les autres methodes */

    public void recopie(Matrice arecopier){
        int ligne, colonne;
        ligne = arecopier.nbLigne(); colonne = arecopier.nbColonne();
        this.coefficient = new double[ligne][colonne];
        for(int i=0; i<ligne; i++)
            for (int j=0;j<colonne;j++)
                this.coefficient[i][j]= arecopier.coefficient[i][j];
    }

    public int nbLigne(){
        return this.coefficient.length;
    }

    public int nbColonne(){
        return this.coefficient[0].length;
    }

    public double getCoef(int ligne, int colonne){
        return this.coefficient[ligne][colonne];
    }

    public void remplacecoef(int ligne, int colonne, double value){
        this.coefficient[ligne][colonne]=value;
    }

    public String toString(){
        int ligne = this.nbLigne();
        int colonne = this.nbColonne();
        String matr = "";
        for(int i = 0; i<ligne;i++){
            for(int j =0; j< colonne;j++){
                if(j == 0)
                {
                    matr += this.getCoef(i, j);
                }
                else{
                    matr += " " + this.getCoef(i, j);
                }
            }
            matr += "\n";
        }
        return matr;
    }

    public Matrice produit(double scalaire){
        int ligne = this.nbLigne();
        int colonne = this.nbColonne();
        for(int i=0; i<ligne;i++)
            for(int j=0; j< colonne; j++)
                this.coefficient[i][j]*=scalaire;
        return this;
    }

    static Matrice addition(Matrice a, Matrice b){
        int ligne = a.nbLigne();
        int colonne = a.nbColonne();
        Matrice mat = new Matrice(ligne, colonne);
        for(int i=0; i<ligne;i++)
            for(int j=0; j< colonne; j++)
                mat.coefficient[i][j]=a.coefficient[i][j] + b.coefficient[i][j];
        return mat;
    }

    static Matrice verif_addition(Matrice a, Matrice b) throws Exception{
        if((a.nbLigne() == b.nbLigne()) && (a.nbColonne() == b.nbColonne()))
        {
            int ligne = a.nbLigne();
            int colonne = a.nbColonne();
            Matrice mat = new Matrice(ligne, colonne);
            for(int i=0; i<ligne;i++)
                for(int j=0; j< colonne; j++)
                    mat.coefficient[i][j]=a.coefficient[i][j] + b.coefficient[i][j];
            return mat;
        }
        else {
            throw new Exception("Les deux matrices n'ont pas les mêmes dimensions !!!");
        }
    }

    static Matrice produit(Matrice a, Matrice b){
        int ligne, colonne;
        ligne = a.nbLigne();
        colonne = b.nbColonne();
        Matrice mat = new Matrice(ligne, colonne);
        for(int i=0; i<ligne;i++)
            for(int j=0; j< colonne; j++)
            {
                mat.coefficient[i][j]=0;
                for(int k=0; k <a.nbColonne();k++)
                    mat.coefficient[i][j] += a.coefficient[i][k] * b.coefficient[k][j];
            }
        return mat;
    }

    static Matrice verif_produit(Matrice a, Matrice b) throws Exception{
        int ligne = 0;
        int colonne = 0;
        if(a.nbColonne()==b.nbLigne())
        {
            ligne = a.nbLigne();
            colonne = b.nbColonne();
        }
        else{
            throw new Exception("Dimensions des matrices à multiplier incorrectes");
        }

        Matrice mat = new Matrice(ligne, colonne);
        for(int i=0; i<ligne;i++)
            for(int j=0; j< colonne; j++)
            {
                mat.coefficient[i][j]=0;
                for(int k=0; k <a.nbColonne();k++)
                    mat.coefficient[i][j] += a.coefficient[i][k] * b.coefficient[k][j];
            }
        return mat;
    }
    public Matrice inverse() throws IllegalOperationException, IrregularSysLinException {
        int n=this.nbLigne();
        Matrice inverse;
        if(this.nbLigne()==this.nbColonne()) {
            inverse=new Matrice(n,n);
            for(int j=0;j<n;j++) {
                Vecteur b=new Vecteur(n);
                for(int i=0;i<n;i++) {
                    if(j!=i)
                        b.remplaceCoef(i, 0);
                    else
                        b.remplaceCoef(i, 1);
                }
                Helder systeme=new Helder(this,b);
                Vecteur x=systeme.resolution();
                for(int i=0;i<n;i++) {
                    inverse.remplacecoef(i, j, x.getCoeffecient(i));
                }
            }
        }
        else {
            throw new IllegalOperationException("la matrice doit etre carre");
        }
        return inverse;
    }

    public  double	norme_1() {
        double norme=0;
        int n=this.nbColonne();
        for(int i=0;i<n;i++) {
            int somme =0;
            for(int j=0;j<n;j++) {
                somme+= Math.abs(this.getCoef(j, i));

            }
            if (norme<somme) norme=somme;
        }
        return norme;

    }
    public  double	 norme_inf() {
        double norme=0;
        int n=this.nbColonne();
        for(int i=0;i<n;i++) {
            int somme =0;
            for(int j=0;j<n;j++) {
                somme+= Math.abs(this.getCoef(i, j));

            }
            if (norme<somme) norme=somme;
        }
        return norme;

    }
    public double cond_1() throws IllegalOperationException, IrregularSysLinException{
        Matrice inverse =this.inverse();
        double normeA=this.norme_1();
        double normeA_1=inverse.norme_1();
        double cond_1=normeA*normeA_1;
        return cond_1;

    }
    public double cond_inf() throws IllegalOperationException, IrregularSysLinException{
        Matrice inverse =this.inverse();
        double normeA=this.norme_inf();
        double normeA_1=inverse.norme_inf();
        double cond_inf=normeA*normeA_1;
        return cond_inf;

    }

    public double conditionnement(NormeGenerale laNorme ) throws IllegalOperationException, IrregularSysLinException {
        Matrice inverse =this.inverse();
        double normeA=laNorme.norme(this);
        double normeA_1=laNorme.norme(inverse);
        double cond_inf=normeA*normeA_1;
        return cond_inf;
    }
    public Matrice transpose() {
        int m = this.nbLigne();
        int n = this.nbColonne();
        Matrice transposedMatrix = new Matrice(n, m); // Matrice de taille nbColonne x nbLigne pour la transposée

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                transposedMatrix.remplacecoef(j, i, this.getCoef(i, j)); // Échanger les lignes et colonnes
            }
        }

        return transposedMatrix;
    }


    public static void main(String[] args) throws Exception {
        double mat[][]= {{2,1},{0,1}};
        Matrice a = new Matrice(mat);
        Matrice transposedA = a.transpose();
        System.out.println("Transposée de la matrice A :\n" + transposedA);
        System.out.println("construction d'une matrice par affectation d'un tableau :\n"+a);
        Matrice b = new Matrice("C:\\Users\\HP\\eclipse-workspace\\Calcul Matriciel\\src\\alglin\\matrice.txt");
        System.out.println("Construction d'une matrice par lecture d'un fichier :\n"+b);
        Matrice c = new Matrice(2,2);
        c.recopie(b);
        System.out.println("Recopie de la matrice b :\n"+c);
        System.out.println("Nombre de lignes et colonnes de la matrice c : "+c.nbLigne()+
                ", "+c.nbColonne());
        System.out.println("Coefficient (2,2) de la matrice b : "+b.getCoef(1, 1));
        System.out.println("Nouvelle valeur de ce coefficient : 8");
        b.remplacecoef(1, 1, 8);
        System.out.println("Vérification de la modification du coefficient");
        System.out.println("Coefficient (2,2) de la matrice b : "+b.getCoef(1, 1));
        System.out.println("Addition de 2 matrices : affichage des 2 matrices "+
                "puis de leur addition");
        System.out.println("matrice 1 :\n"+a+"matrice 2 :\n"+b+"somme :\n"+
                Matrice.addition(a,b));
        System.out.println("Produit de 2 matrices : affichage des 2 matrices "+
                "puis de leur produit");
        System.out.println("matrice 1 :\n"+a+"matrice 2 :\n"+b+"produit :\n"+
                produit(a,b));


        System.out.println("***************************************************************************Tp3*******************************************************************");
        double matrice[][]= {{1,2,3},{4,5,6},{7,8,10}};
        Matrice A = new Matrice(matrice);
        System.out.println("Matrice à inverser :");
        System.out.println(A);

        // Calcul de l'inverse de la matrice
        Matrice inverseA = A.inverse();
        System.out.println("Inverse de la matrice :");
        System.out.println(inverseA);
        // Vérification en multipliant la matrice d'origine par son inverse
        System.out.println("Vérification : produit de la matrice par son inverse :");
        Matrice AA_1 = produit(A, inverseA);
        System.out.println(AA_1);

        // Comparaison avec la matrice identité
        System.out.println("Vérification : comparaison avec la matrice identité :");
        Matrice identite = new Matrice(A.nbLigne(), A.nbColonne());
        for (int i = 0; i < A.nbLigne(); i++) {
            identite.remplacecoef(i, i, 1); // Initialisation de la matrice identité
        }
        System.out.println("l'identit:\n"+identite+"\n");
        // Calcul de la différence entre le produit et la matrice identité
        Matrice difference = new Matrice(A.nbLigne(), A.nbColonne());
        for(int i=0; i<difference.nbLigne();i++)
            for(int j=0; j< difference.nbColonne(); j++)
                difference.coefficient[i][j]=AA_1.coefficient[i][j] - identite.coefficient[i][j];
        System.out.println("Différence entre le produit et la matrice identité :");
        System.out.println(difference);

        // Calcul des normes de la différence
        double norme1 = difference.norme_1();
        double normeInf = difference.norme_inf();
        System.out.println("***Calcule des normes de la matrice AA^-1-Id:***");
        System.out.println("Norme 1 de la différence : " + norme1);
        System.out.println("Norme infinie de la différence : " + normeInf);
        if(norme1==0 && normeInf==0) System.out.println("================>l'inverse est bien calculé");
        else                         System.out.println("================>l'inverse est incorrect");
        // Calcul et affichage des conditionnements de la matrice
        System.out.println("***Calcule des conditionnements de la matrice :***");
        System.out.println("Conditionnement 1 : " + A.cond_1());
        System.out.println("Conditionnement infinie : " + A.cond_inf());

        System.out.println("***************************************************************************Partie facultatif*******************************************************************");
        NormeGenerale laNorme1=new Norme_1();
        NormeGenerale laNormeInf=new Norme_inf();
        System.out.println("Conditionnement 1 : " + A.conditionnement(laNorme1));
        System.out.println("Conditionnement infinie : " + A.conditionnement (laNormeInf));


    }
}
