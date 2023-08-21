$(()=>{
function deleteStudent(data,success){
var obj = {
'url' : 'student/deleteStudent',
'type' : 'POST',
'data' : data,
'success' : function(result){success(result);}
};
$.ajax(obj)
}
});