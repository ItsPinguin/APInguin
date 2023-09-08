package ping.apinguin.utils;

public class StringUtils {
  public static String enhancedDouble(double value) {
    if ((int) value == value) {
      if (value <= 0) {
        return "" + (int) value;
      } else {
        return "+" + (int) value;
      }
    }
    if (value <= 0) {
      return "" + value;
    } else {
      return "+" + value;
    }
  }
}
