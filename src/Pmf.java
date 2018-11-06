import java.util.ArrayList;

public class Pmf{
    ArrayList<Map> myPmf = new ArrayList<>();

    void add(Map m){
        myPmf.add(m);
    }

    public String toString(){
        String total = "";
        for(Map m : myPmf){
            total += m.TOKEN + " -> " + m.PROB +" -> "+ m.CODE + "\n";
        }
        return total;
    }

    public double getAveLength(){
        double expectedLen = 0;
        for(Map m : myPmf){
            expectedLen = expectedLen + m.PROB * m.CODE.length();
        }
        return expectedLen;
    }

}
