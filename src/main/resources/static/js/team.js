$(function(){
    $("#btnFind").click(function(){
        let m_id = $("#findMember").val();
        if(m_id == null || m_id == ""){
            alert("멤버아이디를 입력해주세요.");
            return false;
        }else{
            $.ajax({
                url : "/teammanage/findMember",
                type : "POST",
                data : {
                    "m_id" : m_id
                },
                success : function(data){
                    $("#memberList").html(data);
                }
            })
        }
    })
})
var arr = new Array();
function add(m_id){
    var result = arr.filter(member_id => member_id == m_id);
    if(result !=""){
        alert("이미 추가한 팀원입니다.");
        return false;
    }
    var str = "";
    arr.push(m_id);
    str += "<tr id='tr_"+m_id+"'>";
    str += "<td >";
    str += m_id;
    str += "</td>";
    str += "<td>";
    str += "<input type='button' value='삭제' class='btn btn-danger' onclick=\"deleteMem(\'"+m_id+"\')\">";
    str += "</td>";
    str += "</tr>";
    $("#table_add").append(str);
    $("#addMemberList").css("display","");
}

function deleteMem(m_id){
    if(confirm(m_id+" 팀원을 삭제하시겠습니까?")){
        arr.splice(arr.indexOf(m_id),1);
        $("#tr_"+m_id).remove();
    }else{
        return false;
    }
}

function insertTeam(){
    var str = "";
    for(var i = 0; i < arr.length; i++){
        str += arr[i]+" ";
    }
    $("#str_member").val(str);
    $("#frmAddMember").submit();
}