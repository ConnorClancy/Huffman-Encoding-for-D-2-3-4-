import java.util.ArrayList;

class Huffman {
    private Pmf PMF;
    private int D;
    private ArrayList<Map> encodeVer;
    Huffman(Pmf p, int d){
        PMF = p;
        D = d;
        double result = ((double)PMF.myPmf.size() - 1) / (D - 1);
        int temp = 0;
        while(Math.round(result) != result){
            Map dummy = new Map("dummy" + temp, 0.0);
            temp++;
            PMF.add(dummy);
            result = ((double)PMF.myPmf.size() - 1) / (D - 1);
        }
        encodeVer = PMF.myPmf;
    }

    /*
    Generates a Huffman code from the pmf provided.
     */
    ArrayList<Map> encode(){
        if(encodeVer.size() > D){
            encodeVer = sort(encodeVer);
            Map temp = new Map("", 0);
            for(int i = 0; i < D; i++){
                temp.concat(encodeVer.get(encodeVer.size() - D + i));
                for(Map m : PMF.myPmf){
                    if(encodeVer.get(encodeVer.size() - D + i).TOKEN.contains(m.TOKEN)){
                        m.CODE = "" + (D - (i + 1)) + m.CODE;
                    }
                }
                encodeVer.remove(encodeVer.size() - D + i);
            }
            encodeVer.add(temp);
            encode();
        }
        else{
            encodeVer = sort(encodeVer);
            for(int i = 0; i < D; i++) {
                for (Map m : PMF.myPmf) {
                    if (encodeVer.get(encodeVer.size() - D + i).TOKEN.contains(m.TOKEN)) {
                        m.CODE = (D - (i + 1)) + m.CODE;
                    }
                }
            }
            return encodeVer;
        }
        return null;
    }

    /*
    Sorts Arraylist in decreasing order for next iteration of huffman code generation.
     */
    private ArrayList<Map> sort(ArrayList<Map> c){
        ArrayList<Map> output = new ArrayList<>();
        Map[] probsArray = new Map[c.size()];
        probsArray = c.toArray(probsArray);

        for (int i = 0; i < probsArray.length; i++) {
            for (int j = 0; j < probsArray.length - i - 1; j++) {
                if(probsArray[j].PROB < probsArray[j + 1].PROB) {
                    Map temp = probsArray[j];
                    probsArray[j] = probsArray[j+1];
                    probsArray[j+1] = temp;
                }
            }
        }
        for(int i = 0; i < probsArray.length; i++){
            output.add(probsArray[i]);
        }
        return output;
    }

    /*
    method finds the entropy of the code H = - Summation( p(x)log(p(x)) )
     */
    public double getEntropy(){
        double entropy = 0;
        for(Map m : PMF.myPmf){
            if(m.PROB > 0)
                entropy = entropy + m.PROB * (Math.log(m.PROB)/Math.log(D));
        }
        return -entropy;
    }

    /*
    method finds the theoretical length of the code (Shannon code method)
     */
    public double getTheoreticalLen(){
        double TheoreticalLen = 0;
        for(Map m : PMF.myPmf){
            TheoreticalLen = TheoreticalLen + m.PROB * Math.round(Math.log(1/m.PROB)/Math.log(D));
        }
        return TheoreticalLen;
    }
}
