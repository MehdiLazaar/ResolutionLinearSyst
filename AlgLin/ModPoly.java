package AlgLin;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ModPoly extends JPanel {

    double[] coefficients;
    double[][] fonctions_base;
    int degree;

    public ModPoly(int degree) {
        this.degree = degree;
        coefficients = new double[degree + 1];
        String fileName = "Points.txt";
        double tab[][] = lireFichier(fileName);
        int n = tab.length;
        fonctions_base = new double[n][degree + 1];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < degree + 1; j++) {
            	fonctions_base[i][j] = Math.pow(tab[i][0], j);
            }
        }
    }

    public void identifier() throws Exception {
    	String fileName = "Points.txt";
        double tab[][] = lireFichier(fileName);
        int n = tab.length;

        Matrice A = new Matrice(fonctions_base);
        Matrice At = A.transpose();
        Matrice AtA = Matrice.produit(At, A);
        Vecteur b = new Vecteur(n);
        for (int i = 0; i < n; i++) {
            b.remplaceCoef(i, tab[i][1]);
        }

        Vecteur Atb = new Vecteur(degree + 1); 
        for (int i = 0; i < degree + 1; i++) {
            double somme = 0;
            for (int j = 0; j < n; j++) {
                somme += At.getCoef(i, j) * b.getCoeffecient(j);
            }
            Atb.remplaceCoef(i, somme);
            
        }

        Helder system = new Helder(AtA, Atb);
        Vecteur solution = system.resolution();

        for (int i = 0; i < degree + 1; i++) {
            coefficients[i] = solution.getCoeffecient(i);
        }
        System.out.println(solution);
    }

    private static double[][] lireFichier(String fileName) {
        double[][] coords = null;
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            int numPoints = 0;
            while (scanner.hasNextLine()) {
                scanner.nextLine();
                numPoints++;
            }
            coords = new double[numPoints][2];

            scanner = new Scanner(file);

            for (int i = 0; i < numPoints; i++) {
                String line = scanner.nextLine();
                String[] parts = line.split("\\s+");
                coords[i][0] = Double.parseDouble(parts[0]);
                coords[i][1] = Double.parseDouble(parts[1]);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Fichier introuvable: " + e.getMessage());
        }
        return coords;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();

        g2d.drawLine(0, height / 2, width, height / 2);
        g2d.drawLine(width / 2, 0, width / 2, height);

        double scaleX = width / 30.0;
        double scaleY = height / 30.0;

        int[] xPoints = new int[width];
        int[] yPoints = new int[width];
        for (int i = 0; i < width; i++) {
            double x = (i - width / 2.0) / scaleX;
            double y = evaluerPoly(x);
            xPoints[i] = i;
            yPoints[i] = height / 2 - (int) (y * scaleY);
        }
     // Dessiner les graduations sur l'axe x
        for (int i = -15; i <= 15; i++) {
            int x = width / 2 + (int) (i * scaleX);
            g2d.drawLine(x, height / 2 - 5, x, height / 2 + 5);
            g2d.drawString(Integer.toString(i), x - 5, height / 2 + 20);
        }

        // Dessiner les graduations sur l'axe y
        for (int i = -15; i <= 15; i++) {
            int y = height / 2 - (int) (i * scaleY);
            g2d.drawLine(width / 2 - 5, y, width / 2 + 5, y);
            g2d.drawString(Integer.toString(i), width / 2 - 25, y + 5);
        }

        g2d.setColor(Color.RED);
        g2d.drawPolyline(xPoints, yPoints, width);

        String fileName = "C:\\Users\\HP\\eclipse-workspace\\Calcul Matriciel\\src\\alglin\\Points.txt";
        double[][] points = lireFichier(fileName);
        g2d.setColor(Color.BLUE);
        for (double[] point : points) {
            int x = width / 2 + (int) (point[0] * scaleX);
            int y = height / 2 - (int) (point[1] * scaleY);
            g2d.drawLine(x - 3, y - 3, x + 3, y + 3);
            g2d.drawLine(x - 3, y + 3, x + 3, y - 3);
        }
    }

    private double evaluerPoly(double x) {
        double result = 0;
        for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * Math.pow(x, i);
        }
        return result;
    }

    public static void main(String args[]) {
        String input = JOptionPane.showInputDialog(null, "Entrez le degré du polynôme :");
        int degree = Integer.parseInt(input);

        ModPoly modPoly = new ModPoly(degree);
        try {
            modPoly.identifier();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame();
        frame.setTitle("Méthode des moindres carrée");
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(modPoly);
        frame.setVisible(true);
    }
}
