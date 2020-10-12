package ta.dbbest;

public class OutputRecord {

    private boolean exists;
    private int len;

    public OutputRecord(boolean exists, int len) {
        this.exists = exists;
        this.len = len;
    }

    public boolean isExists() {
        return exists;
    }

    public OutputRecord setExists(boolean exists) {
        this.exists = exists;
        return this;
    }

    public int getLen() {
        return len;
    }

    public OutputRecord setLen(int len) {
        this.len = len;
        return this;
    }
}
