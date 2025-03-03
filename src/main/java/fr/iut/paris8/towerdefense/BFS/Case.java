package fr.iut.paris8.towerdefense.BFS;

public class Case implements Comparable<Case> {
    private int colonne;
    private int ligne;

    public Case ( int colonne, int ligne) {
        this.colonne = colonne;
        this.ligne = ligne;
    }

    public Case () {
    }

    public int getColonne () {
        return this.colonne;
    }

    public int getLigne () {
        return this.ligne;
    }

    public int getX(){return this.colonne *16 ;}
    public int getY(){return this.ligne *16 ;}

    public int hashCode() {
        int result = 1;
        result = 31 * result + this.colonne;
        result = 31 * result + this.ligne;
        return result;
    }


    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (this.getClass() != obj.getClass()) {
            return false;
        } else {
            Case that = (Case) obj;
            return colonne == that.colonne && ligne == that.ligne;
            }
        }

    public String toString() {
        return "Case [" + this.colonne + ", " + this.ligne;
    }

    public int compareTo( Case o) {
        if (this.colonne < o.colonne ) {
            return -1;
        } else {
            return this.colonne == o.colonne ? this.ligne - o.ligne : 1;
        }
    }

    public void setColonne ( int colonne ) {
        this.colonne = colonne;
    }

    public void setLigne ( int ligne ) {
        this.ligne = ligne;
    }
}
