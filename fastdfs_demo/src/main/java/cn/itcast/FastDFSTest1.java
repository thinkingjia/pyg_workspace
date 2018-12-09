package cn.itcast;

import org.csource.fastdfs.*;

import java.io.FileOutputStream;

public class FastDFSTest1 {

    public static void main(String[] args) throws Exception{
//        把图片放到fastDFS服务器上，返回一个url地址
//        七步骤：
//        1、加载配置文件，配置文件中的内容就是 tracker 服务的地址。ClientGlobal加载
        ClientGlobal.init("D:\\class69\\workspace\\fastdfs_demo\\src\\main\\resources\\fdfs_client.conf");
//        2、创建一个 TrackerClient 对象。直接 new 一个
        TrackerClient trackerClient = new TrackerClient();
//        3、使用 TrackerClient 对象获取连接，获得一个 TrackerServer 对象
        TrackerServer trackerServer = trackerClient.getConnection();
//        4、创建一个 StorageServer 的引用，值为 null
        StorageServer storageServer = null;
//        5、创建一个 StorageClient 对象，需要两个参数 TrackerServer 对象、StorageServer 的引用
        StorageClient storageClient1 = new StorageClient(trackerServer,  storageServer);
//        6、使用 StorageClient 对象上传图片
        String[] urls = storageClient1.upload_file("C:\\Users\\syl\\Pictures\\b1.jpg", "jpg", null);
//        7、返回数组。包含组名和图片的路径
//        for (String url : urls) {
//            System.out.println(url);
//        }
//        group1
//        M00/00/00/wKgZhVv7oZOAVvHrAAFP0yQoHiA445.jpg
//        group1
//        M00/00/00/wKgZhVv7mkmAHXbkAAFP0yQoHiA761.jpg
//        int group1 = storageClient1.delete_file("group1", "M00/00/00/wKgZhVv7oW2ASIZOAAFP0yQoHiA428.jpg"); //删除
//        storageClient1.download_file("group1", "M00/00/00/wKgZhVv7oZOAVvHrAAFP0yQoHiA445.jpg",
//                "C:\\Users\\syl\\Pictures\\bbbb11111.jpg");

//        byte[] bytes = storageClient1.download_file("group1", "M00/00/00/wKgZhVv7oZOAVvHrAAFP0yQoHiA445.jpg");
//        FileOutputStream pic = new FileOutputStream("C:\\Users\\syl\\Pictures\\aab11.jpg");
//        pic.write(bytes);

//        FileInfo group1 = storageClient1.get_file_info("group1", "M00/00/00/wKgZhVv7oZOAVvHrAAFP0yQoHiA445.jpg");
//        System.out.println(group1); //source_ip_addr = 192.168.25.133, file_size = 85971, create_timestamp = 2018-11-26 15:01:29, crc32 = 606608928

//        int group2 = storageClient1.truncate_file("group1", "M00/00/00/wKgZhVv7oZOAVvHrAAFP0yQoHiA445.jpg");
//        System.out.println(group2); //不知道这个方法是干什么的

        int group1 = storageClient1.append_file("group1", "M00/00/00/wKgZhVv7oZOAVvHrAAFP0yQoHiA445.jpg", "C:\\Users\\syl\\\\Pictures\\T1.jpg");
    }
}
