### File 类

```java
/**
 * 路径：
 *      绝对路径： 是一个完整的路径
 *          以盘符开始：
 *              c:\\a.txt
 *              c:\\user\\text\\cuiyp\\a.txt
 *      相对路径： 是一个简化的路径
 *          相对 指的是相对于当前项目的根目录（c:\\user\\text\\cuiyp）
 *          如果使用当前项目的根目录，路径可以简化书写
 *          c:\\user\\text\\cuiyp\\a.txt  可以简化为 a.txt （可以省略项目的根目录）
 *   注意：
 *      1. 路径是不区分大写小的
 *      2. 路径中的文件名称分隔符windows使用反斜杠、反斜杠是转移符、两个反斜杠表示一个普通的反斜杠
 */
public class DemoFiel {
    public static void main(String[] args) {

        // windows 反斜杠 \  linux 正斜杠 /
        String separator = File.separator;
        char pathSeparatorChar = File.pathSeparatorChar;
        // windows 分号 ;  linux 冒号 :
        String pathSeparator = File.pathSeparator;


    }
}

File file = new File("a.txt");
//获取文件内容的 字节数
file.length();

// 在不确定当前文件目录存在的情况下 创建文件需要先创建文件目录
file.mkdirs();
// 在 new 新的file对象 创建文件
file1.createNewFile();
// File 对象遍历某个目录下文件夹 或文件
file.li
// 过滤
```

