package com.study.board.dto;

import com.study.board.entity.Board;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/* 게시글의 생성과 수정을 처리할 요청(Request) DTO 클래스 */

/* 
 * Entity 클래스와 요청 DTO 클래스는 유사한 구조
 * Entity 클래스는 테이블 또는 레코드 역할을 하는 DB 그 자체 -> 절대로 요청(Request)이나 응답(Response)에 사용되어선 안됨
 * 반드시 Request, Response 클래스를 따로 생성해 주어야 함
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardRequestDto {

    private String title; // 제목
    private String content; // 내용
    private String writer; // 작성자
    private char deleteYn; // 삭제 여부

    /* 
     * toEntity() 메소드 
     * Entity 클래스는 절대로 요청에 사용되어서는 안됨
     * 따라서 BoardRequestDto로 전달받은 데이터(파라미터)를 기준으로 Entity 객체를 생성
     */
    public Board toEntity() {
        return Board.builder()
                .title(title)
                .content(content)
                .writer(writer)
                .hits(0)
                .deleteYn(deleteYn)
                .build();
    }

}