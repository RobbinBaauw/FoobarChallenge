public class Challenge1 {
    public static String solution(String x) {
        char[] chars = x.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            // 97 = a
            // 122 = z
            if (chars[i] >= 97 && chars[i] <= 122) {
                chars[i] = (char) (122 + 97 - chars[i]);
            }
        }
        return new String(chars);
    }
}
