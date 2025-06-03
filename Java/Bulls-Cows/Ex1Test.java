

public class Ex1Test {
    public static void main(String[] args) {

        double avg2 = 0 , avg3 = 0, avg4 = 0, avg5 = 0, avg6 = 0;
        for (int i = 0; i < 100; i++){ // Test 100 times when the code is 2 digits
            Ex1.numOfDigits = 2;
            Ex1.main(args);
        }
        avg2 = ((double) Ex1.rounds_counter / 100);
        System.out.println(avg2);



        for (int i = 0; i < 100; i++){  // Test 100 times when the code is 3 digits
            Ex1.numOfDigits = 3;
            Ex1.main(args);

        }
        avg3 = ((double) Ex1.rounds_counter / 100);
        System.out.println(avg3);

        for (int i = 0; i < 100; i++){  // Test 100 times when the code is 4 digits
            Ex1.numOfDigits = 4;
            Ex1.main(args);

        }
        avg4 = ((double) Ex1.rounds_counter / 100);
        System.out.println(avg4);

        for (int i = 0; i < 100; i++){  // Test 100 times when the code is 5 digits
            Ex1.numOfDigits = 5;
            Ex1.main(args);

        }
        avg5 = ((double) Ex1.rounds_counter / 100);
        System.out.println(avg5);

        for (int i = 0; i < 100; i++){  // Test 100 times when the code is 6 digits
            Ex1.numOfDigits = 6;
            Ex1.main(args);

        }
        avg6 = ((double) Ex1.rounds_counter / 100);
        System.out.println(avg6);


    }
}
