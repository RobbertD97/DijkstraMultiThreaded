/**
 *

 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        //maak thread aan
//        new ParallelDijkstra("One");
//        new ParallelDijkstra("Two");
//        new ParallelDijkstra("Three");
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            System.out.println("Main thread Interrupted");
//        }
//        System.out.println("Main thread exiting.");



        String inputSetsLocation = "/Users/rmc10/Downloads/Dijkstra/DijkstraMultiThreaded/Inputsets/";
        String[] inputsets = new String[]{"G1.txt", "G23.txt", "G25.txt", "G36.txt", "G45.txt", "G54.txt", "G58.txt",
                "G60.txt", "G50.txt", "G70.txt"};
        Integer[] setRanges = Main.findSetRanges(inputSetsLocation, inputsets);
        Main.runTests(inputsets, inputSetsLocation, setRanges);

        //2. check of de inputsets kloppen
    }

    public static Integer[] findSetRanges(String inputSetsLocation, String[] inputsets) {
        // For loop om voor elke set zijn range te vinden en in te vullen in array.
        Integer[] setRanges = new Integer[inputsets.length];
        for (int i = 0; i < inputsets.length; i++) {
            setRanges[i] = 0;

            try {
                BufferedReader reader = new BufferedReader(new FileReader(inputSetsLocation + inputsets[i]));
                String line = reader.readLine();

                while (line != null) {

                    String[] test = line.split(" ");

                    //Misschien toch een dubbele array, [0] voor nr. inputset, [1] voor source/destination waarde.
                    Integer sourceValue = Integer.parseInt(test[0]);
                    Integer destinationValue = Integer.parseInt(test[1]);
                    if (setRanges[i] < sourceValue) {
                        setRanges[i] = sourceValue;
                    }
                    if (setRanges[i] < destinationValue) {
                        setRanges[i] = destinationValue;
                    }
                    line = reader.readLine();

                }
                System.out.println(setRanges[i]);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  setRanges;
    }

    public static void runTests(String[] inputsets, String inputSetsLocation, Integer[] setRanges) {
        //Create A dijkstra graph for each inputset, solve it, and test its speed
        for (int i = 0; i < inputsets.length; i++) {
            //1. max value +1 is het aantal vertices, aangezien 0 ook een edge is
            int vertices = setRanges[i] + 1;
            Dijkstra.Graph graph = new Dijkstra.Graph(vertices);

            // Het toevoegen van alle edges
            try {
                BufferedReader reader = new BufferedReader(new FileReader(inputSetsLocation + inputsets[i]));
                String line = reader.readLine();
                while (line != null) {
                    String[] test = line.split(" ");
                    graph.addEdge(Integer.parseInt(test[0]), Integer.parseInt(test[1]), Integer.parseInt(test[2]));
                    line = reader.readLine();
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Het meten en uitvoeren van het Dijkstra Algoritme
            long beginNanoTime = System.nanoTime();
            graph.dijkstra_GetMinDistances(0);
            long endNanoTime = System.nanoTime();
            long totalDuration = endNanoTime - beginNanoTime;

            System.out.println("The inputset "+ inputsets[i] + " ran in " + totalDuration + " ns.");
        }
    }
}
