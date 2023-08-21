$(()=>{
function getAll(success){
var obj = {
'url' : 'student/getAll',
'type' : 'GET',
'success' : function(result){success(result);}
};
$.ajax(obj)
}
});