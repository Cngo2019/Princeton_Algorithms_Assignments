import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.HashMap;

public class WordNet {

    private HashMap<String, ArrayList<Integer>> hashMapStringKey;
    private HashMap<Integer, String> hashMapIntegerKey;
    private Digraph wordNetGraph;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {

        if (synsets == null || hypernyms == null)
            throw new IllegalArgumentException();

        hashMapStringKey = new HashMap();
        hashMapIntegerKey = new HashMap();
        In x = new In(synsets);
        int counter = 0;
        while (x.hasNextLine()) {
            String curr = x.readLine();
            String[] temp = curr.split(",");
            String[] noun_arr = temp[1].split(" ");
            hashMapIntegerKey.put(Integer.parseInt(temp[0]), temp[1]);
            for (String noun : noun_arr) {
                if (hashMapStringKey.containsKey(noun)) {
                    ArrayList<Integer> k = new ArrayList<Integer>();
                    k = hashMapStringKey.get(noun);
                    k.add(Integer.parseInt(temp[0]));
                    hashMapStringKey.put(noun, k);
                }
                else {
                    ArrayList<Integer> k = new ArrayList<Integer>();
                    k.add(Integer.parseInt(temp[0]));
                    hashMapStringKey.put(noun, k);
                }
            }
            counter += 1;
        }
        // Create the digraph.
        x.close();
        wordNetGraph = new Digraph(counter);
        x = new In(hypernyms);

        while (x.hasNextLine()) {
            String curr = x.readLine();
            String[] split = curr.split(",");

            for (int i = 1; i < split.length; i++) {
                wordNetGraph.addEdge(Integer.parseInt(split[0]), Integer.parseInt(split[i]));
            }

        }

        x.close();

    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        ArrayList<String> nouns = new ArrayList<String>();
        for (String keys : hashMapStringKey.keySet()) {
            nouns.add(keys);
        }

        return nouns;
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return hashMapStringKey.get(word) != null;
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        SAP sp = new SAP(wordNetGraph);
        return sp.length(hashMapStringKey.get(nounA), hashMapStringKey.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        SAP sp = new SAP(wordNetGraph);
        int ancestorID = sp.ancestor(hashMapStringKey.get(nounA), hashMapStringKey.get(nounB));
        return hashMapIntegerKey.get(ancestorID);
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet k = new WordNet("synsets.txt", "hypernyms.txt");
        System.out.println(k.sap("banana", "apple"));
    }
}
