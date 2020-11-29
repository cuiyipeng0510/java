package com.cuiyp.demo;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;

public class TestXXDemo {
    public static float compz = 0.53f;
    public static float compf = -0.53f;

    public static void main(String[] args) throws Exception {

        File file = new File("D:\\16chol");
        File[] files = file.listFiles();
        for (File text : files) {
            deleFile(text, 3);
        }
    }

    public static void deleFile(File file, int index) throws Exception {
        int count = index -1;
        String name = file.getName();
        String test = FileUtils.readFileToString(file, "utf-8");
        String[] split = StringUtils.split(test, "\r\n");
        StringBuilder gth5 = new StringBuilder();
        String[] split2 = split[0].split("   ");
        float floatBoolean = Float.valueOf(split2[count]);
        boolean iszs = true;
        boolean isfs = true;
        if (floatBoolean > 0) {
            isfs = false;
        } else {
            iszs = false;
        }
        for (String str : split) {
            String[] split1 = str.split("   ");
            float aFloat = Float.valueOf(split1[count]);
            if (aFloat >= compz && iszs) {
                appendStr(gth5, split1);
                iszs = false;
                isfs = true;
            }

            if (compf >= aFloat && isfs) {
                appendStr(gth5, split1);
                iszs = true;
                isfs = false;
            }
        }
        FileUtils.writeStringToFile(new File("D:\\16shouji\\"+ name + "正负循环.txt"), gth5.toString(), "utf-8");
    }

    public static void appendStr(StringBuilder gth5,String[] split1){
        for (int i = 0; i < split1.length; i++) {
            if(i == (split1.length - 1)){
                gth5.append(split1[i]).append("\r\n");
            }else {
                gth5.append(split1[i]).append("\t");
            }
        }
    }
}
