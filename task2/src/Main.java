import java.io.*;
import java.util.ArrayList;

        public class Main {
            private static File test1;
            private static File test2;

            private static ArrayList<ReaderPoints> points = new ArrayList<>();

            public static void main(String[] args) throws IOException {
                test1 = new File("test1.txt");
                test2 = new File("test2.txt");

                ReaderCyrcle readerCyrcle = new ReaderCyrcle(test1);
                downloadPoints(test2);
                for (ReaderPoints p : points) {
                    float distance = p.getDistanceToCentre(readerCyrcle.getCentre());
                    int res = -1;
                    if (distance == readerCyrcle.getRadius()) {
                        res = 0;
                    }
                    if (distance < readerCyrcle.getRadius()) {
                        res = 1;
                    }
                    if (distance > readerCyrcle.getRadius()) {
                        res = 2;
                    }
                    System.out.println(res);
                }
            }

            private static void downloadPoints(File file) throws IOException {
                //points = new ArrayList<>();
                InputStream is = new FileInputStream(file);
                BufferedReader buff = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = buff.readLine()) != null) {
                    int sep = line.indexOf(32);
                    Float x = Float.parseFloat(line.substring(0, sep));
                    Float y = Float.parseFloat(line.substring(sep + 1));
                    points.add(new ReaderPoints(x, y));
                }
                buff.close();
            }
        }
