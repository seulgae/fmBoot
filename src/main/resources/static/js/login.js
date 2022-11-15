$(function(){
    $("#loginBtn").click(function(){
        $.ajax({
            type : "POST",
            url : "loginCheck",
            data : {
                "m_id" : $("#m_id").val(),
                "m_pw" : $("#m_pw").val()
            },
            success : function(data){
                if(data=="1"){
                    alert($("#m_id").val()+"님 환영합니다.");
                    $("#loginFrm").submit();
                }else{
                    alert("로그인에 실패하였습니다. 다시 확인해주세요.");
                    return false;
                }
            }
        })
    })
    $("#loginFind").click(function(){
        window.open('/login/findId', 'pop01', 'top=10, left=10, width=550, height=250, status=no, menubar=no, toolbar=no, resizable=no');
    })
    $("#btnFindId").click(function(){
        var checkedValue = $("input[name='findId']:checked").val();
        var findValue = $("#findValue").val();
        if(findValue==""){
            alert("검색어를 입력해주세요.");
            $("#findValue").focus();
            return false;
        }
        location.href="/login/wannaGetId/"+checkedValue+"/"+findValue;
    })

    $("#btnFindPw").click(function(){
        var pw_id =$("#pw_id").val();
        var pw_email =$("#pw_email").val();
        if(pw_id==""){
            alert("아이디를 입력해주세요.");
            $("#pw_id").focus();
            return false;
        }
        if(pw_email==""){
            alert("이메일을 입력해주세요.");
            $("#pw_email").focus();
            return false;
        }

        location.href="/login/wannaGetPw/"+pw_id+"/"+pw_email;
    })

    $("#btnChange").click(function(){
        if($("#m_pw").val()!=$("#m_pw1").val()){
            alert("비밀번호가 상이합니다.");
            return false;
        }
        $("#frmChange").submit();
    })
})