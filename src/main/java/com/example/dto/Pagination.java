package com.example.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Pagination {
        public static int PAGE_SCALE = 5; //페이지당 게시물 수
        public static int BLOCK_SCALE = 5; //화면당 페이지 수
        private int curPage; //현재페이지수
        private int prevPage; //이전페이지수
        private int nextPage; //다음페이지
        private int totPage; //전체페이지갯수
        private int totBlock; //전체페이지블록갯수
        private int curBlock; //현재페이지 블록
        private int prevBlock; //이전 페이지 블록
        private int nextBlock; //다음 페이지 블록
        //where rn between #{start} and #{end}
        private int pageBegin; //#{start}
        private int pageEnd; //#{end}
        // [이전] blockBegin -> 41 42 43 44 45 46 47 48 49 50 [다음]
        private int blockBegin; //현재 페이지 블록의 시작번호

        private int blockEnd; //현재 페이지 블록의 끝번호

}
