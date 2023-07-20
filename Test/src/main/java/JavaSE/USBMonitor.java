package JavaSE;

import java.io.File;
import java.util.*;

public class USBMonitor {
    private static final String USB_PATH = "/Volumes/";

    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            private List<File> previousFiles;

            @Override
            public void run() {
                List<File> currentFiles = Arrays.asList(new File(USB_PATH).listFiles());

                if (previousFiles != null) {
                    List<File> newFiles = getNewFiles(previousFiles, currentFiles);
                    List<File> removedFiles = getRemovedFiles(previousFiles, currentFiles);

                    if (!newFiles.isEmpty()) {
                        // 处理插入U盘的操作
                        for (File file : newFiles) {
                            // 处理插入U盘后的操作，例如读取文件、拷贝文件等
                            System.out.println("新文件：" + file.getName());
                        }
                    }

                    if (!removedFiles.isEmpty()) {
                        // 处理拔出U盘的操作
                        for (File file : removedFiles) {
                            System.out.println("移除文件：" + file.getName());
                        }
                    }
                }

                previousFiles = currentFiles;
            }

            private List<File> getNewFiles(List<File> previous, List<File> current) {
                List<File> newFiles = new ArrayList<>(current);
                newFiles.removeAll(previous);
                return newFiles;
            }

            private List<File> getRemovedFiles(List<File> previous, List<File> current) {
                List<File> removedFiles = new ArrayList<>(previous);
                removedFiles.removeAll(current);
                return removedFiles;
            }
        }, 0, 1000);  // 每秒轮询一次
    }
}
