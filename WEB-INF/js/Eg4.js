class Eg4Service
{
	test()
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
			xmlHttpRequest.open('GET', 'bobby/eg4/test',true);
			xmlHttpRequest.send();
		});
		return promise;
	}
	test1(eg4)
	{
		var data = {
			'x' : eg4.getX(),
			'y' : eg4.getY()
		};
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
			xmlHttpRequest.open('GET', 'bobby/eg4/test1?x='+data.x+'&y='+data.y+'',true);
			xmlHttpRequest.send();
		});
		return promise;
	}
	test2(eg4)
	{
		var data = {
			'student' : eg4.getStudent(),
		};
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
			xmlHttpRequest.open('POST', 'bobby/eg4/test2',true);
			xmlHttpRequest.setRequestHeader('content-type', 'application/json');
			xmlHttpRequest.send(JSON.stringify(data.student));
		});
		return promise;
	}
	test4(eg4)
	{
		var data = {
			'student' : eg4.getStudent(),
		};
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
			xmlHttpRequest.open('POST', 'bobby/eg4/test4',true);
			xmlHttpRequest.setRequestHeader('content-type', 'application/json');
			xmlHttpRequest.send(JSON.stringify(data.student));
		});
		return promise;
	}
	test3(eg4)
	{
		var data = {
			'x' : eg4.getX(),
			'y' : eg4.getY(),
		};
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
			xmlHttpRequest.open('POST', 'bobby/eg4/test3',true);
			xmlHttpRequest.setRequestHeader('content-type', 'application/x-www-form-urlencoded');
			xmlHttpRequest.send('x='+data.x+'&y='+data.y+'');
		});
		return promise;
	}
}
class Eg4
{
	setX(x)
	{
		this.x = x;
	}
	getX()
	{
		return this.x;
	}
	setY(y)
	{
		this.y = y;
	}
	getY()
	{
		return this.y;
	}
	setStudent(student)
	{
		this.student = student;
	}
	getStudent()
	{
		return this.student;
	}
	setStudent(student)
	{
		this.student = student;
	}
	getStudent()
	{
		return this.student;
	}
	setX(x)
	{
		this.x = x;
	}
	getX()
	{
		return this.x;
	}
	setY(y)
	{
		this.y = y;
	}
	getY()
	{
		return this.y;
	}
}
