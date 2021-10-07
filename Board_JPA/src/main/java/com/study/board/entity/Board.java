package com.study.board.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
// 해당 클래스의 기본 생성자를 생성해 주는 어노테이션
// access 속성을 이용해서 동일한 패키지 내의 클래스에서만 객체를 생성할 수 있도록 제어
@NoArgsConstructor(access = AccessLevel.PROTECTED)
/* 해당 클래스가 테이블과 매핑되는 JPA의 엔티티 클래스임을 의미
 * 기본적으로 클래스명(Camel Case)을 테이블명(Snake Case)으로 매핑
 *  ex. user_role이라는 테이블은 UserRole이라는 이름의 클래스로 네이밍하면 됨
 *  클래스명과 테이블명이 다를 수 밖에 없는 상황에서는 클래스 레벨에 @Table을 선언하고, 
 *  @Table(name="user_role")과 같이 name 속성을 이용해서 처리해주면 됨
 */
@Entity
public class Board {

	// 해당 멤버가 엔티티의 PK임을 의미
    @Id
    /* MySQL은 자동 증가(AUTO_INCREMENT)를 지원하는 DB
     * PK 자동 증가를 지원하는 DB는 해당 어노테이션을 선언해야 함
     * 오라클과 같이 시퀀스를 이용하는 DB는 GenerationType.SEQUENCE를 이용해야 하며,
     * GenerationType.AUTO로 설정하게 되면 DB에서 제공하는 PK 생성 전략을 가져가게 됨
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK

    private String title; // 제목

    private String content; // 내용

    private String writer; // 작성자

    private int hits; // 조회 수

    private char deleteYn; // 삭제 여부

    private LocalDateTime createdDate = LocalDateTime.now(); // 생성일

    private LocalDateTime modifiedDate; // 수정일

    // 롬복에서 제공해주는 빌더라는 기능
    // 생성자 대신에 이용하는 패턴
    @Builder
    public Board(String title, String content, String writer, int hits, char deleteYn) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.hits = hits;
        this.deleteYn = deleteYn;
    }
    
    /* @Setter가 없다?
     * -> 해당 엔티티 클래스에는 @Setter가 없음
     *    엔티티 클래스는 테이블 그 자체
     *    이는 각각의 멤버 변수는 해당 테이블의 컬럼이라는 의미가 되고,
     * 	  컬럼에 대한 setter를 무작정 생성하는 경우, 객체의 값이 어느 시점에 변경되었는지 알 수가 없음
     *    이러한 이유로 Entity 클래스에는 절대로 Set 메소드가 존재해서는 안됨!
     */

}