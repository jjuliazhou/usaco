import java.io.*;
import java.util.*;

public class dec2024q2 {
    public static void main(String[] args) throws IOException {

        // general process: remove unnecessary restrictions (bigger than left & smaler than right & more trees needed), if tree not in any restriction remove

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testcases = Integer.parseInt(br.readLine()); // number of trials, 1 <= testcases <= 10

        for(int j=0; j<testcases; j++){

            String[] temp = br.readLine().split(" ");
            int N = Integer.parseInt(temp[0]); // number of trees
            int K = Integer.parseInt(temp[1]); // number of restrictions

            int[] locs = new int[N];
            int[][] restrictions = new int[K][3]; // [left endpt, right endpt, # needed]

            int leftIndex = 0;
            int rightIndex = 0; 
            boolean[] need = new boolean[N];
            for(int i=0; i<N; i++){
                need[i] = false;
            }
            int[] sums = new int[N+1]; // prefix sums

            temp = br.readLine().split(" ");
            for(int i=0; i<N; i++){ // store locations of trees
                locs[i] = Integer.parseInt(temp[i]);
            }

            Arrays.sort(locs);

            for(int i=0; i<K; i++){ // store restrictions
                temp = br.readLine().split(" ");
                restrictions[i][0] = Integer.parseInt(temp[0]);
                restrictions[i][1] = Integer.parseInt(temp[1]);
                restrictions[i][2] = Integer.parseInt(temp[2]);
            }
            
            Arrays.sort(restrictions, (resA, resB) -> {
                if(resA[1] == resB[1]){
                    return Integer.compare(resA[1], resB[1]); // move left side towards left
                }
                else{
                    return Integer.compare(resA[1], resB[1]); // move right side towards right
                }
            });

            for(int i=0; i<K; i++){ // manipulate restrictions

                int[] res = restrictions[i];

                rightIndex = binary(locs, res[1], N) - 1;
                leftIndex =  binary(locs, res[0]-1, N);

                int plantBack = res[2] - (sums[rightIndex+1] - sums[leftIndex]);

                if(plantBack>0) { 
                    int x = rightIndex+2;
                    while(x < N+1){
                        x++;
                        sums[x-1] = sums[x-1] + plantBack;
                    }

                    while (plantBack>0) { 
                        sums[rightIndex+1] += plantBack;
                        if (need[rightIndex] == false) {
                            plantBack -= 1; 
                            need[rightIndex] = true; 
                        }
                        rightIndex--;
                    }
                }
            }

            int answer = 0;

            for(int i=0; i<N; i++){ 
                if(need[i] == false){
                    answer++;
                }
            }
            System.out.println(answer);

        }
    }

    public static int binary(int[] nums, int want, int length) { // standard binary search 
        int small = 0;
        int big = length - 1;
        int medium;
        while (small <= big) {
            medium = small + (big-small) / 2;
            if (nums[medium] > want){
                big = medium-1;
            }
            else{
                small = medium+1;
            }
        }
        return small; 
    }

}

