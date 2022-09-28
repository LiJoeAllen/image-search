import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Tests {

    public static void main(String[] args) throws IOException {
        //要遍历的路径
        String path = "src/main/resources/";
        //获取其file对象
        File file = new File(path);
        func(file);
    }

    private static void func(@NotNull File file) throws IOException {
        File[] fs = file.listFiles();
        if (fs != null) {
            for (File f : fs) {
                //若是目录，则递归打印该目录下的文件
                if (f.isDirectory()) func(f);
                //若是文件，直接打印
                if (f.isFile()) {
                    if (f.getName().contains("json")) continue;
                    FileWriter fileWritter = new FileWriter("README.md", true);
                    BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
                    bufferWritter.write("![输入图片说明](" + f + " \"在这里输入图片标题\")" + "\n");
                    bufferWritter.close();
                }
            }
        }
    }
}
