import java.io.*;

public class dec2024q1 {
    public static void main(String[] args) throws IOException {

        // general process: bessie always takes from middle, elsie should take certain number from each end

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        for(int test = 0; test < T; test++){

            int N = Integer.parseInt(br.readLine()); // number of cakes
            int elsie = N/2 - 1;
            long total = 0L;
            long[] cakes = new long[N];
            long[] fronts = new long[elsie+1]; // part of cakes before bessie's
            long[] ends = new long[elsie+1]; // part of cakes after bessie's
            long elsieTotal = 0L;
            
            String[] temp = br.readLine().split(" "); 
            for(int i=0; i<N; i++){
                int cake = Integer.parseInt(temp[i]);
                cakes[i] = cake;
                total += cake;
            }

            for(int i=0; i<elsie; i++){  // partial sums for fronts
                fronts[i] = cakes[i] + fronts[i]; 
            }

            for(int i=N-1; i>=N-elsie; i--){ // partial sums for ends
                ends[i+elsie-N] = cakes[i] + ends[i+elsie+1-N];
            }

            for(int i=0; i<=elsie; i++){ // process elsie's on each end
                long current = fronts[i] + ends[i];
                if(current > elsieTotal){
                    elsieTotal = current;
                }
            }

            long bessieTotal = total - elsieTotal;
            System.out.println(bessieTotal + " " + elsieTotal);
        
        }
    }
}