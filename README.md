## [jar 파일 다운로드 링크](https://drive.google.com/drive/folders/1mJp4Zb8hCxN2STv9q5zOHI65UmcDauT3?usp=sharing)

## API 명세서
api는 다음과 같이 응답 바디 포맷이 동일합니다. 응답 바디는 JSON 객체입니다.

| Name     | Type    | Description             | Required |
|----------|---------|-------------------------|----------|
| result    | String  | SUCCESS(성공) 또는 FAIL(실패) | O        |
| data     | json    | API응답                   | X        |
| message     | String  | 에러 메시지                  | X        |
| errorCode | Integer | 에러 코드                   | X        |


### 블로그 검색
```
GET /api/v1/search/blog
HOST: http://localhost:8080
```
검색 서비스에서 질의어로 웹 문서를 검색합니다. 원하는 검색어와 함께 결과 형식 파라미터를 선택적으로 추가할 수 있습니다.
#### Request
| Name     | Type    | Description                                               | Required |
|----------|---------|-----------------------------------------------------------|----------|
| query    | String  | 검색을 원하는 질의어                                               | O        |
| sort     | String  | 결과 문서 정렬 방식. ACCURACY(정확도순) 또는 RECENCY(최신순). 기본값 ACCURACY | X        |
| page     | Integer | 결과 페이지 번호. 1~50 사이의 값. 기본 값 1                             | X        |
| pageSize | Integer | 한 페이지에 보여질 문서 수. 1~50 사이의 값. 기본 값 10                      | X        |

#### Response
* data

| Name     | Type       | Description                          | Required |
|----------|------------|--------------------------------------|----------|
| totalCount    | Integer    | 검색된 문서 수                             | O        |
| pageableCount     | Integer    | total_count 중 노출 가능 문서 수             | O        |
| contentsList     | json array | 결과 목록                                | O        |

 * contentsList

  | Name     | Type       | Description              | Required |
  |------------|--------------------------|----------|----------|
  | title    | String     | 블로그 글 제목                 | O        |
  | contents     | String     | 블로그 글 요약                 | O        |
  | link     | String     | 블로그 글 URL                | O        |
  | postedAt    | Datetime   | 블로그 글 작성시간. ISO 8601. yyyy-MM-dd'T'HH:mm:ss.SSSXXX   | O        |
  | bloggerName     | String    | 블로그의 이름 | O        |
  | thumbnail     | String | 검색 시스템에서 추출한 대표 미리보기 이미지 URL, 미리보기 크기 및 화질은 변경될 수 있음                    | X        |

### 인기 검색어 목록
```
GET /api/v1/search/blog/popular-keyword
HOST: http://localhost:8080
```
사용자들이 많이 검색한 순서대로, 최대 10개의 검색 키워드를 제공합니다. 파라미터는 없습니다.
응답 data는 array입니다.
#### Response
* data

| Name     | Type       | Description | Required |
|----------|------------|-------------|----------|
| baseDate    | Date       | 기준일         | O        |
| keyword     | String     | 검색어         | O        |
| hitCount     | Integer | 검색 횟수       | O        |


## 설계
프로젝트는 DDD기반 레이어드 아키텍처로 구성했습니다.
* 주요 패키지 설명
  * blog : 블로그 검색
  * popular : 인기 검색어
  * common : 애플리케이션 공통 응답, 공통에러처리, jackson설정등
  * core : 외부 라이브러리에 의존적이지 않은 순수 코틀린 소스. 
## Tech Stack
1. 언어
   1. 코틀린 1.6
   2. 자바 17
2. 외부 라이브러리
   1. spring-cloud-starter-openfeign : 카카오 api, 네이버 api 호출
   2. spring-cloud-starter-circuitbreaker-resilience4j : 서킷 브레이커

## 기능 설명
1. 블로그 검색
   * 카카오 검색API에 장애가 발생한 경우 네이버 검색API로 데이터를 제공합니다.
   * 서킷브레이커를하여 조건에 맞으면 카카오API를 차단하고 네이버검색API로 데이터를 제공합니다.
2. 인기 검색어
    * 검색시 이벤트를 수신하여 메모리에 저장합니다.
    * 스케줄러가 5초마다 검색기록을 데이터베이스에 저장합니다.
    * 저장은 성능을 고려하여 batchUpdate로 합니다.
    * 인기 검색어는 캐시를 적용하고, 5초마다 갱신합니다.