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
 * 人民网行业24小时专栏
 */
public class RenMinNet {


    public List<ArticleInfo> getArticleInfoListByIndex(int index) {
        List<ArticleInfo> articleInfos = new ArrayList<>();

        try {
            Document document = Jsoup.connect(Constant.USER_AGENT).get();

            Element articleListContent = document.getElementsByClass("p2_left ej_left").get(0);
            Elements articleListClass = articleListContent.getElementsByTag("li");
            for (Element articleClass : articleListClass) {
                Element element = articleClass.getElementsByTag("a").get(0);
//                Element time = articleClass.getElementsByTag("em").get(0);
                ArticleInfo articleInfo = new ArticleInfo(element.text(), "", "http://shipin.people.com.cn" + element.attr("href"));
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
            articleInfo.setTitle(document.getElementsByClass("clearfix w1000_320 text_title")
                    .get(0).getElementsByTag("h1").get(0).text());
            String info = document.getElementsByClass("fl").get(0).text();
            String[] strArray = info.split("  来源：");
            articleInfo.setTime(strArray[0]);
            if(strArray.length>1) {
                articleInfo.setSource(strArray[1]);
            }
            articleInfo.setContent(document.getElementsByClass("box_con").get(0).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return articleInfo;
    }


}
