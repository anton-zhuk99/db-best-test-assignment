package ta.dbbest.model;

public class TableRecord {

    private int idX;
    private int idY;
    private int len;

    public TableRecord(int idX, int idY, int len) {
        this.idX = idX;
        this.idY = idY;
        this.len = len;
    }

    public int getIdX() {
        return idX;
    }

    public TableRecord setIdX(int idX) {
        this.idX = idX;
        return this;
    }

    public int getIdY() {
        return idY;
    }

    public TableRecord setIdY(int idY) {
        this.idY = idY;
        return this;
    }

    public int getLen() {
        return len;
    }

    public TableRecord setLen(int len) {
        this.len = len;
        return this;
    }
}
