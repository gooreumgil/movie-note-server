package com.oldteam.movienote.clients.kobis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KobisMovieReqDto {

    private String curPage; //현재 페이지를 지정합니다.(default : “1”)
    private String itemPerPage;	//결과 ROW 의 개수를 지정합니다.(default : “10”)
    private String movieNm;	//영화명으로 조회합니다. (UTF-8 인코딩)
    private String directorNm; //감독명으로 조회합니다. (UTF-8 인코딩)
    private String openStartDt;	//YYYY형식의 조회시작 개봉연도를 입력합니다.
    private String openEndDt; //YYYY형식의 조회종료 개봉연도를 입력합니다.
    private String prdtStartYear; //YYYY형식의 조회시작 제작연도를 입력합니다.
    private String prdtEndYear; //YYYY형식의 조회종료 제작연도를 입력합니다.
    private String repNationCd;	//N개의 국적으로 조회할 수 있으며, 국적코드는 공통코드 조회 서비스에서 “2204” 로서 조회된 국적코드입니다. (default : 전체)
    private String movieTypeCd; //N개의 영화유형코드로 조회할 수 있으며, 영화유형코드는 공통코드 조회 서비스에서 “2201”로서 조회된 영화유형코드입니다.(default: 전체)

}
