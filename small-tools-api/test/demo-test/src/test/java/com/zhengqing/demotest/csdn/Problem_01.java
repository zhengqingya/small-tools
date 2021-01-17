package com.zhengqing.demotest.csdn;

import java.util.Scanner;

/**
 * <p>
 * 解决for循环中加入has判断跳不出来问题： https://ask.csdn.net/questions/7395564
 * </p>
 *
 * @author : zhengqing
 * @description : 通过`input.nextLine();`解决; TODO 注意异常控制！ ex: input.nextInt()
 * @date : 2021/1/15 13:48
 */
public class Problem_01 {

    public static void main(String[] args) {
        String username = "admin";
        int password = 123;
        int result;

        System.out.println("\t\t欢迎使用我行我素购物管理系统！");
        System.out.println("\t\t1.登录系统");
        System.out.println("\t\t2.退出");
        System.out.println("***************************************");

        Scanner input = new Scanner(System.in);
        boolean flag = true;
        while (flag) {
            System.out.println("请选择，输入数字：");
            boolean inputContent = input.hasNextInt();
            if (!inputContent) {
                System.out.println("输入错误，请重新输入！");
                input.nextLine();
            }
            if (inputContent) {
                result = input.nextInt();
                switch (result) {
                    case 1:
                        for (;;) {
                            System.out.println("请输入账号：");
                            String usernameByInput = input.next();
                            System.out.println("请输入密码：");
                            int passwordByInput = input.nextInt();
                            if (usernameByInput.equals(username) && passwordByInput == password) {
                                flag = false;
                                System.out.println("登录成功...");
                                break;
                            } else {
                                System.out.println("用户名密码错误，请重新输入!");
                                input.nextLine();
                            }
                        }
                        break;
                    case 2:
                        System.out.println("欢迎下次光临！");
                        flag = false;
                        break;
                    default:
                        System.out.println("输入错误，请重新输入！");
                        input.nextLine();
                        break;
                }
            }
        }
    }

}
