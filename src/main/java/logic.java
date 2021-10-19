import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;
import java.util.*;

public class logic {


    public static List<String> inicialization(String str)
    {
        ArrayList<String> myArrayList = new ArrayList<String>();
    return myArrayList;
    }



    public static String gettime(){
        Date j = new Date();
        String[] g =j.toString().split(" ");
        String[] f =g[3].split(":");
        return f[0]+":"+ f[1];
    }
}
