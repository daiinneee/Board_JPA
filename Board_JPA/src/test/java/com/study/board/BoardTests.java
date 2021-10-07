package com.study.board;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.study.board.entity.Board;
import com.study.board.entity.BoardRepository;

@SpringBootTest

public class BoardTests {

	// 스프링 컨테이너에 등록된 BoardRepository 객체(Bean)를 주입받음
    @Autowired
    BoardRepository boardRepository;

    /* save() : 게시글 저장에 이용되는 params는 빌더(builder) 패턴을 통해 생성된 객체
     * 생성자와 달리, 빌더 패턴을 이용하면 어떤 멤버에 어떤 값을 세팅하는지 직관적으로 확인 가능
     * ex. 생성자를 통해 객체를 생성한다면 
     *     Board entity = new Board("1번 게시글 제목", "1번 게시글 내용", "도뎡이", 0, 'N'); 작성
     *     생성자의 경우, 객체를 생성할 때 인자의 순서에 영향을 받게 되지만, 빌더 패턴은 인자의 순서와 관계없이 
     *     객체 생성 가능 -> 가독성 측면에서 뛰어남
     */
    @Test
    void save() {

        // 1. 게시글 파라미터 생성
        Board params = Board.builder()
                .title("1번 게시글 제목")
                .content("1번 게시글 내용")
                .writer("다인")
                .hits(0)
                .deleteYn('N')
                .build();

        // 2. 게시글 저장
        boardRepository.save(params);

        // 3. 1번 게시글 정보 조회
        Board entity = boardRepository.findById((long) 1).get();
        assertThat(entity.getTitle()).isEqualTo("1번 게시글 제목");
        assertThat(entity.getContent()).isEqualTo("1번 게시글 내용");
        assertThat(entity.getWriter()).isEqualTo("다인");
    }

    // boardRepository의 count()와 findAll() 메소드를 이용해서 전체 게시글 수와 전체 게시글 리스트를 조회
    @Test
    void findAll() {

        // 1. 전체 게시글 수 조회
        long boardsCount = boardRepository.count();

        // 2. 전체 게시글 리스트 조회
        List<Board> boards = boardRepository.findAll();
    }

    // boardRepository의 findById() 메소드를 이용해서 엔티티를 조회
    // findById()도 마찬가지로 JPA에서 기본으로 제공해주는 메소드로
    // 엔티티의 PK를 기준으로 데이터를 조회한 다음 delete() 메소드를 실행해서 1번 게시글을 삭제
    @Test
    void delete() {

        // 1. 게시글 조회
    	// 참고 : findById()의 리턴 타입은 Optional<T>이라는 클래스
    	//       옵셔널은 반복적인 NULL 처리를 피하기 위해 자바 8에서 최초로 도입된 클래스
        Board entity = boardRepository.findById((long) 1).get();

        // 2. 게시글 삭제
        boardRepository.delete(entity);
    }

}