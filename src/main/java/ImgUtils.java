import org.jetbrains.annotations.Nullable;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author ht
 */
public class ImgUtils {
    @Nullable
    public static String downloadFile(String fileUrl, String saveUrl) {
        HttpURLConnection httpUrl = null;
        byte[] buf = new byte[1024];
        int size;
        try {
            //下载的地址
            URL url = new URL(fileUrl);
            //支持http特定功能
            httpUrl = (HttpURLConnection) url.openConnection();
            httpUrl.connect();
            //缓存输入流,提供了一个缓存数组,每次调用read的时候会先尝试从缓存区读取数据
            BufferedInputStream bis = new BufferedInputStream(httpUrl.getInputStream());
            File file = new File(saveUrl.substring(0, saveUrl.lastIndexOf("/")));
            //判断文件夹是否存在
            if (!file.exists()) {
                System.out.print(file + "\t目录不存在");
                //如果不存在就创建一个文件夹
                System.out.print("\t创建" + file + "\t" + file.mkdir());
            }
            //讲http上面的地址拆分成数组,
            String[] arrUrl = fileUrl.split("\\.");
            File imgSrc = new File(saveUrl + "." + arrUrl[arrUrl.length - 1]);
            if (!imgSrc.exists()) {
                //输出流,输出到新的地址上面
                FileOutputStream fos = new FileOutputStream(imgSrc);
                while ((size = bis.read(buf)) != -1) {
                    fos.write(buf, 0, size);
                }
                //记得及时释放资源
                fos.close();
                System.out.println(imgSrc + "\t已经保存");
            } else {
                System.out.println(imgSrc + "\t已经存在");
            }
            bis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (httpUrl != null) {
            httpUrl.disconnect();
        }
        return null;
    }
}
