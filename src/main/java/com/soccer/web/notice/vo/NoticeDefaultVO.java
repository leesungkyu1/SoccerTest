package com.soccer.web.notice.vo;

import lombok.Data;

//import org.apache.commons.lang3.builder.ToStringBuilder;

public @Data class NoticeDefaultVO {

	/** 1. 페이지 당 보여지는 게시글의 최대 개수 **/
	private int pageSize = 10;

	/** 2. 페이징된 버튼의 블럭당 최대 개수 **/
	private int blockSize = 10;

	/** 3. 현재 페이지 **/
	private int page = 1;

	/** 4. 현재 블럭 **/
	private int block = 1;

	/** 5. 총 게시글 수 **/
	private int totalListCnt;

	/** 6. 총 페이지 수 **/
	private int totalPageCnt;

	/** 7. 총 블럭 수 **/
	private int totalBlockCnt;

	/** 8. 블럭 시작 페이지 **/
	private int startPage = 1;

	/** 9. 블럭 마지막 페이지 **/
	private int endPage = 1;

	/** 10. DB 접근 시작 index **/
	private int startIndex = 0;

	/** 11. 이전 블럭의 마지막 페이지 **/
	private int prevBlock;

	/** 12. 다음 블럭의 시작 페이지 **/
	private int nextBlock;	
}
