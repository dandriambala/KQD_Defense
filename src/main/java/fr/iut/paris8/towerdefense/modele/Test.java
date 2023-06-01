package fr.iut.paris8.towerdefense.modele;

public class Test {
    public static void main(String[] args) {
//        int longueur = 60;
//        int largeur = 22;
//        int[][] t = new int[largeur][longueur];
//        for (int i =0; i<t.length; i++){
//            for (int j = 0; j<t[i].length; j++ ){
//                if(i==0 || i== t.length-1 || j == 0 || j == t[i].length-1)
//                    t[i][j] = 2;
//                if(i == t.length/2)
//                    t[i][j] = 1;
//            }
//        }
//        affichertab(t);
        System.out.println(Math.round(4.9));
    }

    public static void affichertab(int[][] t) {
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t[i].length; j++) {
                System.out.print(t[i][j]);
            }
            System.out.println();
        }
    }
}
