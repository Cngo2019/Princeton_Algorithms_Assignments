import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private ArrayList<LineSegment> segments;


    public FastCollinearPoints(Point[] points) {

        if (points == null) {
            throw new IllegalArgumentException();
        }
        segments = new ArrayList<LineSegment>();

        Point[] tempPoints = points.clone();
        //Arrays.sort(tempPoints);
        // should be in slope order.

        /**
         * Remove duplicates:
         * Sort the tempPoints.
         *
         * Keep a counter
         */

        // remove duplicates:
        if (points == null) {
            throw new IllegalArgumentException();
        }
        //Arrays.sort(tempPoints);
        // should be in slope order.

        /**
         * Remove duplicates:
         * Sort the tempPoints.
         *
         * Keep a counter
         */

        // remove duplicates:
        int n = tempPoints.length;
        tempPoints = filter_duplicates(tempPoints);
        if (tempPoints.length != n) {
            throw new IllegalArgumentException();
        }        //slope order
        ArrayList<String> segmentCheck = new ArrayList<String>();
        for (int j = 0; j < tempPoints.length; j++) {
            Point p = tempPoints[j];

            Arrays.sort(tempPoints, p.slopeOrder()); //sorting. O(nlogn)

            double[] slopeVals = new double[tempPoints.length];
            for (int i = 0; i < tempPoints.length; i++) {
                slopeVals[i] = tempPoints[i].slopeTo(p); //O(n)
            }

            /**
             * Our sorted array is in slope order and their corresponding subsections are sorted from greatest to biggest.
             * No create an algorithm that will search for the endpoints.
             *
             * Be careful of negative infinity. Just ignore. negative infinity will always be at the beginning of temp_arr.
             */

            //searching algorithm:

            int counter = 1;
            double curr_slope = slopeVals[1];

            for (int i = 2; i < tempPoints.length; i++) {
                if (i == tempPoints.length - 1 && counter >= 2 && curr_slope == slopeVals[i]) {
                    //get segment.
                    Point max = choose_max(p, tempPoints[i]);
                    Point min = choose_min(p, tempPoints[i - counter]);

                    LineSegment s = new LineSegment(min, max);
                    /** check if it is in **/
                    if (!segmentCheck.contains(s.toString())) {
                        segmentCheck.add(s.toString());
                        segments.add(s);
                    }
                }
                else if (curr_slope == slopeVals[i]) {
                    counter += 1;
                }
                else if (counter >= 3) {
                    // get segment.
                    Point max = choose_max(p, tempPoints[i - 1]);
                    Point min = choose_min(p, tempPoints[i - counter]);

                    LineSegment s = new LineSegment(min, max);
                    /** check if it is in **/
                    if (!segmentCheck.contains(s.toString())) {
                        segmentCheck.add(s.toString());
                        segments.add(s);
                    }
                    // reset counter to 1.
                    counter = 1;
                    // update curr slope.
                    curr_slope = slopeVals[i];
                }
                else {
                    counter = 1;
                    curr_slope = slopeVals[i];
                }


            }

        }


    }     // finds all line segments containing 4 or more points

    private Point[] filter_duplicates(Point[] x) {
        Arrays.sort(x);
        ArrayList<Point> t_list = new ArrayList<Point>();
        for (int i = 1; i < x.length; i++) {
            if (x[i - 1].compareTo(x[i]) != 0) {
                t_list.add(x[i - 1]);
            }
        }

        //The very last unique number will never be added to our list.
        t_list.add(x[x.length - 1]);

        x = new Point[t_list.size()];
        for (int i = 0; i < t_list.size(); i++) {
            x[i] = t_list.get(i);
        }
        return x;

    }

    public int numberOfSegments() {
        return segments.size();
    }      // the number of line segments

    public LineSegment[] segments() {
        LineSegment[] s = new LineSegment[segments.size()];
        for (int i = 0; i < s.length; i++) {
            s[i] = segments.get(i);
        }

        return s;
    }          // the line segments

    private Point choose_min(Point x, Point y) {
        if (x.compareTo(y) <= 0) {
            return x;
        }
        else {
            return y;
        }
    }

    private Point choose_max(Point x, Point y) {
        if (x.compareTo(y) >= 0) {
            return x;
        }
        else {
            return y;
        }
    }
}
