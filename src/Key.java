/**
 * Created by bresiu on 11.11.13.
 */
public class Key {

    String fileName;
    byte[] hash;

    public Key(String fileName) {
        this.fileName = fileName;
        this.hash = Util.toHash(this.fileName);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getHash() {
        return hash;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }
}
