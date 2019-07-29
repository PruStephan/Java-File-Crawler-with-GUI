package Utils;

import java.util.ArrayList;

public class TextSearch {

    private static ArrayList<Integer> prefix = new ArrayList<>();
    private static String pattern;

    TextSearch(String pattern) {
        this.pattern = pattern;
        prefixFunction(pattern);
    }


    private static void prefixFunction(String s) {
      ArrayList<Integer> p = new ArrayList<>();
        int k = 0;
        for (int i = 1; i < s.length(); i++) {
            while (k > 0 && s.charAt(k) != s.charAt(i))
                k = p.get(k - 1);
            if (s.charAt(k) == s.charAt(i))
                ++k;
            p.add(k);
        }
        prefix = p;
    }

    public static int kmpMatcher(String s) {
        int m = pattern.length();
        if (m == 0)
            return 0;
        for (int i = 0, k = 0; i < s.length(); i++)
            for (; ; k = prefix.get(k - 1)) {
                if (pattern.charAt(k) == s.charAt(i)) {
                    if (++k == m)
                        return i + 1 - m;
                    break;
                }
                if (k == 0)
                    break;
            }
        return -1;
    }
}
