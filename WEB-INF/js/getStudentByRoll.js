$(()=>{
function getStudentByRoll(data,success){
var obj = {
'url' : 'student/getStudentByRoll',
'type' : 'POST',
'data' : data,
'success' : function(result){success(result);}
};
$.ajax(obj)
}
});