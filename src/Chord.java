import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bresiu on 11.11.13.
 */
public class Chord {
    public static List<Node> nodeList = new ArrayList<Node>();

    // Dolacz wezel do sieci
    // TODO get keys from successor
    public static void join(String address) {
        Node node = new Node(address);
        if (getCorrectNodePosition(address) >= 0) {
            nodeList.add(getCorrectNodePosition(address), node);
        } else {
            System.out.println("Node already in Chord.");
        }
    }

    // Odlacz wezel od sieci, przekaz klucze nastepcy
    public static void leave(String address) {
        boolean removed = false;
        for (int i = 0; i < size(); i++) {
            if (getNode(i).getAddress().equals(address)) {
                notifySuccessorLeave(getSuccessor(getNode(i)), getNode(i).getKeys());
                nodeList.remove(i);
                removed = true;
            }
        }
        if (!removed) {
            System.out.println("Can not find a node with address: " + address);
        }
    }

    // Wstaw dane do sieci (do odpowiedniego wezla)
    public static void insert(String fileName) {
        int hash = fileName.hashCode();
    }

    // Znajdz dane w sieci (odpowiedni wezel)
    public static void find(String filename) {

    }

    public static void nodeListToString() {
        for (int i = 0; i < size(); i++) {
            System.out.print(getNode(i).getAddress() + " (" + Util.hashToNum(getNode(i).getHash()) + ") ");
        }
        System.out.println();
    }

    private static int getCorrectNodePosition(String address) {
        int position = 0;
        // biginteger z hasha node'a do wstawienia
        BigInteger nodeToInsert = Util.hashToNum(Util.toHash(address));
        if (!nodeList.isEmpty()) {
            while (position < size()) {
                // biginteger z hasha node'a na odpowiedniej pozycji
                BigInteger nodeInserted = Util.hashToNum(getNode(position).getHash());
                int result = compareHashes(nodeToInsert, nodeInserted)
                if (result == -1) {
                    // a < b
                    return position;
                } else if (result == 0) {
                    // wezly sa identyczne
                    return -1;
                }
                position++;
            }
        }
        return position;
    }

    private static Node getNode(int i) {
        return nodeList.get(i);
    }

    private static Node getSuccessor(Node node) {
        int position = nodeList.indexOf(node);
        if (position < size() - 1) {
            // zwraca nastepny wezel
            return getNode(++position);
        } else if (position == size() - 1) {
            // jezeli jest to ostatni wezel, zwraca pierwszy
            return getNode(0);
        } else {
            // zwraca siebie, jest tylko jeden wezel
            return node;
        }
    }

    private static void notifySuccessorLeave(Node successor, List<Key> listOfKeys) {
        // kopoiowanie wszystkich kluczy do nastepcy
        successor.keys.addAll(listOfKeys);
    }

    private static int compareHashes(BigInteger nodeHash, BigInteger keyHash) {
        return nodeHash.compareTo(keyHash);
    }

    private static void notifySuccessorCome(Node node, Node successor) {
        BigInteger nodeHash = Util.hashToNum(node.getHash());
        for (int i = 0; i < successor.getKeys().size(); i++) {
            BigInteger keyHash = Util.hashToNum(successor.getKeys().get(i).getHash());
            int result = compareHashes(nodeHash, keyHash);
            if (result == 1 || result == 0) {
                Key temp = successor.getKeys().get(i);
                successor.getKeys().remove(i);
                node.getKeys().add(temp);
            }
        }
    }

    private static int size() {
        return nodeList.size();
    }

    public static void main(String[] args) {
        String a = "a";
        String b = "b";
        String c = "c";
        String d = "d";
        join(a);
        nodeListToString();
        leave(a);
        nodeListToString();
        join(b);
        nodeListToString();
        join(c);
        nodeListToString();
        join(d);
        nodeListToString();
        join(a);
        nodeListToString();
        leave(a);
        nodeListToString();
        leave("e");
        nodeListToString();
    }
}
