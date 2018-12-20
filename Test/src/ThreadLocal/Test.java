package ThreadLocal;

import java.util.Scanner;

/**
 * @Author: clf
 * @Date: 18-12-17
 * @Description:
 */
public class Test {

    public static void main(String[] args) {
        int[][] a = new int[3][2];
        int[][] b = new int[2][3];
        int[][] c = new int[3][3];
        Scanner sc = new Scanner(System.in);

        for (int i = 0; i < a.length; i ++){
            for (int j = 0; j < a[i].length; j++) {
                a[i][j] = sc.nextInt();
            }
        }

//        for (int i = 0; i < b.length; i ++){
//            for (int j = 0; j < b[i].length; j++) {
//                b[i][j] = sc.nextInt();
//            }
//        }
//
//        for (int i = 0; i < a.length; i++) {
//            System.out.println();
//            for (int j = 0; j < a[i].length; j++) {
//                System.out.print(a[i][j]);
//            }
//        }
//        for (int i = 0; i < b.length; i++) {
//            System.out.println();
//            for (int j = 0; j < b[i].length; j++) {
//                System.out.print(b[i][j]);
//            }
//
//        }
//
//

        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++) {
                c[i][j] = 0;
                for (int k = 0; k < 2; k++) {
                    c[i][j] += a[i][k] * b[k][j];
                }
            }
        }

        for (int i = 0; i < c.length; i++) {
            System.out.println();
            for (int j = 0; j < c[i].length; j++) {
                System.out.print(c[i][j]);
            }
        }

    }


}



