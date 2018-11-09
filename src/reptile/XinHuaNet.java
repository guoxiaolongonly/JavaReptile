package reptile;

import bean.ArticleInfo;
import bean.xinhua.XinHuaArticle;
import bean.xinhua.XinhuaListData;
import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import util.Constant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 新华网食品专栏
 */
public class XinHuaNet {


    /**
     * 必须是一个合格的article （emmm  不为空，带上你的url）
     *
     * @param articleInfo
     * @return
     */
    public ArticleInfo loadArticle(ArticleInfo articleInfo) {

        try {
            Document document = Jsoup.connect(articleInfo.getUrl()).userAgent(Constant.USER_AGENT).timeout(3000).get();
//            Element titleInfo = document.getElementsByClass("h-news").get(0);
//            articleInfo.setTitle(titleInfo.getElementsByClass("h-title").get(0).text());
//            articleInfo.setTime(titleInfo.getElementsByClass("h-time").get(0).text());
//            articleInfo.setSource(titleInfo.getElementById("source").text());
            articleInfo.setContent(document.getElementById("p-detail").toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return articleInfo;
    }

    public List<ArticleInfo> getJRYWArticleList(int pageNum, int pageSize) {
        String responseJson = "";
        StringBuffer shssUrl = new StringBuffer("http://qc.wa.news.cn/nodeart/list")
                .append("?nid=11109456")
                .append("&pgnum=" + pageNum)
                .append("&cnt=" + pageSize)
                .append("&tp=1").append("&orderby=1");
        try {
            String responseResult = Jsoup.connect(shssUrl.toString()).execute().body();
            responseJson = responseResult.substring(1, responseResult.length() - 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getArticleListByResponseJson(responseJson);
    }

    public List<ArticleInfo> getSHSSArticleList(int pageNum, int pageSize) {
        String responseJson = "";
        StringBuffer shssUrl = new StringBuffer("http://qc.wa.news.cn/nodeart/list")
                .append("?nid=11109457")
                .append("&pgnum=" + pageNum)
                .append("&cnt=" + pageSize)
                .append("&tp=1").append("&orderby=1");
        try {
            String responseResult = Jsoup.connect(shssUrl.toString()).execute().body();
            responseJson = responseResult.substring(1, responseResult.length() - 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getArticleListByResponseJson(responseJson);
    }

    public List<ArticleInfo> getArticleListByResponseJson(String json) {
        List<ArticleInfo> articleInfos = new ArrayList<>();
        Gson gson = new Gson();
        XinhuaListData xinhuaListData = gson.fromJson(json, XinhuaListData.class);
        for (XinHuaArticle xinHuaArticle : xinhuaListData.data.list) {
            ArticleInfo articleInfo = new ArticleInfo();
            articleInfo.setUrl(xinHuaArticle.LinkUrl);
            articleInfo.setTitle(xinHuaArticle.Title);
            articleInfo.setEditor(xinHuaArticle.Editor);
            articleInfo.setAuthor(xinHuaArticle.Author);
            articleInfo.setTime(xinHuaArticle.PubTime);
            articleInfo.setDescribe(xinHuaArticle.Abstract);
            articleInfo.setSource(xinHuaArticle.SourceName);
            articleInfo.setPicLinks(xinHuaArticle.PicLinks);
            loadArticle(articleInfo);
            articleInfos.add(articleInfo);
        }
        return articleInfos;
    }

}
