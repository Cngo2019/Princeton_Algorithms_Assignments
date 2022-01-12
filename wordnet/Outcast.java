import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private WordNet wordnet;

    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }        // constructor takes a WordNet object

    public String outcast(String[] nouns) {
        /**
         * find words that contain that noun.
         */


        int[] distance = new int[nouns.length];
        int curr_distance = 0;
        for (int i = 0; i < distance.length; i++) {
            curr_distance = 0;
            for (int j = 0; j < distance.length; j++) {

                curr_distance += wordnet.distance(nouns[i], nouns[j]);
            }
            distance[i] = curr_distance;
        }

        int curr_max = distance[0];
        int max_ind = 0;
        for (int i = 0; i < distance.length; i++) {
            if (distance[i] > curr_max) {
                curr_max = distance[i];
                max_ind = i;
            }
        }
        return nouns[max_ind];
    }   // given an array of WordNet nouns, return an outcast

    public static void main(String[] args) {

        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }

    }
}
