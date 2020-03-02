package com.ping;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.*;
import java.nio.channels.FileChannel;

public class FileDoByNIO {
    @Test
    public void ReadFile() throws Exception {
        File file=new File("E:\\envi视频\\2.txt");
        //1.创建输出流
        FileInputStream is=new FileInputStream(file);
        //从流中得到通道
        FileChannel fe = is.getChannel();
        //3.创建缓冲区
        ByteBuffer buf = ByteBuffer.allocate((int) file.length());
        //4.从通道读数据写入缓冲区
         fe.read(buf);
        //5.重置初始位置
        System.out.println(new String(buf.array()));
        //6.关闭
       is.close();

    }
    @Test
    public void WriteFile() throws Exception{
        //1.创建输出流
        FileOutputStream os=new FileOutputStream("E:\\envi视频\\2.txt");
        //从流中得到通道
        FileChannel fe = os.getChannel();
        //3.创建缓冲区
        ByteBuffer buf = ByteBuffer.allocate(2048);
        //4.往缓冲区写数据
        String s="hello nio";
        buf.put(s.getBytes());
        //5.重置初始位置
        buf.flip();
        //6.写入
        fe.write(buf);
        //7.关闭
        os.close();
    }
    @Test
    public void CopyFileByNIO() throws Exception{
        //1.创建流
        FileInputStream is=new FileInputStream("E:\\envi视频\\2.txt");
        FileOutputStream os=new FileOutputStream("E:\\envi视频\\3.txt");
        //2.得到两个tongdao
        FileChannel Ischannel = is.getChannel();
        FileChannel Oschannel = os.getChannel();
        //3.复制
        Oschannel.transferFrom(Ischannel,0,Ischannel.size());
        //4.关闭
        is.close();
        os.close();
    }

}
