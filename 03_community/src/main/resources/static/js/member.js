/* ---------- 회원가입 페이지 ---------- */

// --------------- 프로필 이미지 미리보기(프리뷰) ----------------------
const profileImage = document.querySelector("#profile-image");

profileImage.addEventListener('change', function(e) {
    // 업로드한 파일 정보를 가져오기 (첫 번째 요소)
    const file = e.target.files[0];

    // 프로필 이미지 선택 클릭 후 취소가 가능한데, 이 경우 e.target.files가 빈 배열이 된다.
    // 그러면 file은 undefined가 되어 오류가 발생할  수 있다.
    // => file이 없을 때 메서드를 종료하도록 검증
    if (!file) {
        return;
    }

    // FileReader: 아직 서버에 업로드 하지 않은 파일을 브라우저 메모리에 올리기 위해
    //  base64라는 문자열로 만드어주는 객체
    const reader = new FileReader();
    // 자바스크립트는 비동기 방식!
    // 즉, 작업이 완료되기까지 기다려주지 않으므로 이벤트 핸들러를 따로 등록한다.
    reader.onload = function(e) {
        // 프로필 미리보기 영역에 변환된 이미지 파일을 표시
        const profilePreview = document.querySelector("#profile-preview");
        profilePreview.src = e.target.result;
        profilePreview.style.display = "block";

        // 이미지가 없을 경우 표시되는 영역은 display:none 변경
        document.querySelector("#profile-preview-placeholder").style.display = "none";
    }

    reader.readAsDataURL(file);     // 업로드한 파일을 base64 방식으로 변경
});

// -------------------- 비밀번호 일치 여부 확인 ---------------------------------------
const memberPwd = document.querySelector("#member-pwd");
const memberPwdConfirm = document.querySelector("#member-pwd-confirm");

let checkPwd = false;   // 비밀번호 일치 여부
function validatePwdConfirm() {
    const confirmResult = document.querySelector("#check-pwd-result");

    // 비밀번호 확인 입력창이 비어 있을 경우 검사 X
    if (!memberPwdConfirm.value.trim()/* .value.trim(): 비어 있으면 false 반환 */) {
        checkPwd = false;
        return;
    }

    checkPwd = memberPwd.value === memberPwdConfirm.value;

    confirmResult.textContent = checkPwd ? "비밀번호가 일치합니다." : "비밀번호가 일치하지 않습니다."
    confirmResult.className = checkPwd ? "form-tip form-tip-ok" : "form-tip form-tip-error";
}

memberPwd.addEventListener('input', validatePwdConfirm);
// memberPwd.addEventListener('input', function(e) {}); 형태 대신 미리 정의한 함수를 전달
memberPwdConfirm.addEventListener('input', validatePwdConfirm);

// ------- 아이디 [중복확인] 버튼의 클릭 이벤트 리스너 추가 -----------------
let checkId = null;    // ID 중복체크 값
// ID를 입력하는 동안은 null이 되도록 (중복확인 과정에서 오류가 발생할 때도!)

const memberIdInput = document.querySelector("#member-id");
const checkIdResult = document.querySelector("#check-id-result");
const checkIdBtn = document.querySelector("#check-id-btn");
memberIdInput.addEventListener("input", function() {
    checkIdResult.textContent = "";
    // 값이 입력되면 (input) 알림을 지우도록 처리
    checkId = null;
});

checkIdBtn.addEventListener('click',async function(e) {
    // alert("클릭 확인");
    const memberId = memberIdInput.value.trim();
    // 아이디 입력값이 없으면 서버로 중복확인 요청을 하지 않음
    if (memberId.length === 0) {
        
        checkIdResult.textContent = "아이디를 입력해주세용";
        checkIdResult.className = "form-tip form-tip-error";
        checkId = null;
        return;     // 서버로 요청하지 않고 종료
    }
    // 입력된 아이디값이 중복되는지 서버로 확인 요청
    /*
        fetch API
        : 브라우저에서 서버로 요청을 보내고 응답을 받을 수 있게 해주는 자바스크립트 내장 함수
          form 태그의 submit과 달리 "화면 새로고침 없이(즉, 비동기적으로)",
          백엔드 서버와 데이터를 주고 받을 수 있음.
          이러한 통신 방식을 AJAX라고 함.

        
        fetch(URL, settings)
        - URL: 요청을 보낼 주소 (요청을 받아줄 URL을 서버에서 먼저 매핑해줘야 함!!!)
        - settings: 설정 객체 (요청 방식, 헤더, 데이터 등)
                    (POST 방식일 경우 URL에 표시가 안 되니 그때 전달할 데이터를 넣어줌)
            - method: 요청 방식
            - headers: 헤더 설정
        
        encodeURIComponent(): 전달하는 파라미터에 &, =와 같은 특수문자가 있을 경우
                              URL 형식이 깨지는 것을 방지함 (인코딩)
        "X-Requested-With" : "XMLHttpRequest"
        : 이 요청이 브라우저 주소창에서 이동한 것이 아닌,
          자바스크립트(AJAX)를 통해 보낸 것임을 서버에 알려주는 설정
          (필수 설정은 아니고 관례라고 보면 됨)
    */

    try {

        const response = await fetch("/member/checkId?memberId=" + encodeURIComponent(memberId), {
            method: "GET",
            headers: {"X-Requested-With" : "XMLHttpRequest"}
        });
        
        // response.json(): json 응답을 자바스크립트 객체로 변경
        const result = await response.json();
        //  console.log(result);
        checkIdResult.textContent = result.message;
        checkIdResult.className = result.data ? "form-tip form-tip-error" : "form-tip form-tip-ok"

        checkId = result.data? null : memberId;
    } catch (error) {
        // console.log(error)

        checkIdResult.textContent = "중복 확인 중 오류가 발생했습니다.";
        checkIdResult.className = "form-tip form-tip-error";

        checkId = null;
    }

});


// 회원가입 폼 제출 이벤트 핸들러
// 여기까지만 작성하면 비밀번호-비밀번호확인 값이 일치하지 않아도 제출됨
// 비밀번호가 일치했을 때만 제출하도록 처리!
// 아이디 중복 시에도 제출 못 하게 막기
const joinForm = document.querySelector("#join-form");
joinForm.addEventListener("submit", function(e) {
    if (!checkId) {
        e.preventDefault();
        alert("사용할 수 없는 아이디입니다.");
        return;
    }
    
    if (!checkPwd/* 일치하지 않을 때 */) {
        e.preventDefault();     // 기존 폼 제출 동작을 막고,
        alert("비밀번호가 일치하지 않습니다.");     // 알림 띄우고,
        return;                 // 메서드를 중지시킴!
    }
});