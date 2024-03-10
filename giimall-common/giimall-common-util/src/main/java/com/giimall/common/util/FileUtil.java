package com.giimall.common.util;


import com.giimall.common.constant.CharsetConstant;
import com.giimall.common.constant.MediaTypeConstant;
import com.giimall.common.constant.SymbolConstant;
import com.giimall.common.exception.CommonException;
import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文件相关工具类
 *
 * @author wangLiuJing
 * Created on 2019/9/27
 */
public class FileUtil {

    private static Pattern pattern;
    /**
     * 获取程序运行工程路径，如果在jar包运行则返回jar包所在的目录，如果不在jar包运行则返回工程根目录
     *
     * @return the projectPath (type String) of this FileUtil object.
     * @throws URISyntaxException when
     * @author wangLiuJing
     * Created on 2021/10/9
     */
    @SneakyThrows
    public static String getProjectPath() {
        // 程序运行的Jar包路径
        String runningJarPath = FileUtil.class.getProtectionDomain().getCodeSource().getLocation().toURI()
                .getSchemeSpecificPart();
        if (SystemUtil.isRunInJar()) {
            if(pattern == null){
                pattern = Pattern.compile("file:(.+)/.+\\.jar!/BOOT-INF/classes!/");
            }
            Matcher matcher = pattern.matcher(runningJarPath);
            if (matcher.find()) {
                return StringUtil.join(matcher.group(1), File.separator);
            }
            return null;
        } else {
            return runningJarPath.replace("target/classes/", "");
        }
    }

