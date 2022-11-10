$(function(){
    week = new Array('일', '월', '화', '수', '목', '금', '토');
    date = new Date();
    year = date.getFullYear();
    month = date.getMonth() + 1;
    day = date.getDate();
    dayW = week[date.getDay()];

    $("#current_date").html(year + "." + month + "." + day + " (" + dayW + ")");

//
//    if($("#op1").val() == "1"){
//        $("#divOp1").css("background-color","#646464");
//    }else{
//        $("#divOp1").css("background-color","#FFFFFF");
//    }
//    if($("#op2").val() == "1"){
//        $("#divOp2").css("background-color","#646464");
//    }else{
//        $("#divOp2").css("background-color","#FFFFFF");
//    }
//    if($("#op3").val() == "1"){
//        $("#divOp3").css("background-color","#646464");
//    }else{
//        $("#divOp3").css("background-color","#FFFFFF");
//    }
//    if($("#op4").val() == "1"){
//        $("#divOp4").css("background-color","#646464");
//    }else{
//        $("#divOp4").css("background-color","#FFFFFF");
//    }
//    if($("#op5").val() == "1"){
//        $("#divOp5").css("background-color","#646464");
//    }else{
//        $("#divOp5").css("background-color","#FFFFFF");
//    }
//    if($("#op6").val() == "1"){
//        $("#divOp6").css("background-color","#646464");
//    }else{
//        $("#divOp6").css("background-color","#FFFFFF");
//    }

})