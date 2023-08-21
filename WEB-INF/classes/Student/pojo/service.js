$(()=>{
function getAll(success){
var obj = {
'url' : '/student/getAll',
'type' : 'GET',
'success' : function(result){success(result);}
};
$.ajax(obj)
}
function getStudentByRoll(data,success){
var obj = {
'url' : '/student/getStudentByRoll',
'type' : 'GET',
'data' : data,
'success' : function(result){success(result);}
};
$.ajax(obj)
}
function deleteStudent(data,success){
var obj = {
'url' : '/student/deleteStudent',
'type' : 'GET',
'data' : data,
'success' : function(result){success(result);}
};
$.ajax(obj)
}
function addStudent(data,success){
var obj = {
'url' : '/student/addStudent',
'type' : 'GET',
'data' : data,
'success' : function(result){success(result);}
};
$.ajax(obj)
}
function editStudent(data,success){
var obj = {
'url' : '/student/editStudent',
'type' : 'GET',
'data' : data,
'success' : function(result){success(result);}
};
$.ajax(obj)
}
});