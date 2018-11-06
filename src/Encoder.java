import java.io.*;

public class Encoder {
    public static void main(String[] args) throws IOException {
        int N = 10000000;

        NSequenceGenerator E = new NSequenceGenerator();
        FileOutputStream result = new FileOutputStream("output.txt");

        //create n-sequence.txt file of 10,000,000 characters
        File seq = new File("n-sequence.txt");
        FileWriter sequenceWriter = new FileWriter(seq);
        E.generateOutcomes(N, sequenceWriter);
        sequenceWriter.close();

        //write stats about n-sequence.txt to output.txt
        result.write("n-sequence generated\n".getBytes());
        String[] stats = E.getStats();
        for (String s : stats) {
            s = s + "\n";
            result.write(s.getBytes());
        }
        /*
        generate PMFs for d = 2,3,4
         */
        Pmf D2 = new Pmf();
        D2.add(new Map("A", 0.2141));
        D2.add(new Map("T", 0.2648));
        D2.add(new Map("C", 0.3207));
        D2.add(new Map("G", 0.2004));
        Huffman h2 = new Huffman(D2, 2);

        Pmf D3 = new Pmf();
        D3.add(new Map("A", 0.2141));
        D3.add(new Map("T", 0.2648));
        D3.add(new Map("C", 0.3207));
        D3.add(new Map("G", 0.2004));
        Huffman h3 = new Huffman(D3, 3);

        Pmf D4 = new Pmf();
        D4.add(new Map("A", 0.2141));
        D4.add(new Map("T", 0.2648));
        D4.add(new Map("C", 0.3207));
        D4.add(new Map("G", 0.2004));
        Huffman h4 = new Huffman(D4, 4);

        h2.encode();
        h3.encode();
        h4.encode();
        /*
        Print out stats to output.txt
        */
        result.write(("\nD=2\n" + D2.toString()).getBytes());
        result.write(("Empirical average length: " + empiricalAve(E, D2, N)+ "\n").getBytes());
        result.write(("Theoretical length: " + D2.getAveLength()+ "\n").getBytes());
        result.write(("Entropy: " + h2.getEntropy()+ "\n").getBytes());

        result.write(("\nD=3\n" + D3.toString()).getBytes());
        result.write(("Empirical average length: " + empiricalAve(E, D3, N)+ "\n").getBytes());
        result.write(("Theoretical length: " + D3.getAveLength()+ "\n").getBytes());
        result.write(("Entropy: " + h3.getEntropy()+ "\n").getBytes());

        result.write(("\nD=4\n" + D4.toString()).getBytes());
        result.write(("Empirical average length: " + empiricalAve(E, D4, N)+ "\n").getBytes());
        result.write(("Theoretical length: " + D4.getAveLength()+ "\n").getBytes());
        result.write(("Entropy: " + h4.getEntropy()+ "\n").getBytes());

        /*
        Create encoded files of n-sequence.txt on d=2,3 and 4.
         */
        String[] files = {"D2encode.txt", "D3encode.txt", "D4encode.txt"};
        FileOutputStream file;
        BufferedReader reader = new BufferedReader(new FileReader("n-sequence.txt"));

        Pmf[] myPmfs = {D2, D3, D4};
        for (int fileNum = 0; fileNum < 3; fileNum++) {
            file = new FileOutputStream(files[fileNum]);
            int c;
            while ((c = reader.read()) != -1) {
                switch (c) {
                    case 'A':
                        file.write(myPmfs[fileNum].myPmf.get(0).CODE.getBytes());
                        break;
                    case 'T':
                        file.write(myPmfs[fileNum].myPmf.get(1).CODE.getBytes());
                        break;
                    case 'C':
                        file.write(myPmfs[fileNum].myPmf.get(2).CODE.getBytes());
                        break;
                    case 'G':
                        file.write(myPmfs[fileNum].myPmf.get(3).CODE.getBytes());
                        break;
                }
            }
            reader.close();
            reader = new BufferedReader(new FileReader("n-sequence.txt"));
            file.close();
        }
    }

    private static double empiricalAve(NSequenceGenerator E, Pmf p, int N){
        int[] c = E.getCounts();
        double total = 0;
        for(int i = 0; i < 4; i++){
            total += p.myPmf.get(i).CODE.length() * c[3-i];
        }
        return total/N;
    }
}
