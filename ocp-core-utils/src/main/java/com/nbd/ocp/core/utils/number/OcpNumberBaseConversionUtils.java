package com.nbd.ocp.core.utils.number;
/*
                       _ooOoo_
                      o8888888o
                      88" . "88
                      (| -_- |)
                      O\  =  /O
                   ____/`---'\____
                 .'  \\|     |//  `.
                /  \\|||  :  |||//  \
               /  _||||| -:- |||||-  \
               |   | \\\  -  /// |   |
               | \_|  ''\---/''  |   |
               \  .-\__  `-`  ___/-. /
             ___`. .'  /--.--\  `. . __
          ."" '<  `.___\_<|>_/___.'  >'"".
         | | :  `- \`.;`\ _ /`;.`/ - ` : | |
         \  \ `-.   \_ __\ /__ _/   .-` /  /
    ======`-.____`-.___\_____/___.-`____.-'======
                       `=---='
    ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
             佛祖保佑       永无BUG
*/


import java.util.Stack;

public class OcpNumberBaseConversionUtils {

    public static void main(String[] args) {
        System.out.println("10进制与64进制互转类");
        for(int i=0;i<64;i++){
            System.out.println(decode(encode(i)));
        }

    }

    public static final char[] array = {  '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9','A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
            'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
            'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' , '+', '-'};
    /**
     * 编码,从10进制转换到64进制
     *
     * @param number
     *            long类型的10进制数,该数必须大于0
     * @return string类型的64进制数
     */
    public static String encode(long number) {
        Long rest = number;
        // 创建栈
        Stack<Character> stack = new Stack<Character>();
        StringBuilder result = new StringBuilder(0);
        while (rest >= 0) {
            // 进栈,
            // 也可以使用(rest - (rest / 64) * 64)作为求余算法
            stack.add(array[new Long(rest % 64).intValue()]);
            rest = rest / 64;
            if(rest==0){
                break;
            }
        }
        for (; !stack.isEmpty();) {
            // 出栈
            result.append(stack.pop());
        }
        return result.toString();

    }

    /**
     * 解码,从64进制解码到10进制
     *
     * @param str
     *            string类型的64进制数A-Z,a-z,0-9,+,-
     * @return long类型的10进制数
     */
    public static long decode(String str) {
        // 倍数
        int multiple = 1;
        long result = 0;
        Character c;
        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(str.length() - i - 1);
            result += decodeChar(c) * multiple;
            multiple = multiple * 64;
        }
        return result;
    }

    /**
     * 比对数组,得到字符对应的值
     * @param c 64位字符
     * @return
     */
    private static int decodeChar(Character c) {
        for (int i = 0; i < array.length; i++) {
            if (c == array[i]) {
                return i;
            }
        }
        return -1;
    }
}
