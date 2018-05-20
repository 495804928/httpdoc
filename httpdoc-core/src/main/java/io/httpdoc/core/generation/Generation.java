package io.httpdoc.core.generation;

import io.httpdoc.core.Document;
import io.httpdoc.core.provider.Provider;
import io.httpdoc.core.provider.SystemProvider;

/**
 * 生成对象
 *
 * @author 杨昌沛 646742615@qq.com
 * @date 2018-04-19 16:36
 **/
public class Generation {
    private Document document;
    private String directory = System.getProperty("tmp.dir") + "io/httpdoc/gen";
    private String pkg = "io.httpdoc.gen";
    private Provider provider = new SystemProvider();

    public Generation(Document document) {
        this.document = document;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getPkg() {
        return pkg;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

}