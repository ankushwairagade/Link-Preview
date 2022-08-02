package com.ankush.Link.Preview.controller;

import com.ankush.Link.Preview.model.Link;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class PageController {

    @RequestMapping(value ="/link",method = RequestMethod.GET)
    public String home(@RequestParam(value = "url", required = true) String url , Model model)
    {
        Link link = null;
        try {
            link = extractLinkPreviewInfo(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(link == null){return "preview";}

        // System.out.println("inside home");  //just for check
        model.addAttribute("desc",link.getDesc());
        model.addAttribute("img",link.getImage());
        model.addAttribute("url",link.getUrl());
        model.addAttribute("title",link.getTitle());
        return "preview";
    }



    @RequestMapping(value ="/",method = RequestMethod.GET)
     public String index()
    {   return "preview";
    }



    private Link extractLinkPreviewInfo(String url) throws IOException {
        if (!url.startsWith("http")) {
            url = "http://" + url;
        }
        Document document = Jsoup.connect(url).get();
        String title = getMetaTagContent(document, "meta[name=title]");
        String desc = getMetaTagContent(document, "meta[name=description]");
        String ogTitle = getMetaTagContent(document, "meta[property=og:title]");
        String ogDesc = getMetaTagContent(document, "meta[property=og:description]");
        String ogImage = getMetaTagContent(document, "meta[property=og:image]");

        return new Link(url, StringUtils.defaultIfBlank(ogTitle, title), StringUtils.defaultIfBlank(ogDesc, desc), ogImage);
    }

    private String getMetaTagContent(Document document, String cssQuery) {
        Element elm = document.select(cssQuery).first();
        if (elm != null) {
            return elm.attr("content");
        }
        return "";
    }



}
