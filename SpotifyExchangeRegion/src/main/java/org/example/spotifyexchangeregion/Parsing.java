package org.example.spotifyexchangeregion;

import org.htmlunit.WebClient;
import org.htmlunit.html.HtmlPage;

public class Parsing implements AutoCloseable {
    private final HtmlPage htmlPage;

    Parsing(String url) {
        try (final WebClient webClient = new WebClient()) {
            // Отключение JavaScript и CSS для ускорения
            webClient.getOptions().setJavaScriptEnabled(false);
            webClient.getOptions().setCssEnabled(false);

            htmlPage = webClient.getPage(url);
            Thread.sleep(10000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void close() throws Exception {
        htmlPage.cleanUp();
    }

    public void changeRegion(String login, String password) {
        try {
            System.out.println(htmlPage.getTitleText());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
