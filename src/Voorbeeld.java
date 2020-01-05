public class Voorbeeld implements Runnable {
        String name;
        Thread t;
        Voorbeeld(String thread){
            name = "Threadtest";
            t = new Thread(this, name);
            System.out.println("New thread: " + t);
            t.start();
        }

        public void run() {
            try {
                for(int i = 5; i > 0; i--) {
                    System.out.println(name + ": " + i);
                    Thread.sleep(1000);
                }
            }catch (InterruptedException e) {
                System.out.println(name + "Interrupted");
            }
            System.out.println(name + " exiting.");
        }
    }

    class MultiThread {
        public static void main(String args[]) {
            new Voorbeeld("One");
            new Voorbeeld("Two");
            new Voorbeeld("Three");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                System.out.println("Main thread Interrupted");
            }
            System.out.println("Main thread exiting.");
        }
    }
