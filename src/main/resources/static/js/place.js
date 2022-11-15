$(function(){
    $("#btnReservation").click(function(){
      if($("#loginCheck").val() == ""){
        alert('로그인을 해주세요.');
        location.href="/login/login";
      }else if($("#dateSet").val() == ""){
        alert('날짜를 선택해주세요.');
        return false;
      }else if($("#time").val() == ""){
        alert('시간을 선택해주세요.');
        return false;
      }else{
        $("#frmReservation").submit();
      }
    })
    setDate();
    $("#div_explain").html($("#hidden_explain").val());
 })
function setDate(){
    $("#time").val("");
    timeSet();
    $.ajax({
        type : "POST",
        url : "/payment/rserveCheck",
        data : {
            "r_date" : $("#dateSet").val(),
            "p_no" : $("#p_no").val()
        },
        success : function(data){
            for(var i = 0; i < data.length; i++){
                if(data[i] == '12:00~14:00'){
                    $("#td_1").css("background-color","red");
                }
                if(data[i] == '14:00~16:00'){
                    $("#td_2").css("background-color","red");
                }
                if(data[i] == '16:00~18:00'){
                    $("#td_2").css("background-color","red");
                }
                if(data[i] == '18:00~20:00'){
                    $("#td_2").css("background-color","red");
                }
                if(data[i] == '20:00~22:00'){
                    $("#td_2").css("background-color","red");
                }
            }
        }
    })
}
function selected(value){
    if($("#td_"+value).css("background-color")=="red"){
        alert("선택할수없습니다.");
        return false;
    }
    var result = otherBackSet(value);
    if(result!=false){
        $("#td_"+value).css("background-color","yellow");
    }
    if(value=="1"){
        $("#time").val("12:00~14:00");
    }else if(value=="2"){
        $("#time").val("14:00~16:00");
    }else if(value=="3"){
        $("#time").val("16:00~18:00");
    }else if(value=="4"){
        $("#time").val("18:00~20:00");
    }else if(value=="5"){
        $("#time").val("20:00~22:00");
    }
}
function otherBackSet(value){
    if($("#td_"+value).css("background-color")==="rgb(255, 0, 0)"){
        alert("선택이 불가합니다.");
        return false;
    }else{
        for(var i = 1; i <= 5; i++){
            if(i != value){
                if($("#td_"+i).css("background-color")!=="rgb(255, 0, 0)"){
                    $("#td_"+i).css("background-color","gray");
                }
            }
        }
    }
}

function timeSet(){
    $("#td_1").css("background-color","gray");
    $("#td_2").css("background-color","gray");
    $("#td_3").css("background-color","gray");
    $("#td_4").css("background-color","gray");
    $("#td_5").css("background-color","gray");
    let today = new Date();
    let year = today.getFullYear(); // 년도
    let month = today.getMonth() + 1;  // 월
    let date = today.getDate();  // 날짜
    let hours = today.getHours(); // 시
    let minutes = today.getMinutes();  // 분

    let time = hours + ':' + minutes;
    today = year+"-"+month+"-"+date;
    if($("#dateSet").val() <= today){
        if(time > "14:00"){
            $("#td_1").css("background-color","red");
        }
        if(time > "16:00"){
            $("#td_2").css("background-color","red");
        }
        if(time > "18:00"){
            $("#td_3").css("background-color","red");
        }
        if(time > "20:00"){
            $("#td_4").css("background-color","red");
        }
        if(time > "22:00"){
            $("#td_5").css("background-color","red");
        }
    }
}

