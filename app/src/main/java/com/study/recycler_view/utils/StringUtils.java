package com.study.recycler_view.utils;

import android.app.Application;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by BJM on 2016-09-20.
 */
public class StringUtils {

    public static String cutString(String str, String separator, boolean isLeft) {

        int index = indexOf(str, separator, 1);
        if (index < 0) {
            return str;
        } else if (isLeft) {
            return substr(str, 0, index);
        } else {
            return substr(str, index + 1, str.length());
        }
    }

    public static int indexOf(String str, String target, int seq) {
        int index = -1;
        int start = 0;

        if (seq < 0) {
            seq += getStringCount(str, target) + 1;
        }

        for (int i = 0; i < seq; i++) {
            index = str.indexOf(target, start);
            if (index == -1) {
                return -1;
            } else {
                start = index + 1;
            }
        }

        return index;
    }

    public static String substr(String str, int start, int length) {
        int len = str.length();

        int s = start >= 0 ? start : len + start;
        s = s < 0 ? 0 : (s > len ? len : s);

        int e = length >= 0 ? s + length : len + length;
        e = e < 0 ? 0 : (e > len ? len : e);

        return str.substring(s, e);
    }

    public static int getStringCount(String str, String target) {
        int count = 0;
        int start = 0;
        int end;
        while (true) {
            end = str.indexOf(target, start);
            if (end < 0) {
                break;
            }
            count++;
            start = end + target.length();
        }
        return count;
    }

    public static String[] split(String str, String separator) {
        ArrayList<String> list = new ArrayList<>();
        int start = 0;
        int end;
        while (true) {
            end = str.indexOf(separator, start);
            if (end < 0) {
                list.add(str.substring(start));
                break;
            }
            list.add(str.substring(start, end));
            start = end + separator.length();
        }
        return list.toArray(new String[]{});
    }


    public static String join(String[] strArray, String separator) {
        ArrayList<String> list = new ArrayList<>(Arrays.asList(strArray));
        StringBuffer buffer = new StringBuffer();
        for (int i = 0, cnt = list.size(); i < cnt; i++) {
            String str = list.get(i);
            if (str == null) {
                continue;
            }
            if (i == 0) {
                buffer.append(str);
            } else {
                buffer.append(separator + str);
            }
        }
        return buffer.toString();
    }

    public static boolean isEmpty(String str) {
        return (str == null || str.equals(""));
    }
}
