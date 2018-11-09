package reptile;

import bean.ArticleInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.Constant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * CCTV餐饮频道
 */
public class FoodCCTV {


    public List<ArticleInfo> getArticleInfoList() {
        List<ArticleInfo> articleInfos = new ArrayList<>();

        try {
            Document document = Jsoup.connect("http://food.cctv.com/hangye/index.shtml")
                    .userAgent(Constant.USER_AGENT)
                    .get();
            Element articleListContent = document.getElementsByClass("meishigbZ10384_con05").get(0);
            Element articleList = articleListContent.getElementsByClass("list_lt").get(0);
            Elements articleListLI = articleList.getElementsByTag("li");

            for (Element elementLI : articleListLI) {
                Elements elements = elementLI.getElementsByTag("a");
                if (elements.size() > 0) {
//                Element time = elementLI.getElementsByTag("date").get(0);
                    ArticleInfo articleInfo = new ArticleInfo(elements.get(0).text(), "", elements.get(0).attr("href"));
                    loadArticle(articleInfo);
                    articleInfos.add(articleInfo);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return articleInfos;
    }

    public ArticleInfo loadArticle(ArticleInfo articleInfo) {
        System.out.println(articleInfo.toString());
        try {
            Document document = Jsoup.connect(articleInfo.getUrl()).userAgent(Constant.USER_AGENT).timeout(3000).get();
            Elements articleBodys = document.getElementsByClass("cnt_bd");
            if (articleBodys.size() != 0) {
                Element articleBody = articleBodys.get(0);
                articleInfo.setTitle(articleBody.getElementsByTag("h1").get(0).text());
                Element articleInfoEle = articleBody.getElementsByClass("info").get(0);
                if (articleInfoEle.getElementsByTag("i").size() > 0) {
                    Element articleInfoEleTagI = articleInfoEle.getElementsByTag("i").get(0);
                    String sourceInfoStr = articleInfoEleTagI.text();
                    articleInfo.setSource(sourceInfoStr.substring(3, sourceInfoStr.length() - 18));
                    articleInfo.setTime(articleInfoEleTagI.text().substring(sourceInfoStr.length() - 18, sourceInfoStr.length()));
                }
                Elements articleContentTagP = articleBody.getElementsByTag("p");
                articleContentTagP.remove(0);
                Element element = new Element("content");
                for (Element perP : articleContentTagP) {
                    element.append(perP.toString());
                }
                articleInfo.setContent(element.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return articleInfo;
    }


}
