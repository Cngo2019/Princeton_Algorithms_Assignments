import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> segments;

    public BruteCollinearPoints(Point[] points) {

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
        int n = tempPoints.length;
        tempPoints = filter_duplicates(tempPoints);
        if (tempPoints.length != n) {
            throw new IllegalArgumentException();
        }

        if (tempPoints.length > 3) {
            int c = 0;
            for (int i = 0; i < tempPoints.length - 3; i++) {
                for (int j = i + 1; j < tempPoints.length - 2; j++) {
                    for (int k = j + 1; k < tempPoints.length - 1; k++) {
                        for (int h = k + 1; h < tempPoints.length; h++) {
                            c += 1;
                            /** check if points are collinear **/
                            if (collinear(tempPoints[i], tempPoints[j], tempPoints[k],
                                          tempPoints[h])) {
                                /** find end points via sorting **/
                                Point[] arr = {
                                        tempPoints[i], tempPoints[j], tempPoints[k], tempPoints[h]
                                };
                                // Sorting collinear points will put them in spatial order. This will allow us to get the endpoints.
                                Arrays.sort(arr);
                                segments.add(new LineSegment(arr[0], arr[3]));
                            }

                        }
                    }
                }
            }
        }


    }

    // finds all line segments containing 4 points

    private boolean collinear(Point p, Point q, Point r, Point s) {

        double slope1 = p.slopeTo(q);
        double slope2 = p.slopeTo(r);
        double slope3 = p.slopeTo(s);

        return slope1 == slope2 && slope1 == slope3 && slope2 == slope3;
    }

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
    }     // the number of line segments

    public LineSegment[] segments() {
        LineSegment[] s = new LineSegment[segments.size()];
        for (int i = 0; i < s.length; i++) {
            s[i] = segments.get(i);
        }

        return s;
    }            // the line segments
}
