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
    var str = "";
    arr.push(m_id);
    str += "<tr>";
    str += "<td>";
    str += m_id;
    str += "</td>";
    str += "<td>";
    str += "<input type='button' value='삭제' onclick='deleteMem('"+m_id+"')''>";
    str += "</td>";
    str += "</tr>";
    $("#table_add").before(str);
}

function deleteMem(m_id){
    alert(m_id);
}