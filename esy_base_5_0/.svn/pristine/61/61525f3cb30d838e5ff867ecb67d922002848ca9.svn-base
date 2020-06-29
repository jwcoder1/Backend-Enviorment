
# Base 6.1.0 Readme
---

## 业务流程

### 用户账号操作流程

- 创建用户账号（账号自动生成、密码设定）
	- 手动创建
	- 批量创建
- 用户账号首次登入初始化
	- 输入账号、初始化密码
	- 重置密码（首次登入时配置）
	- 设置别名（可选）
	- 设置手机（可选）
	- 设置邮箱（可选）
- 普通用户登入
	- 输入账号/手机号/邮箱地址/别名、密码验证登入

## Account（用户账号）API

### 创建新用户账号

URL:

- /api/base/account/user/new

Method:

- POST
- Content-Type:application/json

Parameters：

	{
		"username":"account",
		"password":"password"
	}

- username：账号名称
- password：初始密码

### 创建管理员账号

URL:

- /api/base/account/user/newadmin

Method:

- POST
- Content-Type:application/json

Parameters：

	{
		"username":"account",
		"password":"password"
	}

- username：账号名称
- password：初始密码

### 用户账号登入

URL:

- /api/login

Method:

- POST
- Content-Type:application/json

Parameters：

	{
    	"username":"admin",
    	"password":"admin",
    	"rememberMe":false
	}

- username：账号
- password：密码
- rememberMe：保存密码

### 用户密码修改

URL:

- /api/setpassword

Method:

- POST
- Content-Type:application/json

Parameters：

	{
    	"newPassword":"admin1",
    	"confirmPassword":"admin1",
    	"oldPassword":"admin"
	}

- newPassword：新密码
- confirmPassword：新密码确认
- oldPassword：原密码

### 管理员密码修改

URL:

- /api/setpassword/{userid}

Method:

- POST
- Content-Type:application/json

Parameters：

	{
    	"newPassword":"admin1",
    	"confirmPassword":"admin1"
	}

- {userid}：用户账号
- newPassword：新密码
- confirmPassword：新密码确认


## Entity Searching Options ##
- org.yessoft.base.entity.Account
- 
	- Search
		- aid:eq
		- type:eq
		- matrixNo:eq
		- name:match
		- mobile	:eq
		- mail:match
		- master	:eq
		- parent	:eq
		- lastLogin:lte,gte
	- Order by
		- default:aid
		- lastLogin:lastLogin desc
		- created:created desc
		- updated:updated desc
		
- org.yessoft.base.entity.Log
- 
	- Search
		- moduleId:eq
		- eventId:eq
		- user:eq
		- info:match
		- time:lte,gte
	- Order by
		- default:time desc
		- localIp:localIp
		- remoteIp:remoteIp
		- user:user
- org.yessoft.base.entity.LogEvent
-  
	- Search
		- moduleId:eq
		- eventId:eq
	- Order by
		- default:moduleId, eventId
- org.yessoft.base.entity.LogModule
- 
	- Search
		- mId:eq
		- name:match
	- Order by
		- default:mId
- org.yessoft.base.entity.Menu
- 
	- Search
		- mid:eq
		- pid:eq
		- name:match
		- type:eq
	- Order by
		- default:pid, mid
- org.yessoft.base.entity.MQueueCfg
- 
	- Search
		- moduleId:eq
		- qid:eq
		- name:match
		- category:match
	- Order by
		- default:qid
- org.yessoft.base.entity.MQueue
- 
	- Search
		- qid:eq
		- seq:eq
	- Order by
		- default:seq desc
		- updated:updated desc
		- created:created desc
- org.yessoft.base.entity.Role
- 
	- Search
		- rid:rid eq
		- name:name match
		- describe:describe match
	- Order by
		- default:rid
- org.yessoft.base.entity.RoleMember
- 
	- Search
		- roleId:eq
		- accountId:eq
	- Order by
		- default:roleId, accountId
- org.yessoft.base.entity.RoleMenu
- 
	- Search
		- menuId:eq
		- roleId:eq
		- enable:eq
	- Order by
		- default:pid, mid
- org.yessoft.base.entity.Serial
- 
	- Search
		- tableName:eq
		- fieldName:eq
		- serialKey:eq
	- Order by
		- tableName, fieldName, serialKey