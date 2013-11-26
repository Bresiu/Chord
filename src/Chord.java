import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bresiu on 11.11.13.
 */
public class Chord {
    public static List<Node> nodeList = new ArrayList<Node>();

    // Dolacz wezel do sieci
    public static void join(String address) {
        // tworzenie nowego wezla
        Node node = new Node(address);
        // obliczanie poprawnej pozycji do wstawienia wezla
        int position = getCorrectNodePosition(address);
        if (position >= 0) {
            nodeList.add(position, node);
            // przechwycenie kluczy nastepnika
            notifySuccessorCome(node, getSuccessor(node));
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

    // TODO
    // Wstaw dane do sieci (do odpowiedniego wezla)
    public static void insert(String fileName) {
        Key key = new Key(fileName);
        int position = getCorrectKeyNode(fileName);
        getNode(position).getKeys().add(key);
    }

    private static int getCorrectKeyNode(String fileName) {
        int position = 0;
        // biginteger z hasha key'a do wstawienia
        BigInteger keyToInsert = Util.hashToNum(Util.toHash(fileName));
        if (!nodeList.isEmpty()) {
            while (position < size()) {
                // biginteger z hasha node'a na odpowiedniej pozycji
                BigInteger nodeInList = Util.hashToNum(getNode(position).getHash());
                int result = compareHashes(keyToInsert, nodeInList);
                if (result == -1 || result == 0){
                    return position;
                } else {
                    position++;
                }
            }
        }
        // jezeli petla doszla do tego momentu, znaczy, ze hash klucza jest wiekszy niz
        // hashe wszystkich wezlow. Zwraca pozycje pierwszego wezla
        return 0;
    }

    // TODO
    // Znajdz dane w sieci (odpowiedni wezel)
    public static void find(String filename) {

    }

    private static int getCorrectNodePosition(String address) {
        int position = 0;
        // biginteger z hasha node'a do wstawienia
        BigInteger nodeToInsert = Util.hashToNum(Util.toHash(address));
        if (!nodeList.isEmpty()) {
            while (position < size()) {
                // biginteger z hasha node'a na odpowiedniej pozycji
                BigInteger nodeInserted = Util.hashToNum(getNode(position).getHash());
                int result = compareHashes(nodeToInsert, nodeInserted);
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
        // kopiowanie wszystkich kluczy do nastepcy
        successor.keys.addAll(listOfKeys);
    }

    private static int compareHashes(BigInteger nodeHash, BigInteger keyHash) {
        return nodeHash.compareTo(keyHash);
    }

    private static void notifySuccessorCome(Node node, Node successor) {
        // Biginteger hasha
        BigInteger nodeHash = Util.hashToNum(node.getHash());
        // szukanie wszystkich kluczy nastepnika
        for (int i = 0; i < successor.getKeys().size(); i++) {
            // zamiana hasha klucza na biginteger w celu porownania
            BigInteger keyHash = Util.hashToNum(successor.getKeys().get(i).getHash());
            // porownanie
            int result = compareHashes(nodeHash, keyHash);
            // wszystkie mniejsze, rowne hashe dodawane do node'a i usuwane z nastepnika
            if (result == 1 || result == 0) {
                Key temp = successor.getKeys().get(i);
                successor.getKeys().remove(i);
                node.getKeys().add(temp);
            }
        }
    }

    // zwraca wielkosc tablicy wezlow
    private static int size() {
        return nodeList.size();
    }

    public static void nodeListToString() {
        for (int i = 0; i < size(); i++) {
            System.out.print(getNode(i).getAddress() + " (" +
                    Util.hashToNum(getNode(i).getHash()) + ") ");
            if (!getNode(i).getKeys().isEmpty()) {
                String keyList = "";
                for (int j = 0; j < getNode(i).getKeys().size(); j++) {
                    keyList += "|" + Util.hashToNum(getNode(i).getKeys().get(j).getHash()) + "| ";
                }
                System.out.println("Keys: " + keyList);
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        String a = "a";
        String b = "b";
        String c = "c";
        String d = "d";
        String e = "e";
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
        // spr czy poprawnie zwraca nastepnika w przypadku ostatniego wezla
        System.out.println("Nastepnik wezla: \'" + getNode(nodeList.size() - 1).getAddress()
                + "\' to \'" + getSuccessor(getNode(nodeList.size() - 1)).getAddress() + "\'");
        join(a);
        nodeListToString();
        join(a);
        nodeListToString();
        insert(a);
        insert(b);
        insert(c);
        insert(d);
        insert(a);
        insert(e);
        nodeListToString();
    }
}
