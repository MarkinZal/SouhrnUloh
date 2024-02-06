public class Deskovky {
    private String nazev;
    private boolean koupeno;
    private int oblib;

    public Deskovky(String nazev, boolean koupeno, int oblib) {
        this.nazev = nazev;
        this.koupeno = koupeno;
        this.oblib = oblib;
    }

    public String getName() {
        return nazev;
    }

    public void setName(String name) {
        this.nazev = name;
    }

    public boolean isOwned() {
        return koupeno;
    }

    public void setOwned(boolean owned) {
        this.koupeno = owned;
    }

    public int getScore() {
        return oblib;
    }

    public void setScore(int score) {
        this.oblib = score;
    }
}