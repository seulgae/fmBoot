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
})