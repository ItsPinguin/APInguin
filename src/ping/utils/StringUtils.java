package ping.utils;

public class StringUtils {
    public static String valueToString(double value){
        if(value<=0){
            return ""+value;
        }else {
            return "+"+value;
        }
    }
}
