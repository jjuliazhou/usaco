// passed ~50% test cases

import java.io.*;
import java.util.*;

public class dec2023q2 {
    static long divisor = 1_001L; // standard divisor used
    static long prime = 1_000_000_007L; // standard prime used

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] line1 = br.readLine().split(" ");
        int numBarns = Integer.parseInt(line1[0]); // number of barns
        int pairs = Integer.parseInt(line1[1]); // number connected pairs

        String[] aIn = br.readLine().split(" ");
        String[] bIn = br.readLine().split(" ");
        int[] A = new int[pairs];
        int[] B = new int[pairs];
        int[] extendB = new int[pairs*2];

        boolean[] barns = new boolean[numBarns+1];
        int fixedPoints = 0;

        for(int i=0; i<pairs; i++){
            int a = Integer.parseInt(aIn[i]);
            int b = Integer.parseInt(bIn[i]);
            A[i] = a;
            B[i] = b;
            extendB[i] = b;
            extendB[i+pairs] = b;
            barns[a] = true;
            barns[b] = true; 
        }  

        int[] reverseA = reverseArr(A);
        int[] reverseB = reverseArr(extendB);

        for(int i=1; i<numBarns+1; i++){
            if(barns[i] == false){
                fixedPoints += 1;
            }
        }

        int a = Math.max((binSearch(A, extendB)), (binSearch(A, reverseB)));
        int b = Math.max((binSearch(reverseA, extendB)), (binSearch(reverseA, reverseB)));
        System.out.println(Math.max(a, b) + fixedPoints);

    }
    public static int[] reverseArr(int[] extend){
        int[] toReturn = extend.clone();
        int start = 0;
        int end = toReturn.length-1;
        int current = -1;
        while(start < end){ // standard reverse algorithm
            current = toReturn[start];
            toReturn[start] = toReturn[end];
            toReturn[end] = current;
            start += 1;
            end -= 1;
        }
        return toReturn; 
    }

    public static int binSearch(int[] A, int[] B) { // standard binary search algorithm, time complexity = log n
        if(A.length > B.length){
            return binSearch(B, A);
        }
        int end = Math.min(A.length+1, B.length+1);
        int beginning = 1;
        while (beginning < end) {
            int middle = beginning+(end-beginning) / 2;
            if (getHash(A, B, middle) == true){
                beginning = 1+middle; // add 1 here instead of adjusting later
            }
            else {
                end = middle;
            }
        }
        return beginning-1; // want 1 less than the start value
    }

    public static boolean getHash(int[] A, int[] B, int middle) { // rolling hash approach w/ max time = O(a*b), average = O(a+b)
        long currPow = 1L; // track the current power
        for (int i=0; i<middle-1; i++){
            currPow = (currPow * prime) % divisor;
        }
        long tracker = 0L; // track the hash
        for (int i=0; i<middle; i++){
            tracker = (tracker * prime % divisor + A[i]) % divisor;
        }
        Set<Integer> rollingHash = new HashSet<>();
        rollingHash.add((int)tracker);

        for (int y=middle, x=0; y<A.length; y++, x++) {
            tracker = ((divisor+tracker-((currPow*A[x]) % divisor)) % divisor);
            tracker = (tracker * prime + A[y]) % divisor;
            rollingHash.add((int)tracker);
        }
        
        tracker = 0L; // reset and check again
        for (int i = 0; i < middle; i++){
            tracker = (tracker * prime + B[i]) % divisor;
        }
        if (rollingHash.contains(Math.toIntExact(tracker))){
            return true;
        }
        for (int y=middle, x=0; y<B.length; y++, x++) {
            tracker = ((divisor+tracker-((currPow*B[x]) % divisor)) % divisor);
            tracker = (tracker * prime + B[y]) % divisor;
            if (rollingHash.contains(Math.toIntExact(tracker))){
                return true;
            }
        }
        return false;
    }
}
