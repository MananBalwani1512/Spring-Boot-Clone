class eg1Service
{
	editStudent()
	{
		var promise = new Promise((resolve,reject)=>
		{
			var xmlHttpRequest = new XMLHttpRequest();
			xmlHttpRequest.onreadystatechange = ()=>
			{
				if(xmlHttpRequest.readyState == 4)
				{
					if(xmlHttpRequest.status == 200)
					{
						var response = xmlHttpRequest.responseText;
						resolve(response);
					}
					else
					{
						reject();
					}
				}
			}
			xmlHttpRequest.open('GET', 'bobby/student/edit',true);
			xmlHttpRequest.send();
		});
		return promise;
	}
	addStudent()
	{
		var promise = new Promise((resolve,reject)=>
		{
			var xmlHttpRequest = new XMLHttpRequest();
			xmlHttpRequest.onreadystatechange = ()=>
			{
				if(xmlHttpRequest.readyState == 4)
				{
					if(xmlHttpRequest.status == 200)
					{
						var response = xmlHttpRequest.responseText;
						resolve(response);
					}
					else
					{
						reject();
					}
				}
			}
			xmlHttpRequest.open('GET', 'bobby/student/addStudent',true);
			xmlHttpRequest.send();
		});
		return promise;
	}
	deleteStudent()
	{
		var promise = new Promise((resolve,reject)=>
		{
			var xmlHttpRequest = new XMLHttpRequest();
			xmlHttpRequest.onreadystatechange = ()=>
			{
				if(xmlHttpRequest.readyState == 4)
				{
					if(xmlHttpRequest.status == 200)
					{
						var response = xmlHttpRequest.responseText;
						resolve(response);
					}
					else
					{
						reject();
					}
				}
			}
			xmlHttpRequest.open('GET', 'bobby/student/delete',true);
			xmlHttpRequest.send();
		});
		return promise;
	}
	getStudent()
	{
		var promise = new Promise((resolve,reject)=>
		{
			var xmlHttpRequest = new XMLHttpRequest();
			xmlHttpRequest.onreadystatechange = ()=>
			{
				if(xmlHttpRequest.readyState == 4)
				{
					if(xmlHttpRequest.status == 200)
					{
						var response = xmlHttpRequest.responseText;
						resolve(response);
					}
					else
					{
						reject();
					}
				}
			}
			xmlHttpRequest.open('GET', 'bobby/student/getStudent',true);
			xmlHttpRequest.send();
		});
		return promise;
	}
}
