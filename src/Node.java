import java.util.ArrayList;
import java.util.List;

public class Node {
    public List<Key> keys;

    String address;
    byte[] hash;

    public Node(String address) {
        this.address = address;
        this.hash = Util.toHash(address);
        this.keys = new ArrayList<Key>();
    }

    public String getAddress() {
        return address;
    }

    public byte[] getHash() {
        return hash;
    }

    public List<Key> getKeys() {
        return keys;
    }
}
