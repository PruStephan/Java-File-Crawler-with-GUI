package Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public class Crawler {

    private static TextSearch textSearch;
    private static ArrayList<Path> files = new ArrayList<>();

    public static boolean FileSearch(Path path, String text) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(path.toString()));
        char[] oldBuffer = new char[1024];
        char[] curBuffer = new char[1024];

        br.read(oldBuffer);
        while(br.read(curBuffer) != -1) {
            if(textSearch.kmpMatcher(new String(oldBuffer) + new String(curBuffer)) != -1) {
                //tree.children.add(new Tree(path.getFileName().toString(), new ArrayList<>()));
                return true;
            }
            oldBuffer = curBuffer;
        }
        if(textSearch.kmpMatcher(new String(oldBuffer) + new String(curBuffer)) != -1) {
            //tree.children.add(new Tree(path.getFileName().toString(), new ArrayList<>()));
            return true;
        }
        return false;
    }

    public static boolean crawlerDfs(Path path, String pattern, String extension, Tree tree) {
        File file = new File(path.toString());
        if(file.isDirectory()) {
            File[] fileList = file.listFiles();
            boolean ans = false;
            if(fileList != null) {
                for (File file1 : fileList) {
                    Tree cur = new Tree(file1.getName(), new ArrayList<>());
                    if (crawlerDfs(file1.toPath(), pattern, extension, cur)) {
                        tree.children.add(cur);
                        ans = true;
                    }
                }
                return ans;
            }
        }
        else {
            if(file.getPath().endsWith("."  + extension)) {
                try  {
                    if(FileSearch(path, pattern)) {
                        //tree.children.add(new Tree(path.getFileName().toString(), new ArrayList<>()));
                        return true;
                    }
                    return false;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public static Tree crawl(String path, String pattern, String extension) {
        textSearch = new TextSearch(pattern);
        Tree root = new Tree(path, new ArrayList<>());
        crawlerDfs(new File(path).toPath(), pattern, extension, root);

        for(Path file : files) {
            System.out.println(file);
        }
        return root;
    }
}
