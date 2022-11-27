# hanghaeStartProject
1. 수정, 삭제 API의 request를 어떤 방식으로 사용하셨나요? (param, query, body)
@PathVariable 로 id를 보내고, @RequestBody 를 사용하여 수정의 경우 수정된 게시글 값, 삭제의 경우 password값을 받아옴

2. 어떤 상황에 어떤 방식의 request를 써야하나요?
전체목록 불러오는 GET의 경우 가져올 데이터에 조건이없기때문에 requestParam이 없음
특정 글을 불러오는 GET의경우도 requestParam은 없지만, 원하는 조건을 queryParam에 담아서 보냄
POST와 PUT의 경우 넣고싶은 정보를 RequestBody에 담아서 보내며,
DELETE의 경우 보낼 정보가 있을경우 queryParam이나 RequestBody에 담아서 보낼 수도 있음.

3. RESTful한 API를 설계했나요? 어떤 부분이 그런가요? 어떤 부분이 그렇지 않나요?
동일한 URL이라도 GET, PUT, DELETE등 메소드를 달리하여 처리과정을 다르게 처리하였음.

4. 적절한 관심사 분리를 적용하였나요? (Controller, Repository, Service)
controller에서는 요청받은 url과 service를 연결해주는 작업만 하도록 분리함.
service단에서는 받은 데이터를 repository함수를 사용하여 DB에 적용시키도록 기능부분만 담음.
repository에는 JPA에서 기본제공되는 함수외의 기능을 가진 함수를 추가정의하여 담음

5. API 명세서 작성 가이드라인을 검색하여 직접 작성한 명세서와 비교해보세요!
가이드라인의 author 가 직접작성한 명세에서는 username이 됨
