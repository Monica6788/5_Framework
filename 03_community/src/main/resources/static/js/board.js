// 이미지 미리보기
const imagesInput = document.querySelector("#images");
const imagePreviewList = document.querySelector("#image-preview-list");

if (imagesInput) {  // imagesInput이 있을 때에만 이미지 미리보기 기능을 실행
// 이 구문을 if문으로 감싸주지 않으면 댓글 작성할 때 Uncaught TypeError 발생함
// 댓글에는 이미지가 없어서 가져올 이미지 없는데? 하고 오류 나는 것!
// NullPointerException이라고 보면 됨
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
}

// 댓글 기능
const commentForm  = document.querySelector("#comment-form");
const boardKey = document.querySelector("#board-key");
if (commentForm) {
// 얘도 마찬가지로 댓글 없을 때 다른 기능까지 죄다 종료될 것을 고려해서 if문으로 감싸기
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
            alert("댓글이 등록되었습니다.");
            appendComment(result.data);
            // 키 값은 data, 전달된 값은 result로 저장되어 있음.
            
            contentInput.value = "";    // 댓글 작성 후 댓글란 다시 비워주기


        } catch (error) {
            alert("댓글 등록 중 오류가 발생했습니다.");
        }
      });
    }

// 댓글 등록 후 화면에 표시
const commentList = document.querySelector("#comment-list");
function appendComment(comment) {
    // 템플릿 영역 접근
    const commentTemplate = document.querySelector("#comment-template");
    const cloneComment = commentTemplate.content.cloneNode(true);

    const li = cloneComment.querySelector("li");
    // 복제한 comment로 li 태그에 접근
    li.id = `comment-${comment.commentId}`;
    // li 요소에 commentId 값에 따른 id 속성 부여
    cloneComment.querySelector(".comment-list_writer").textContent = comment.writerNickname;
    cloneComment.querySelector(".comment-list_content").textContent = comment.content;
    cloneComment.querySelector(".comment-list_date").textContent = comment.createAtStr;

    cloneComment.querySelector(".comment-delete-btn").dataset.commentId = comment.commentId;
    // dataset을 사용하면 data-*** 속성으로 추가될 것임

    commentList.appendChild(cloneComment);
}

// 댓글 영역에 표시되는 댓글 삭제 기능
if (commentList) {
    commentList.addEventListener("click", async function(e) {
        // 아래 await fetch 함수를 사용하는 부분이라서 async를 붙임
        /*
         *  if (e.target.classList.contains("comment-delete-btn")) return;
         *  const delBtn = e.target;
         * 
         *  또는
         * 
         *  closest(선택자)  : 클릭한 요소에서 부모 방향으로 선택자에 해당하는 요소를 찾아줌
         */
        const delBtn = e.target.closest(".comment-delete-btn");
        if (!delBtn) return;       // 삭제 버튼이 아니면 메서드 종료
        if(!confirm("댓글을 삭제하시겠습니까?")) return;
        // 확인 누르면 true, 취소 누르면 false

        const commentId = delBtn.dataset.commentId;

        try {
            const response = await fetch(`/api/comments/${commentId}`, {
                method: "DELETE",
                /*  Restful 설계 원칙에 따라 요청 방식은
                get, post, put, patch, delete로 나누어짐 */
                headers: {"X-Requested-With" : "XMLHttpRequest"},
            });
            
            const result = await response.json();
            
            if (!response.ok || !result.success) {
                alert(result.message || "댓글 삭제에 실패했습니다.");
                return;
            }
            
            // 화면 상에서 해당 댓글 제거
            document.querySelector(`#comment-${commentId}`).remove();
        } catch (error) {
            alert("댓글 삭제 중 오류 발생");
        }
    
    });
}