    /**
     * 获取文件mimeType
     *
     * @param filename of type String
     * @return String
     * @author wangLiuJing
     * Created on 2019/9/27
     */
    public static String getFileMimeType(String filename) {
        try {
            String contentType = Files.probeContentType(Paths.get(filename));
            if (contentType != null) {
                return contentType;
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("获取文件MimeType失败", e);
        }
        // 获取后缀
        String[] split = StringUtil.split(filename, SymbolConstant.POINT);
        String extension = split[split.length - 1];
        return MimeTypeEnum.getByExtension(extension).getMimeType();
    }

    /**
     * 获取图片dateUrl  "data:image/png;base64," + Base64.encode(file.getBytes())
     *
     * @param bytes    of type byte[]
     * @param mimeType of type String
     * @return String
     * Created on 2019/9/27
     */
    public static String getDateUrl(byte[] bytes, String mimeType) {
        StringBuilder sb = new StringBuilder("data:");
        sb.append(mimeType);
        sb.append(";base64,");
        sb.append(new String(Base64Utils.encode(bytes)));
        return sb.toString();
    }

    /**
     * 将网络图片转成BASE64   "data:image/png;base64," + Base64.encode(file.getBytes())
     *
     * @param fileUrl  of type String
     * @param mimeType of type String
     * @return String
     * @author wangLiuJing
     * Created on 2019/9/27
     */
    public static String imageToBase64(String fileUrl, String mimeType) {
        StringBuilder sb = new StringBuilder("data:");
        try {
            URL url = new URL(fileUrl);
            sb.append(mimeType);
            sb.append(";base64,");
            byte[] bytes = encodeImageToBase64(url);
            sb.append(new String(Base64Utils.encode(bytes)));
        } catch (IOException e) {
            e.printStackTrace();
            throw new CommonException("图片转换失败!");
        }
        return sb.toString();
    }

    /**
     * 获取图片dateUrl  "data:image/png;base64," + Base64.encode(file.getBytes())
     *
     * @param file of type MultipartFile
     * @return String
     * @author wangLiuJing
     * Created on 2019/9/27
     */
    public static String getDateUrl(MultipartFile file) {
        try {
            return getDateUrl(file.getBytes(), file.getContentType());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("获取DateUrl失败", e);
        }
    }

    /**
     * 目录分离算法
     *
     * @param fileName of type String
     * @param level    of type int 最大值为7级目录 8级目录为0没有意义
     * @return StringBuilder
     * @author wangLiuJing
     * Created on 2020/1/16
     */
    public static StringBuilder getPath(String fileName, int level) {
        if (level > 7) {
            throw new CommonException("目录层级不能大于7");
        }
        int hash = fileName.hashCode();
        StringBuilder path = new StringBuilder();
        for (int i = 0; i < level; i++) {
            if (path.length() > 0) {
                path.append(SymbolConstant.SLASH);
            }
            // hash & 0xf 后,得到0-15中的一个整数
            int dir = hash & 0xf;
            // 将 hash 码无符号右移 4 位(一个整数最多右移8次就归0)
            hash = hash >>> 4;
            // 字符串拼接
            path.append(dir);
        }
        path.append(SymbolConstant.SLASH);
        return path;
    }


    /**
     * 将网络图片编码为base64 byte[]
     *
     * @param url
     * @return byte[]
     */
    public static byte[] encodeImageToBase64(URL url) {
        //打开链接
        HttpURLConnection conn;
        try {
            conn = (HttpURLConnection) url.openConnection();
            //设置请求方式为"GET"
            conn.setRequestMethod(HttpMethod.GET.name());
            //超时响应时间为5秒
            conn.setConnectTimeout(5 * 1000);
            //通过输入流获取图片数据
            InputStream inStream = conn.getInputStream();
            //得到图片的二进制数据，以二进制封装得到数据，具有通用性
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            //创建一个Buffer字符串
            byte[] buffer = new byte[1024];
            //每次读取的字符串长度，如果为-1，代表全部读取完毕
            int len = -1;
            //使用一个输入流从buffer里把数据读取出来
            while ((len = inStream.read(buffer)) != -1) {
                //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
                outStream.write(buffer, 0, len);
            }
            //关闭输入流
            inStream.close();
            return outStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new CommonException("图片下载失败!");
        }
    }

    /**
     * 下载文件
     *
     * @param filename    of type String
     * @param response    of type HttpServletResponse
     * @param inputStream of type InputStream
     * @author wangLiuJing
     * Created on 2019/11/26
     */
    @SneakyThrows
    public static void downLoadFile(String filename, HttpServletResponse response,
                                    InputStream inputStream) {
        filename = URLEncoder.encode(filename,
                CharsetConstant.CHARSET_UTF_8);
        response.setCharacterEncoding(CharsetConstant.CHARSET_UTF_8);
        response.setContentType(MediaTypeConstant.APPLICATION_OCTET_STREAM);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename*=utf-8''" + filename);
        IOUtils.copy(inputStream, response.getOutputStream());
    }

    /**
     * 读取txt文件
     *
     * @param file of type File
     * @return String
     * @throws IOException when
     * @author zhanghao
     * Created on 2020/10/17
     */
    public static String readTxt(File file) throws IOException {
        String s = "";
        InputStreamReader in = new InputStreamReader(new FileInputStream(file), "UTF-8");
        BufferedReader br = new BufferedReader(in);
        StringBuffer content = new StringBuffer();
        while ((s = br.readLine()) != null) {
            content = content.append(s);
        }
        return content.toString();
    }

    /**
     * 获取类路径下的资源
     *
     * @param classpath of type String  例如：classpath:abc.yaml
     * @return Resource
     * @author wangLiuJing
     * Created on 2021/9/1
     */
    public static Resource getClasspathResource(String classpath) {
        FileSystemResourceLoader fileSystemResourceLoader = new FileSystemResourceLoader();
        return fileSystemResourceLoader.getResource(classpath);
    }


    /**
     * 获取文件对象
     *
     * @param classpath of type String    例如：classpath:abc.yaml
     * @return File
     * @author wangLiuJing
     * Created on 2021/9/1
     */
    public static File getFile(String classpath) throws IOException {
        Resource resource = getClasspathResource(classpath);
        if (resource.exists()) {
            return resource.getFile();
        }
        return null;
    }

    @Getter
    public enum MimeTypeEnum {
        AAC("acc", "AAC音频", "audio/aac"),

        ABW("abw", "AbiWord文件", "application/x-abiword"),

        ARC("arc", "存档文件", "application/x-freearc"),

        AVI("avi", "音频视频交错格式", "video/x-msvideo"),

        AZW("azw", "亚马逊Kindle电子书格式", "application/vnd.amazon.ebook"),

        BIN("bin", "任何类型的二进制数据", "application/octet-stream"),

        BMP("bmp", "Windows OS / 2位图图形", "image/bmp"),

        BZ("bz", "BZip存档", "application/x-bzip"),

        BZ2("bz2", "BZip2存档", "application/x-bzip2"),

        CSH("csh", "C-Shell脚本", "application/x-csh"),

        CSS("css", "级联样式表（CSS）", "text/css"),

        CSV("csv", "逗号分隔值（CSV）", "text/csv"),

        DOC("doc", "微软Word文件", "application/msword"),

        DOCX("docx", "Microsoft Word（OpenXML）", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"),

        EOT("eot", "MS Embedded OpenType字体", "application/vnd.ms-fontobject"),

        EPUB("epub", "电子出版物（EPUB）", "application/epub+zip"),

        GZ("gz", "GZip压缩档案", "application/gzip"),

        GIF("gif", "图形交换格式（GIF）", "image/gif"),

        HTM("htm", "超文本标记语言（HTML）", "text/html"),

        HTML("html", "超文本标记语言（HTML）", "text/html"),

        ICO("ico", "图标格式", "image/vnd.microsoft.icon"),

        ICS("ics", "iCalendar格式", "text/calendar"),

        JAR("jar", "Java存档", "application/java-archive"),

        JPEG("jpeg", "JPEG图像", "image/jpeg"),

        JPG("jpg", "JPEG图像", "image/jpeg"),

        JS("js", "JavaScript", "text/javascript"),

        JSON("json", "JSON格式", "application/json"),

        JSONLD("jsonld", "JSON-LD格式", "application/ld+json"),

        MID("mid", "乐器数字接口（MIDI）", "audio/midi"),

        MIDI("midi", "乐器数字接口（MIDI）", "audio/midi"),

        MJS("mjs", "JavaScript模块", "text/javascript"),

        MP3("mp3", "MP3音频", "audio/mpeg"),

        MPEG("mpeg", "MPEG视频", "video/mpeg"),

        MPKG("mpkg", "苹果安装程序包", "application/vnd.apple.installer+xml"),

        ODP("odp", "OpenDocument演示文稿文档", "application/vnd.oasis.opendocument.presentation"),

        ODS("ods", "OpenDocument电子表格文档", "application/vnd.oasis.opendocument.spreadsheet"),

        ODT("odt", "OpenDocument文字文件", "application/vnd.oasis.opendocument.text"),

        OGA("oga", "OGG音讯", "audio/ogg"),

        OGV("ogv", "OGG视频", "video/ogg"),

        OGX("ogx", "OGG", "application/ogg"),

        OPUS("opus", "OPUS音频", "audio/opus"),

        OTF("otf", "otf字体", "font/otf"),

        PNG("png", "便携式网络图形", "image/png"),

        PDF("pdf", "Adobe 可移植文档格式（PDF）", "application/pdf"),

        PHP("php", "php", "application/x-httpd-php"),

        PPT("ppt", "Microsoft PowerPoint", "application/vnd.ms-powerpoint"),

        PPTX("pptx", "Microsoft PowerPoint（OpenXML）", "application/vnd.openxmlformats-officedocument.presentationml.presentation"),

        RAR("rar", "RAR档案", "application/vnd.rar"),

        RTF("rtf", "富文本格式", "application/rtf"),

        SH("sh", "Bourne Shell脚本", "application/x-sh"),

        SVG("svg", "可缩放矢量图形（SVG）", "image/svg+xml"),

        SWF("swf", "小型Web格式（SWF）或Adobe Flash文档", "application/x-shockwave-flash"),

        TAR("tar", "磁带存档（TAR）", "application/x-tar"),

        TIF("tif", "标记图像文件格式（TIFF）", "image/tiff"),

        TIFF("tiff", "标记图像文件格式（TIFF）", "image/tiff"),

        TS("ts", "MPEG传输流", "video/mp2t"),

        TTF("ttf", "ttf字体", "font/ttf"),

        TXT("txt", "文本（通常为ASCII或ISO 8859- n", "text/plain"),

        VSD("vsd", "微软Visio", "application/vnd.visio"),

        WAV("wav", "波形音频格式", "audio/wav"),

        WEBA("weba", "WEBM音频", "audio/webm"),

        WEBM("webm", "WEBM视频", "video/webm"),

        WEBP("webp", "WEBP图像", "image/webp"),

        WOFF("woff", "Web开放字体格式（WOFF）", "font/woff"),

        WOFF2("woff2", "Web开放字体格式（WOFF）", "font/woff2"),

        XHTML("xhtml", "XHTML", "application/xhtml+xml"),

        XLS("xls", "微软Excel", "application/vnd.ms-excel"),

        XLSX("xlsx", "微软Excel（OpenXML）", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),

        XML("xml", "XML", "application/xml"),

        XUL("xul", "XUL", "application/vnd.mozilla.xul+xml"),

        ZIP("zip", "ZIP", "application/zip"),

        MIME_3GP("3gp", "3GPP audio/video container", "video/3gpp"),

        MIME_3GP_WITHOUT_VIDEO("3gp", "3GPP audio/video container doesn't contain video", "audio/3gpp2"),

        MIME_3G2("3g2", "3GPP2 audio/video container", "video/3gpp2"),

        MIME_3G2_WITHOUT_VIDEO("3g2", "3GPP2 audio/video container  doesn't contain video", "audio/3gpp2"),

        MIME_7Z("7z", "7-zip存档", "application/x-7z-compressed");

        //扩展名
        private final String extension;
        //说明
        private final String explain;
        //contentType/mime类型
        private final String mimeType;

        /**
         * @param extension 上传的文件扩展名
         * @param explain   类型说明
         * @param mimeType  Mime对应的类型
         */
        MimeTypeEnum(String extension, String explain, String mimeType) {
            this.extension = extension;
            this.explain = explain;
            this.mimeType = mimeType;
        }

        public static MimeTypeEnum getByExtension(String extension) {
            if (StringUtil.isBlank(extension)) {
                return null;
            }
            for (MimeTypeEnum typesEnum : MimeTypeEnum.values()) {
                if (extension.equals(typesEnum.getExtension())) {
                    return typesEnum;
                }
            }
            return null;
        }
    }
}
