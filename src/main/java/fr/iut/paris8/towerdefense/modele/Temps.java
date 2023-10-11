package fr.iut.paris8.towerdefense.modele;

public class Temps {

    private long tempsDepart=0;
    private long tempsFin=0;
    private long duree;

    public Temps(long duree){
        this.start();
    }
    public void start()
    {
        tempsDepart=System.currentTimeMillis();
        tempsFin=0;
        duree=0;
    }

    public void resume()
    {
        if(tempsDepart==0) {return;}

    }

    public void stop()
    {
        if(tempsDepart==0) {return;}
        tempsFin=System.currentTimeMillis();
        duree=(tempsFin-tempsDepart);
        tempsDepart=0;
        tempsFin=0;

    }

    public long getDureeSec()
    {
        return duree/1000;
    }

    public long getDureeMs()
    {
        return duree;
    }

}
