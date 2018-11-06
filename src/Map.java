public class Map{
    String TOKEN;
    double PROB;
    String CODE = "";

    Map(String token, double probability){
        TOKEN = token;
        PROB = probability;
    }

    void concat(Map other){
        TOKEN = TOKEN + other.TOKEN;
        PROB = Math.floor((PROB + other.PROB) * 100)/100;
    }

}
