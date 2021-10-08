package com.study.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/* 페이지 처리용 컨트롤러 생성하기 */

@Controller
@RequestMapping("/board")
public class BoardPageController {

    /**
     * 게시글 리스트 페이지
     */
    @GetMapping("/list")
    public String openBoardList() {
        return "board/list";
    }
    
    /**
     * 게시글 등록 페이지
     */
    @GetMapping("/write")
    public String openBoardWrite() {
        return "board/write";
    }

}