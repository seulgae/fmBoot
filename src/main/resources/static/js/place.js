$(function(){
    $("#btnReservation").click(function(){
      if($("#loginCheck").val() == ""){
        alert('로그인을 해주세요.');
        location.href="/login/login";
      }else if($("#dateSet").val() == ""){
        alert('날짜를 선택해주세요.');
        return false;
      }else if($("#time option:selected").val() == ""){
        alert('시간을 선택해주세요.');
        return false;
      }else{
        $("#frmReservation").submit();
      }
    })

    $("#dateSet").change(function(){
        $("#time option[value='12:00~14:00']").prop('disabled',false);
        $("#time option[value='14:00~16:00']").prop('disabled',false);
        $("#time option[value='16:00~18:00']").prop('disabled',false);
        $.ajax({
            type : "POST",
            url : "/payment/rserveCheck",
            data : {
                "r_date" : $("#dateSet").val(),
                "p_no" : $("#p_no").val()
            },
            success : function(data){
                for(var i = 0; i < data.length; i++){
                    $("#time option[value='"+data[i]+"']").prop('disabled',true);
                }
            }
        })
    })
 })