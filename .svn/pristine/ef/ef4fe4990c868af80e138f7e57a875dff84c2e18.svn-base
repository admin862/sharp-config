package com.dafy.config.client.utils;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Collections;
import java.util.List;

/**
 *
 * 查找目录下符合文件命名规则的文件，并按照最后修改日期拍寻
 *
 * Created by de on 2017/4/12.
 */
public class FileFinder{

    private static final Logger LOG = LoggerFactory.getLogger(FileFinder.class);
    private String fileName;
    private String root;
    private final List<FileEx> fileList = Lists.newArrayList();

    static class FileEx implements Comparable<FileEx> {

        private File file;
        private FileTime fileTime;

        public FileEx(File file, FileTime fileTime) {
            this.file = file;
            this.fileTime = fileTime;
        }

        public File getFile() {
            return file;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            FileEx fileEx = (FileEx) o;

            if (file != null ? !file.equals(fileEx.file) : fileEx.file != null) return false;
            return fileTime != null ? fileTime.equals(fileEx.fileTime) : fileEx.fileTime == null;
        }

        @Override
        public int hashCode() {
            int result = file != null ? file.hashCode() : 0;
            result = 31 * result + (fileTime != null ? fileTime.hashCode() : 0);
            return result;
        }

        @Override
        public int compareTo(FileEx o) {
            return this.fileTime.compareTo(o.fileTime);
        }
    }

    public FileFinder(String root, String fileName) {
        this.fileName = fileName;
        this.root = root;
    }

    public void walk() throws IOException {
        Files.walkFileTree(Paths.get(this.root), new FileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                File f = file.toFile();
                if (f.isFile() && f.getName().startsWith(fileName)) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Got File [{}].", f.getAbsoluteFile().toString());
                    }
                    fileList.add(new FileEx(f, attrs.lastModifiedTime()));
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public File lastFile() {
        if (!this.fileList.isEmpty())
            return getSortedFileList().get(this.fileList.size() - 1);
        else
            return null;
    }

    public List<File> getFileList() {
        List<File> fileList = Lists.newArrayList();
        for (FileEx fileEx : this.fileList) {
            fileList.add(fileEx.getFile());
        }
        return Collections.unmodifiableList(fileList);
    }

    public List<File> getSortedFileList() {
        Collections.sort(this.fileList);
        return getFileList();
    }

}
