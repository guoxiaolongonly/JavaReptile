package bean;

import java.util.List;

public class ArticleInfo {
    private String title;//标题
    private String subTitle;//副标题~~ 引言
    private String describe; //简单描述
    private String url; //地址
    private List<String> images; //图片地址
    private String source; //来源
    private String time; //日期
    private String content;//内容
    private String editor;//编辑
    private String author;//作者
    private String picLinks;//标题小图片

    public String getPicLinks() {
        return picLinks;
    }

    public void setPicLinks(String picLinks) {
        this.picLinks = picLinks;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public ArticleInfo() {
    }

    public ArticleInfo(String title, String subTitle, String url) {
        this.title = title;
        this.subTitle = subTitle;
        this.url = url;
    }

    @Override
    public String toString() {
        return "ArticleInfo{" +
                "title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", describe='" + describe + '\'' +
                ", url='" + url + '\'' +
                ", images=" + images +
                ", source='" + source + '\'' +
                ", time='" + time + '\'' +
                ", content='" + content + '\'' +
                ", editor='" + editor + '\'' +
                ", author='" + author + '\'' +
                ", picLinks='" + picLinks + '\'' +
                '}';
    }
}
