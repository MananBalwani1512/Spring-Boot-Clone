class eg2Service
{
	getName()
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
			xmlHttpRequest.open('GET', 'bobby/teacher/getName',true);
			xmlHttpRequest.send();
		});
		return promise;
	}
	add()
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
			xmlHttpRequest.open('POST', 'bobby/teacher/add',true);
			xmlHttpRequest.send();
		});
		return promise;
	}
	delete()
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
			xmlHttpRequest.open('GET', 'bobby/teacher/delete',true);
			xmlHttpRequest.send();
		});
		return promise;
	}
	getAll(eg2)
	{
		var data = {
			'x' : eg2.getX(),
			'y' : eg2.getY(),
			'z' : eg2.getZ()
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
			xmlHttpRequest.open('GET', 'bobby/teacher/getAll?x='+data.x+'&y='+data.y+'&z='+data.z+'',true);
			xmlHttpRequest.send();
		});
		return promise;
	}
	getNaam()
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
			xmlHttpRequest.open('GET', 'bobby/teacher/naam',true);
			xmlHttpRequest.send();
		});
		return promise;
	}
	edit()
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
			xmlHttpRequest.open('GET', 'bobby/teacher/edit',true);
			xmlHttpRequest.send();
		});
		return promise;
	}
	getByEmail(eg2)
	{
		var data = {
			'student1' : eg2.getStudent1(),
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
			xmlHttpRequest.open('POST', 'bobby/teacher/getByEmail',true);
			xmlHttpRequest.setRequestHeader('content-type', 'application/json');
			xmlHttpRequest.send(JSON.stringify(data.student1));
		});
		return promise;
	}
}
class eg2
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
	setZ(z)
	{
		this.z = z;
	}
	getZ()
	{
		return this.z;
	}
	setStudent1(student1)
	{
		this.student1 = student1;
	}
	getStudent1()
	{
		return this.student1;
	}
}
