package com.zhengqing.demo;

/**
 * <p> 模拟超卖问题 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/1/16 12:12 下午
 */
class ThreadMain {

    //总票数为 num 张
    public static Integer num = 200;

    public static void main(String[] args) {

        //模拟 i 个人订票
        for (int i = 0; i < 202; i++) {
            DemoThread thread = new DemoThread();
            thread.start();
        }

    }
}

/**
 * <p> java 创建线程之继承 Thread 方式 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/1/16 11:52 上午
 */
class DemoThread extends Thread {


    @Override
    public void run() {
        sellTicket1();
    }

    /**
     * 模拟售票,多线程下会出现线程安全问题 -- 会发生超卖
     */
    public void sellTicket1() {
        if (ThreadMain.num > 0) {
            ThreadMain.num--;
            System.out.println(Thread.currentThread().getName() + "抢票成功");
        } else {
            System.out.println(Thread.currentThread().getName() + "票已经售罄,不好意思");
        }
    }

    /**
     * 方法上增加java 内部锁(synchronized),这种方式是给当前实例对象加锁 -- 会发生超卖
     */
    public synchronized void sellTicket2() {
        if (ThreadMain.num > 0) {
            ThreadMain.num--;
            System.out.println(Thread.currentThread().getName() + "抢票成功");
        } else {
            System.out.println(Thread.currentThread().getName() + "票已经售罄,不好意思");
        }
    }

    /**
     * 方法上增加java 内部锁(synchronized)，这种方式是给当前类对象加锁 -- 不会发生超卖
     */
    public static synchronized void sellTicket3() {
        if (ThreadMain.num > 0) {
            ThreadMain.num--;
            System.out.println(Thread.currentThread().getName() + "抢票成功");
        } else {
            System.out.println(Thread.currentThread().getName() + "票已经售罄,不好意思");
        }
    }


}
