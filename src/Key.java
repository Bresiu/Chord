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

    public byte[] getHash() {
        return hash;
    }
}
