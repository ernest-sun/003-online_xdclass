package net.xdclass.online_xdclass.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * ClassName:CommonUtils Package: Description:
 *
 * @Date:2021/1/28 14:58
 * @Author:sunzheng@bmrj.com
 */
public class CommonUtils {

  public static String MD5(String data) {
    try {
      MessageDigest md =
          MessageDigest.getInstance("MD5");
      byte[] array = md.digest(data.getBytes(StandardCharsets.UTF_8));
      StringBuilder sb = new StringBuilder();
      for (byte item : array) {
        sb.append(Integer.toHexString((item & 0xFF) |
            0x100).substring(1, 3));
      }
      return sb.toString().toUpperCase();
    } catch (Exception exception) {
    }
    return null;
  }
}
