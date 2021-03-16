

import java.io.File;
import java.io.FileInputStream;
import java.util.Base64;

public class ImgToBase64Convertor {

  public ImgToBase64Convertor() {
    // TODO Auto-generated constructor stub
  }

  public static void main(String[] args) throws Exception {

    File file = new File("/Users/gmtdevelopment/sajid_photo_sm.jpg");
    FileInputStream fileInputStreamReader = new FileInputStream(file);
    byte[] bytes = new byte[(int) file.length()];
    fileInputStreamReader.read(bytes);
    System.out.println("data:image/jpeg;base64," + new String(Base64.getEncoder().encode(bytes), "UTF8"));

    fileInputStreamReader.close();
  }

}
