package org.wenruo.util.threadpool;

import lombok.SneakyThrows;

public class ResizeableB {
    static ResizeableCapacityLinkedBlockingQueue<Integer> queue = new ResizeableCapacityLinkedBlockingQueue<>(3);
    public static void main(String[] args)throws Exception {
       new Thread(new Runnable() {
           @SneakyThrows
           @Override
           public void run() {
               for (int i=0;i<5;i++){
                   System.out.println("pre put "+i);
                   queue.put(i);
                   System.out.println("put "+i+"  ");
               }
           }
       }).start();

        Thread.sleep(1000);
        System.out.println("调整capacity");
        queue.setCapacity(10);
        Thread.sleep(1000);
        for (int i=10;i<15;i++){
            System.out.println("pre put "+i);
            boolean b = queue.offer(i);
            System.out.println("put "+i+"  "+b);
        }
    }
}
