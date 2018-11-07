package reptile;

import bean.ArticleInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 食品中国餐饮频道
 */
public class SPZG {



    public SPZG() {
    }

    public List<ArticleInfo> getArticleInfoListByIndex(int index) {
        List<ArticleInfo> articleInfos = new ArrayList<>();

        try {
            Document document = Jsoup.connect("http://www.shipinzg.cn/F-News/newslist-0-146-aa-p" + index + ".html").userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36").get();
            Element articleListContent = document.getElementsByClass("left_").get(0);
            Elements articleListUL = articleListContent.getElementsByClass("l_l");
            Element articleListEle = new Element("lucky");
            for (Element articleClass : articleListUL) {
                articleListEle.append(articleClass.html());
            }
            Elements articleListLI = articleListEle.getElementsByTag("li");

            for (Element elementLI : articleListLI) {
                Element element = elementLI.getElementsByTag("a").get(0);
//                Element time = articleClass.getElementsByTag("em").get(0);
                ArticleInfo articleInfo = new ArticleInfo(element.text(), "", "http://www.shipinzg.cn" + element.attr("href"));
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
            Document document = Jsoup.connect(articleInfo.getUrl()).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36").timeout(3000).get();
//            Element titleInfo = document.getElementsByClass("h-news").get(0);
            articleInfo.setTitle(document.getElementsByClass("Title_h1")
                    .get(0).getElementsByTag("h1").get(0).text());
            if (document.getElementsByClass("laiyuan").size() > 0) {
                String source = document.getElementsByClass("laiyuan").get(0).text();
                articleInfo.setSource(source.substring(3, source.length()));
            }
            if (document.getElementsByClass("bianji").size() > 0) {
                articleInfo.setAuthor(document.getElementsByClass("bianji").get(0).text());
            }
            articleInfo.setTime(document.getElementsByClass("riqi").get(0).text());
            articleInfo.setContent(document.getElementsByClass("conter_show").get(0).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return articleInfo;
    }


}
