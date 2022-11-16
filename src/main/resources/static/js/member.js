$(function(){

        /* 초기 라디오버튼 사용자 셀렉트 (START)*/

        if($('input[type=radio][name=m_level]').val()=="1"){
            $(".manager").css('display', 'none');
            $(".userchk").css('display', 'inline-block');
            $("#m_zip").attr("placeholder","우편번호를 입력해주세요.");
            $("#m_addr1").attr("placeholder","주소를 입력해주세요.");
            $("#m_addr2").attr("placeholder","상세주소를 입력해주세요.");
        }

        /* 초기 라디오버튼 사용자 셀렉트 (END)*/

        /* 아이디 중복 및 글자 수 검사(START) */

		$("#m_id").keyup(function(){
			//// 아이디 => 영어, 숫자, 특수기호(_), 5글자 이상 20글자 이하
			let m_id = $("#m_id").val();
			let uIdLen  = m_id.length;
			let uIdReg = /[^a-z|A-Z|0-9|_]/;

			if (uIdLen < 5) {
				$("#idCheck").html("영어, 숫자, 특수기호(_)만 사용가능, 5글자 이상 20글자 이하");
				$("#m_id").focus();
				$("#idCheck").css("color","red");
				return false;
			} else if(uIdReg.test(m_id)) {
				$("#idCheck").html("영어, 숫자, 특수기호(_)만 사용가능, 5글자 이상 20글자 이하");
				$("#m_id").focus();
				$("#idCheck").css("color","red");
				return false;
			}else{
				$.ajax({
					type : 'GET',
					url : 'idCheck',
					data : {
						"m_id" : m_id
					},
					success : function(flag){
						if(flag==0){
							$("#idCheck").html("사용가능한 ID입니다.");
				            $("#idCheck").css("color","blue");
							$("#idCheckFlag").val("1");
						}else{
				            $("#idCheck").css("color","red");
							$("#idCheck").html("이미 사용중인 ID입니다.");
							$("#idCheckFlag").val("0");
						}
					}
				});
			}
		})
        /* 아이디 중복 및 글자 수 검사(END) */

        /* 패스워드 체크 (START)*/

        $("#m_pw").keyup(function(){
            pwTest();
        })

        $("#m_pw_re").keyup(function(){
            pwTest();
        })

        function pwTest(){
            if($("#m_pw").val() != "" &&$("#m_pw").val() == $("#m_pw_re").val()){
                $("#pwCheck").css("color","blue");
                $("#pwCheck").html("비밀번호가 일치합니다.");
                $("#pwCheckFlag").val("1");
            }else{
                $("#pwCheck").css("color","red");
                $("#pwCheck").html("비밀번호가 일치하지 않습니다.");
                $("#pwCheckFlag").val("0");
            }
        }
        /* 패스워드 체크 (END)*/

        /* 전화번호 이벤트 (START)*/

        $("#m_phone").keyup(function(){
            $(this).val( $(this).val().replace(/[^0-9]/g, "").replace(/(^02|^0505|^1[0-9]{3}|^0[0-9]{2})([0-9]+)?([0-9]{4})$/,"$1-$2-$3").replace("--", "-") );
        });

        /* 전화번호 이벤트 (END)*/

        /* 1:사용자 2: 매니저 라디오 버튼 이벤트(START)*/

        $('input[type=radio][name=m_level]').change(function() {
        	if (this.value == '1') {
        		$(".manager").css('display', 'none');
        		$(".userchk").css('display', 'inline-block');
                $("#m_zip").attr("placeholder","우편번호를 입력해주세요.");
                $("#m_addr1").attr("placeholder","주소를 입력해주세요.");
                $("#m_addr2").attr("placeholder","상세주소를 입력해주세요.");
                $("#mFlag").val("0");
        	}
        	else if (this.value == '2') {
        		$(".userchk").css('display', 'none');
        		$(".manager").css('display', 'inline-block');
                $("#m_zip").attr("placeholder","우편번호를 입력해주세요.");
                $("#m_addr1").attr("placeholder","주소를 입력해주세요.");
                $("#m_addr2").attr("placeholder","상세주소를 입력해주세요.");
                $("#mFlag").val("1");
        	}
        });

        /* 1:사용자 2: 매니저 라디오 버튼 이벤트(END)*/


        /* 우편번호 찾기 이벤트 (START) */

        $("#zipBtn").click(function(){
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
        });

        /* 우편번호 찾기 이벤트 (END) */

        /* 전체동의 체크 이벤트 (START)*/

        $("input:checkbox[id='allChk']").change(function(){
            if($("input:checkbox[id='allChk']").prop("checked",true)){
                $("input:checkbox[id='uChk1']").prop("checked",true);
                $("input:checkbox[id='uChk2']").prop("checked",true);
                $("input:checkbox[id='uChk3']").prop("checked",true);
            }
        })

        /* 전체동의 체크 이벤트 (END)*/

        /* join버튼 클릭 이벤튼 (START)*/

        $("#joinBtn").click(function(){
            let idCheck = $("#idCheckFlag").val();//아이디 중복체크
            let pwCheck = $("#pwCheckFlag").val();//패스워드 비교 체크
            if(idCheck == "0"){
                alert("아이디를 확인해주세요.");
                $("#m_id").focus();
                return false;
            }

            if(pwCheck == "0"){
                alert("패스워드를 확인해주세요.");
                $("#m_pw").focus();
                return false;
            }

            if($("#m_name").val() == ""){
                alert("이름을 입력해주세요.");
                $("#m_name").focus();
                return false;
            }

            if($("#m_phone").val() == ""){
                alert("전화번호를 입력해주세요.");
                $("#m_phone").focus();
                return false;
            }

            if($("#m_email").val() == ""){
                alert("이메일을 입력해주세요.");
                $("#m_name").focus();
                return false;
            }

            if($("#m_zip").val() == ""){
                alert("우편번호를 입력해주세요.");
                $("#m_zip").focus();
                return false;
            }

            if($("#m_addr1").val() == ""){
                alert("주소를 입력해주세요.");
                $("#m_addr1").focus();
                return false;
            }

            if($("#m_addr2").val() == ""){
                alert("상세주소를 입력해주세요.");
                $("#m_addr2").focus();
                return false;
            }

            if($("#mFlag").val()=="1"){
//                if($("#m_pname").val() == ""){
//                    alert("구장명을 입력해주세요.");
//                    $("#m_pname").focus();
//                    return false;
//                }
                if($("#m_bank").val() == ""){
                    alert("은행명을 입력해주세요.");
                    $("#m_bank").focus();
                    return false;
                }
                if($("#m_account").val() == ""){
                    alert("계좌번호를 입력해주세요.");
                    $("#m_account").focus();
                    return false;
                }
            }

            if($("#uChk1").prop("checked")==false){
                alert("만 14세이상 동의를 체크해주세요.");
                return false;
            }

            if($("#uChk2").prop("checked")==false){
                alert("이용약관 동의를 체크해주세요.");
                return false;
            }

            if($("#uChk3").prop("checked")==false){
                alert("개인정보 수집·이용 동의를 체크해주세요.");
                return false;
            }

            $("#joinFrm").submit();
        });

        /* join버튼 클릭 이벤튼 (END)*/
});
