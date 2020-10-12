package ta.dbbest;

public class InputRecord {

    private int idX;
    private int idY;

    public InputRecord(int idX, int idY) {
        this.idX = idX;
        this.idY = idY;
    }

    public int getIdX() {
        return idX;
    }

    public InputRecord setIdX(int idX) {
        this.idX = idX;
        return this;
    }

    public int getIdY() {
        return idY;
    }

    public InputRecord setIdY(int idY) {
        this.idY = idY;
        return this;
    }
}
