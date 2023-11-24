//package com.sponet.controller.article;
//
//import com.sponet.domain.type.SearchType;
//import com.sponet.dto.response.ArticleResponse;
//import com.sponet.dto.response.ArticleWithCommentsResponse;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.web.PageableDefault;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@RequiredArgsConstructor
//@RequestMapping("/articles")
//@Controller
//public class ArticleController {
//
//	private final ArticleService articleService;
//
//    @GetMapping
//    public String articles(
//            @RequestParam(required = false) SearchType searchType,
//            @RequestParam(required = false) String searchValue,
//            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
//            ModelMap map
//    ) {
//        map.addAttribute("articles", articleService.searchArticles(searchType, searchValue, pageable).map(ArticleResponse::from));
//
//        return "articles/articleIndex";
//    }
//
//    @GetMapping("/{articleId}")
//    public String articles(@PathVariable Long articleId, ModelMap map) {
//        ArticleWithCommentsResponse article = ArticleWithCommentsResponse.from(articleService.getArticle(articleId));
//
//        map.addAttribute("article", article);
//        map.addAttribute("articleComments", article.articleCommentsResponses());
//
//        return "articles/articleDetail";
//    }
//
//}
