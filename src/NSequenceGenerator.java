/*
p(x1) = 0.2141, p(x2) = 0.2648, p(x3) = 0.3207 and p(x4) = 0.2004
 */
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class NSequenceGenerator {
    private int[] counts = {0,0,0,0};
    private int N;
    private double[] realCounts = {21.41,26.48,32.07,20.04};
    private String[] chars = {"A", "T", "C", "G"};

    public void generateOutcomes(int n, FileWriter f) throws IOException{
        N = n;
        Random rand = new Random();
        int x;
        for(int i = 0; i < N; i++){
            x = rand.nextInt(10000) + 1;
            if(x > 7996){
                counts[0]++;
                f.append("G");
            }
            else if(x > 4789){
                counts[1]++;
                f.append("C");
            }
            else if(x > 2141){
                counts[2]++;
                f.append("T");
            }
            else{
                counts[3]++;
                f.append("A");
            }
        }
    }

    public String[] getStats(){
        double n = N/100;
        String[] statistics = new String[5];
        statistics[0] = "char\tcount\t\t%\t\t\treal distribution";
        for(int i = 3; i >= 0; i--){
            statistics[4-i] = chars[3-i] + "\t\t" + counts[i] + "\t\t" + counts[i]/n + "\t\t" + realCounts[3-i];
        }
        return statistics;
    }

    public int[] getCounts(){
        return counts;
    }
}