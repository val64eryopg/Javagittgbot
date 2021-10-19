import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;
import java.util.Date;
import java.util.List;

public class logic {


//    public static List<String> inicialization(String str){
//        List<String> d;
//        d.add(str);
//
//        return d;
//    }

    public static String gettime(){
        Date j = new Date();
        String[] g =j.toString().split(" ");
        String[] f =g[3].split(":");
        return f[0]+":"+ f[1];
    }
}
