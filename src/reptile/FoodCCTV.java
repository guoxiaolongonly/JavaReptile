package reptile;

import bean.ArticleInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 食品中国餐饮频道
 */
public class FoodCCTV {
    String cookieStr ="c_adb=1; smidV2=20181022161242ce0add1dca115097e55166b794d9eb4400b3128dfcf4e2ba0; UN=guoxiaolongonly; ARK_ID=JSb9508ab033f474c29bdaa6eec55488d7b950; uuid_tt_dd=10_28867322960-1540280271339-759935; dc_session_id=10_1541470951644.617128; Hm_lvt_6bcd52f51e9b3dce32bec4a3997715ac=1541582720,1541582791,1541582802,1541594782; BT=1541594792198; ci_session=a%3A6%3A%7Bs%3A10%3A%22session_id%22%3Bs%3A32%3A%22e0113052f0fbcf40e70f77bf364c437a%22%3Bs%3A10%3A%22ip_address%22%3Bs%3A14%3A%22120.36.251.238%22%3Bs%3A10%3A%22user_agent%22%3Bs%3A114%3A%22Mozilla%2F5.0+%28Windows+NT+10.0%3B+Win64%3B+x64%29+AppleWebKit%2F537.36+%28KHTML%2C+like+Gecko%29+Chrome%2F70.0.3538.77+Safari%2F537.36%22%3Bs%3A13%3A%22last_activity%22%3Bi%3A1541594954%3Bs%3A9%3A%22user_data%22%3Bs%3A0%3A%22%22%3Bs%3A8%3A%22userInfo%22%3Bs%3A15%3A%22guoxiaolongonly%22%3B%7D047217d7973f4784ddac337d66716905; dc_tos=phtqo4; Hm_lpvt_6bcd52f51e9b3dce32bec4a3997715ac=1541595461";

    public static void main(String args[]) {
        FoodCCTV foodMate = new FoodCCTV();
//        foodMate.getArticleInfoListByIndex(1);
        foodMate.test();
    }

    public FoodCCTV() {
    }

    public void test() {
        Map<String, String> cookies = new HashMap<>();
        String[] cookieArray = cookieStr.split("; ");
        for (String str : cookieArray) {
            String[] cookie = str.split("=");
            cookies.put(cookie[0], cookie[1]);
        }
        try {
            Document document = Jsoup.connect("https://my.csdn.net/index.php/follow/check_is_followed//guoxiaolongonly?jsonpcallback=jQuery19103850534421799585_1541595460453&_=1541595460454").cookies(cookies).
                    userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36").timeout(3000).get();
            System.out.println(document);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
