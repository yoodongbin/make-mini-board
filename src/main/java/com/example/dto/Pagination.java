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
        public static int BLOCK_SCALE = 3; //화면당 페이지 수
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

        //생성자
        public Pagination(int count, int curPage) {//레코드 갯수, 현재 페이지 번호
                curBlock = 1; //현재 페이지 블록 번호
                this.curPage = curPage; //현재 페이지 설정
                setTotPage(count); //전체 페이지 갯수 계산
                setPageRange();
                setTotBlock(); //전체 페이지 블록 갯수 계산
                setBlockRange(); //페이지 블록의 시작, 끝 번호 계산
        }

        //페이지 블록의 갯수 계산 (총 100페이지라면 10개의 블록)
        public void setTotBlock() {
                //전체 페이지 갯수 / 10
                //91/10 => 9.1 => 10개
                totBlock = (int)Math.ceil(totPage / BLOCK_SCALE);
        }

        public void setTotPage(int count) {
                //Math.ceil(실수) 올림 처리
                totPage = (int) Math.ceil(count*1.0 / PAGE_SCALE);
        }

        public void setBlockRange() {
                curBlock = (int)Math.ceil((curPage-1) / BLOCK_SCALE)+1;//현재 페이지가 몇번째 페이지 블록에 속하는지 계산
                blockBegin = (curBlock-1)*BLOCK_SCALE+1;//현재 페이지 블록의 시작, 끝 번호 계산
                blockEnd = blockBegin+BLOCK_SCALE-1;//페이지 블록의 끝번호
                if(blockEnd > totPage) blockEnd = totPage;//마지막 블록이 범위르 초과하지 않도록 계산
                prevPage = (curPage == 1) ? 1:(curBlock -1)*BLOCK_SCALE; //이전을 눌렀을 때 이동할 페이지 번호
                nextPage = curBlock > totBlock ? (curBlock*BLOCK_SCALE) : (curBlock*BLOCK_SCALE)+1; //다음을 눌렀을 때 이동할 페이지 번호
                if(nextPage >= totPage) nextPage = totPage; //마지막 페이지가 범위를 초과하지 않도록 처리
        }

        public void setPageRange() {
                //where rn between #{start} and #{end}
                pageBegin = (curPage-1) * PAGE_SCALE+1; //시작번호
                pageEnd = pageBegin+PAGE_SCALE-1; //끝번호
        }

}
