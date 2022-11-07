$(function(){
	//////////////  아이디 중복 검사 시작 //////////////////////			
	function idCheck() {
		
		//// 아이디 => 영어, 숫자, 특수기호(_), 5글자 이상 20글자 이하
		let uId = $("#m_id").val();
		let uIdLen  = uId.length;
		let uIdReg = /[^a-z|A-Z|0-9|_]/;
		if (uIdLen < 5) {
			alert("ID는 영어, 숫자, 특수기호(_), 5글자 이상 20글자 이하");
			$("#m_id").focus();
			return;
		} else if(uIdReg.test(uId)) {
			alert("ID는 영어, 숫자, 특수기호(_), 5글자 이상 20글자 이하");
			$("#m_id").focus();
			return;
		}
		
		let url = "/idCheck?uId=" + uId;
		let w = screen.width;
		let popW = 400;
		let leftP = (w-popW)/2;
		let h = screen.height;
		let popH = 300;
		let topP = (h-popH)/2;
		//alert("w : " + w + "\nh : " + h);
		
		window.open(url, "IdCheck", "width="+popW+", height="+popH+", left="+leftP+", top="+topP);
		
	}
	
	
	$("#idChkBtn").click(function(){
		idCheck();
	});
	//////////////  아이디 중복 검사 끝 //////////////////////
		
	////////////// 사용자 우편번호 주소 확인 시작 //////////////////////
	function zipCheck() {	
		new daum.Postcode({
   			 oncomplete: function(data) {
       		 // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var roadAddr = data.roadAddress; // 도로명 주소 변수

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById("m_zip").value = data.zonecode;
                document.getElementById("m_addr1").value = roadAddr;
                document.getElementById("m_addr2").focus(); // 상세주소로 포커스
            }
        }).open();	
	}
	
	$("#zipBtn").click(function(){
		zipCheck();
	});

	////////////// 우편번호 주소 확인 끝 //////////////////////
		
	////////////// 회원가입 유효성 검사 끝 //////////////////////
	$("#joinBtn").click(function(){
	
		
		let uIdBtnClickChk = $("#uIdBtnClickChk").val();
		let uPw = $("#m_pw").val();
		let uPw_Re = $("#m_pw_Re").val();
		let uName = $("#m_name").val();
		let uPhone = $("#m_phone").val();
		let uZip = $("#m_email").val();
		
		
		if(uIdBtnClickChk == 0) {    	// 아이디 검사 시작
			alert("ID중복확인 버튼을 클릭하세요.");
			$("#idChkBtn").focus();
			
			$("#idChkBtn").focus(function(){
				$(this).css({"outline": "1px solid #555"});	
			}).blur(function(){
				$(this).css({"outline": "none"});	
			});					
			// 아이디 검사 끝
		} else if (uPw == "") {     // 비밀번호 검사 시작
			alert("비밀번호를 입력하세요.");
			$("#m_pw").focus();
		} else if (uPw != uPw_Re) {     // 비밀번호 동일검사 시작
			alert("비밀번호가 다릅니다. 확인 후 다시 입력하세요.");
			$("#m_pw_Re").val("");
			$("#m_pw").focus();
		} else if (uName == "") {     // 이름 검사 시작
			alert("이름을 입력하세요.");
			$("#uName").focus();
		} else if (m_name == "") {     // 전화번호 검사 시작
			alert("전화번호를 입력하세요.");
			$("#m_phone").focus();
		} else if (uZip == "") {     // 주소 1검사 시작
			alert("주소를 입력하세요.");
			$("#zipBtn").focus(function(){
				$(this).css({"outline": "1px solid #555"});	
			}).blur(function(){
				$(this).css({"outline": "none"});	
			});
		} else if (uEmail == "") {     // 이메일 검사 시작
			alert("이메일을 입력하세요.");
			$("#m_email").focus();
		} else if (uEmail.indexOf("@") < 0 || uEmail.indexOf(".") < 0) {     // 이메일 검사 시작
			alert("이메일주소를 확인하세요.");
			$("#m_email").focus();
		} else if (!uChk1) {     // 체크박스 여부 확인1
			alert("만 14세 이상 동의 여부를 확인해주세요.");
		} else if (!uChk2) {     // 체크박스 여부 확인2
			alert("이용약관 동의 여부를 확인해주세요.");
		} else if (!uChk3) {     // 체크박스 여부 확인3
			alert("개인정보 수집 동의 여부를 확인해주세요.");
		} else {
			$("#regFrm").submit();
		}
		
	});
////////////// 회원가입 유효성 검사 끝 //////////////////////
	
//////////////  체크박스 전부 동의하기 시작 /////////////
    $("#allChk").click(function(){
        let checked = $("#allChk").is(":checked");

        if(checked)
            $("input:checkbox").prop("checked", true);
        else
            $("input:checkbox").prop("checked", false);
    });
	
	 //////////////  체크박스 전부 동의하기 끝 //////////////	
				
	///////////  로그인 페이지 이동 시작 ////////////////
	$("#loginBtn").click(function(){
		location.href = "/login";
	});	
});	