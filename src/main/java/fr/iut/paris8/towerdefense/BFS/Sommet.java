package fr.iut.paris8.towerdefense.BFS;

public class Sommet implements Comparable<Sommet> {
    private int i;
    private int j;

    public Sommet(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public Sommet () {
    }

    public int getI () {
        return this.i;
    }

    public int getJ () {
        return this.j;
    }

    public int getX(){return this.i*16;}
    public int getY(){return this.j*16;}

    public int hashCode() {
        int result = 1;
        result = 31 * result + this.i;
        result = 31 * result + this.j;
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
            Sommet other = (Sommet)obj;
            if (this.i != other.i ) {
                return false;
            } else {
                return this.j == other.j;
            }
        }
    }

    public String toString() {
        return "Sommet [" + this.i + ", " + this.j;
    }

    public int compareTo(Sommet o) {
        if (this.i < o.i ) {
            return -1;
        } else {
            return this.i == o.i ? this.j - o.j : 1;
        }
    }
}
