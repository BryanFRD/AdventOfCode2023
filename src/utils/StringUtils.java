package utils;

public class StringUtils {

    public static String insertStringAt(String str, String strToInsert, int index){
        return str.substring(0, index) + strToInsert + str.substring(index);
    }

}
