package com.bgy.design_pattern.product;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

// 商品类
class Product {
    private String name;
    private int count;
    private long number;

    public Product(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public Product(String name, int count, long number) {
        this.name = name;
        this.count = count;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }
}

// 生产者
class Producer implements Runnable {
    private final List<Product> buffer; // 缓冲区
    private final int capacity; // 缓冲区容量
    private long number;

    public Producer(List<Product> buffer, int capacity) {
        this.buffer = buffer;
        this.capacity = capacity;
    }

    public Producer(List<Product> buffer, int capacity, long number) {
        this.buffer = buffer;
        this.capacity = capacity;
        this.number = number;
    }

    @Override
    public void run() {
        while (true) {
            //商品编号
            number++;
            // 生产商品
            Product product = new Product("商品", 1, number);
            // 等待缓冲区有空位
            synchronized (buffer) { // 使用同步块保证线程安全
                while (buffer.size() >= capacity) {
                    try {
                        buffer.wait(); // 等待缓冲区有空位
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // 生产商品入库
                buffer.add(product);
                System.out.println("生产者生产了商品：" + product.getName() + " 库存数量：" + buffer.size() + " 商品编号:" + product.getNumber());
                buffer.notifyAll(); // 唤醒所有等待的消费者线程
            }
            try {
                Thread.sleep(1000); // 模拟生产时间
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

// 消费者
class Consumer implements Runnable {
    private final List<Product> buffer; // 缓冲区
    String name;

    public Consumer(List<Product> buffer, String name) {
        this.buffer = buffer;
        this.name = name;
    }

    @Override
    public void run() {
        while (true) {
            // 等待缓冲区有商品
            synchronized (buffer) { // 使用同步块保证线程安全
                while (buffer.isEmpty()) {
                    try {
                        buffer.wait(); // 等待缓冲区有商品
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // 消费商品
                Product product = buffer.remove(0);
                System.out.println("name:" + name + " 消费者消费了商品：" + product.getName() + " 库存数量：" + buffer.size() + " 商品编号:" + product.getNumber());
                buffer.notifyAll(); // 唤醒所有等待的生产者线程
            }
            try {
                Thread.sleep(500); // 模拟消费时间
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class ProducerConsumer {
    public static void main(String[] args) {
        // 初始化缓冲区
        List<Product> buffer = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            buffer.add(new Product("商品_" + (i + 1), 1, (i + 1)));
        }
        // 设置缓冲区容量
        int capacity = 100;

        Semaphore semaphore = new Semaphore(1);
        AtomicLong atomicLong = new AtomicLong(1);

        // 创建生产者和消费者线程
        Producer producer = new Producer(buffer, capacity, 100);
        Consumer consumer = new Consumer(buffer, "consumer");
        Consumer consumerCopy = new Consumer(buffer, "consumerCopy");
        // 启动线程
        new Thread(producer).start();
        new Thread(consumer).start();
        new Thread(consumerCopy).start();
    }
}
