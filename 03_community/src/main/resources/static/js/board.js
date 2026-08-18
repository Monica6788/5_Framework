// 이미지 미리보기
const imagesInput = document.querySelector("#images");
const imagePreviewList = document.querySelector("#image-preview-list");

imagesInput.addEventListener("change", function(e) {
    // preview 영역 초기화
    imagePreviewList.textContent = "";
    
    // 파일 객체 -> 이벤트 객체로 가지고 오기
    let images = e.target.files;    // 배열이 아닌 FileList 객체임!
    // forEach 등의 반복문 사용 시 배열로 변환해주어야 함.

    images = Array.from(images);    // forEach 사용을 위해 배열로 변환함.
    images.forEach(function(file, index) {
        const reader = new FileReader();    // base64 문자열로 바꾸기 위한 객체
        reader.onload = function(event) {
            const li = document.createElement("li");    // li 태그(요소) 생성
            const img = document.createElement("img");

            img.src = event.target.result;
            /* FileReader가 읽어들인 파일의 결과(Base64 문자열 데이터)를 이미지 태그의
            src 속성에 집어 넣어서 실제로 화면에 렌더링 되도록 만드는 핵심 코드 */
            img.alt = file.name;            // 이미지 각각의 alt 속성값을 파일명으로

            li.appendChild(img);
            // img 태그를 li 태그의 자식으로 집어 넣어 한 세트로 묶어주기
            imagePreviewList.appendChild(li);
            // 이미지가 담긴 리스트를 최종적으로 화면에 보여줄 미리보기 목록 전체 부모 태그에
            // 얹어서 브라우저 화면에 목록 형태로 나타나게 하는 코드
        }

        reader.readAsDataURL(file);

    });

});

// 댓글 기능
const commentForm  = document.querySelector("#comment-form");
const boardKey = document.querySelector("#board-key");

commentForm.addEventListener("submit", async function(ev) {
    ev.preventDefault();        // 기본 이벤트는 막고 직접 처리

    const contentInput = commentForm.querySelector("textarea");
    const content = contentInput.value.trim();

    if (!content) {     // 댓글 내용이 비어 있으면 요청하지 않고 리턴
        alert("댓글 내용을 입력해주세요.");
        return;
    }

    const boardId = boardKey.value;

    try {

        const response = await fetch(`/api/board/${boardId}/comment`, {
            method: "POST",
            headers: {
                "X-Requested-With": "XMLHttpRequest",   // 비동기 요청임을 서버에 전달
                "Content-Type": "application/json",     // 전달되는 데이터가 JSON임을 서버에 전달
                
            },
            body: JSON.stringify({content})
        });
        
        const result = await response.json();   // 전달된 값을 JSON 형태로 변환해주는 메서드
        
        if (!response.ok/* response 성공 시 true 반환 */ || !result.success) {
            alert(result.message || "댓귿 등록에 실패했습니다.");
            return;
        }
        // TODO: 응답 결과를 화면에 표시
    } catch (error) {
        alert("댓글 등록 중 오류가 발생했습니다.");
    }
});
