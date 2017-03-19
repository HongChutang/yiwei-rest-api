package person.practice.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import sun.misc.BASE64Decoder;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.util.Random;

/**
 * Created by Evan Hung on 2016/12/28.
 */
public class GeneImgHelper {
//    private static String path = "src/main/resources/static/file/";
    private static String path = "/usr/local/nginx/html/proj/yiwei/img/";

    //base64字符串转化成图片并返回图片路径
    public static String generateImage(String imgStr) {
        //去掉标志信息
        int index = imgStr.indexOf("base64")+7;
        String trueStr = imgStr.substring(index);
        //对字节数组字符串进行Base64解码并生成图片
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(trueStr);
            for(int i=0; i<b.length; ++i) {//调整异常数据
                if(b[i]<0) {
                    b[i]+=256;
                }
            }
            //生成jpeg图片
            String name = getRandomString()+".jpg";
            String imgFilePath = path + name;//相对路径
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return  "http://evanhung.me/proj/yiwei/img/" + name;
        }
        catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    //随机生成10位的字符串
    public static String getRandomString() {
        String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}
