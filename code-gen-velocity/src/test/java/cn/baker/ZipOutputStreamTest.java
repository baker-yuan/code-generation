package cn.baker;


import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipOutputStreamTest {

    @Test
    public void test_() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
        //
        StringWriter stringWriter = new StringWriter();
        stringWriter.append("main");
        // 添加到zip
        zipOutputStream.putNextEntry(new ZipEntry("Main.java"));
        IOUtils.write(stringWriter.toString(), zipOutputStream, "UTF-8");
        zipOutputStream.closeEntry();
        //
        zipOutputStream.putNextEntry(new ZipEntry("HeHe.java"));
        IOUtils.write(stringWriter.toString(), zipOutputStream, "UTF-8");
        zipOutputStream.closeEntry();
        //
        IOUtils.closeQuietly(stringWriter);
        IOUtils.closeQuietly(zipOutputStream);
        // 内容转为字节数组
        byte[] bytes = outputStream.toByteArray();
        //
        IOUtils.write(bytes, new FileOutputStream("t.zip"));
    }
}
