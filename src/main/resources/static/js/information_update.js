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
            let pwCheck = $("#pwCheckFlag").val();//패스워드 비교 체크

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
                if($("#m_pname").val() == ""){
                    alert("구장명을 입력해주세요.");
                    $("#m_pname").focus();
                    return false;
                }
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


            $("#joinFrm").submit();
        });

        /* join버튼 클릭 이벤튼 (END)*/
});
