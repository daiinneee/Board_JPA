package com.study.board.model;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.study.board.dto.BoardRequestDto;
import com.study.board.dto.BoardResponseDto;
import com.study.board.entity.Board;
import com.study.board.entity.BoardRepository;
import com.study.exception.CustomException;
import com.study.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {

	// boardRepository : JPA Repository 인터페이스
	/*
	 * 보통 @Autowired로 빈(Bean)을 주입 받는 방시을 사용하였으나,
	 * 스프링은 생성자로 빈을 주입하는 방식을 권장
	 * 클래스 레벨에 선언된 @RequiredArgsConstructor는 롬복에서 제공해주는 어노테이션으로,
	 * 클래스 내에 final로 선언된 모든 멤버에 대한 생성자를 만들어 줌
	 * ex. public BoardService(BoardRepository boardRepository) {
	 *	   	   this.boardRepository = boardRepository;
	 *		}
	 */
    private final BoardRepository boardRepository;

    /**
     * 게시글 생성
     */
    /* 
     * @Transactional
     * JPA를 사용한다면, 서비스 클래스에서 필수적으로 사용되어야 하는 어노테이션
     * 일반적으로 메소드 레벨에 선언하게 되며, 메소드의 실행, 종료, 예외를 기준으로
     * 각각 실행(begin), 종료(commit), 예외(rollback)를 자동으로 처리해 줌
     */
    
    /*
     * save() 메소드
     * boardRepository의 save() 메소드가 실행되면 새로운 게시글이 생성
     * Entity 클래스는 절대로 요청(Request)에 사용되어서는 안되기 때문에
     * BoardRequestDto의 toEntity() 메소드를 이용해서 boardRepository의 save() 메소드를 실행
     * save() 메소드가 실행된 후 entity 객체에는 생성된 게시글 정보가 담기며,
     * 메소드가 종료되면 생성된 게시글의 id(PK)를 리턴
     */
    @Transactional
    public Long save(final BoardRequestDto params) {

        Board entity = boardRepository.save(params.toEntity());
        return entity.getId();
    }

    /**
     * 게시글 리스트 조회
     */
    /*
     * findAll() 메소드
     * boardRespository의 findAll() 메소드의 인자로 sort 객체를 전달해서 전체 게시글을 조회
     * sort 객체는 ORDER BY id DESC, created_date DESC를 의미
     * return문에서 Java의 Stream API를 이용 -> list 변수에는 게시글 Entity가 담겨 있고,
     * 										각각의 Entity를 BoardResponseDto 타입으로 변경(생성)해서
     * 										리턴해 줌
     */
    public List<BoardResponseDto> findAll() {

        Sort sort = Sort.by(Direction.DESC, "id", "createdDate");
        List<Board> list = boardRepository.findAll(sort);
        return list.stream().map(BoardResponseDto::new).collect(Collectors.toList());
        
        /* findAll() 메소드를 Stream API 없이 풀어서 작성한다면 */
        /*
             List<BoardResponseDto> boardList = new ArrayList<>();
    
    		 for (Board entity : list) {
        		boardList.add(new BoardResponseDto(entity));
    		 }
    
    		return boardList;
		}                   
         */
        
    }

    /**
     * 게시글 수정
     */
    
    /*
     * update() 메소드
     * Board Entity 클래스에 update() 메소드 추가 -> 해당 메소드에는 update 쿼리를 실행하는 로직이 없음
     * 하지만, 해당 메소드의 실행이 종료(commit)되면 update 쿼리가 자동으로 실행
     * JPA에는 영속성 컨텍스트라는 개념이 존재 
     * 
     * 영속성 컨텍스트란?
     * Entity를 영구히 저장하는 환경이라는 뜻(애플리케이션과 DB 사이에서 객체를 보관하는 가상의 영역)
     * JPA의 엔티티 매니저는 Entity가 생성되거나, Entity를 조회하는 시점에 영속성 컨텍스트에 Entity를 보관 및 관리
     * 결론 : Entity를 조회하면 해당 Entity는 영속성 컨텍스트에 보관(포함)되고, 
     * 		 영속성 컨텍스트에 포함된 Entity 객체의 값이 변경되면,
     * 		 트랜잭션이 종료(commit)되는 시점에 update 쿼리를 실행
     * 		 이렇게 자동으로 쿼리가 실행되는 개념을 더티 체킹(Dirty Checking)이라고 함
     * 		 "영속성 컨텍스트에 의해 더티 체킹이 가능해진다"
     */
    @Transactional
    public Long update(final Long id, final BoardRequestDto params) {

    	/* 
    	 * JPA Repository의 findById()는 Java 8에서 도입된 Optional 클래스를 리턴 
    	 * Optional은 반복적인 NULL 처리를 피하기 위해 사용되는 클래스
    	 * orElseThrow()는 Optional 클래스에 포함된 메소드로,
    	 * Entity 조회와 예외 처리를 단 한 줄로 처리 가능
    	 * 해당 코드를 풀어서 작성한다면
    	 * @Transactional
		   public Long update(final Long id, final BoardRequestDto params) {

    			Board entity = boardRepository.findById(id).orElse(null);

    			if (entity == null) {
        			throw new CustomException(ErrorCode.POSTS_NOT_FOUND);
    			}

    			entity.update(params.getTitle(), params.getContent(), params.getWriter());
    			return id;
			}
    	 */
        Board entity = boardRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));
        entity.update(params.getTitle(), params.getContent(), params.getWriter());
        return id;
    }

}