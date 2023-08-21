$(()=>{
function editStudent(data,success){
var obj = {
'url' : 'student/editStudent',
'type' : 'POST',
'data' : data,
'success' : function(result){success(result);}
};
$.ajax(obj)
}
});