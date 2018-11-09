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
 * 食品行业网餐饮信息资讯专栏
 */
public class FoodMate {



    public FoodMate() {
    }

    public List<ArticleInfo> getArticleInfoListByIndex(int index) {
        List<ArticleInfo> articleInfos = new ArrayList<>();

        try {
            Document document = Jsoup.connect(Constant.USER_AGENT).get();

            Element articleListContent = document.getElementsByClass("list_biao").get(0);
            Elements articleListClass = articleListContent.getElementsByClass("list_bt");
            for (Element articleClass : articleListClass) {
                Element element = articleClass.getElementsByTag("a").get(0);
                ArticleInfo articleInfo = new ArticleInfo(element.text(), "", element.attr("href"));
                loadArticle(articleInfo);
                articleInfos.add(articleInfo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return articleInfos;
    }

    public ArticleInfo loadArticle(ArticleInfo articleInfo) {

        try {
            Document document = Jsoup.connect(articleInfo.getUrl()).userAgent(Constant.USER_AGENT).timeout(3000).get();
//            Element titleInfo = document.getElementsByClass("h-news").get(0);
            articleInfo.setTitle(document.getElementsByClass("title").get(0).text());
            String info = document.getElementsByClass("info").get(0).text();
            articleInfo.setTime(info.split("时间：")[1].split("来源：")[0]);

            if (info.indexOf("作者：") > 0) {
                articleInfo.setSource(info.split("来源：")[1].split(" 作者")[0]);
                articleInfo.setAuthor(info.split("作者：")[1].split(" 浏览")[0]);
            }else
            {
                articleInfo.setSource(info.split("来源：")[1].split(" 浏览")[0]);
            }

            articleInfo.setContent(document.getElementsByClass("content").get(0).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return articleInfo;
    }


}
