$(()=>{
function addStudent(data,success){
var obj = {
'url' : 'student/addStudent',
'type' : 'POST',
'data' : data,
'success' : function(result){success(result);}
};
$.ajax(obj)
}
});