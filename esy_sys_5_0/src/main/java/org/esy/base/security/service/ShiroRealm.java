package org.esy.base.security.service;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.esy.base.common.BaseUtil;
import org.esy.base.entity.Account;
import org.esy.base.security.core.SSOAuth;
import org.esy.base.security.core.WeChaOAuth;
import org.esy.base.security.util.BCrypt;
import org.esy.base.security.util.Encodes;
import org.esy.base.service.IAccountService;
import org.esy.base.util.YESUtil;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Objects;

public class ShiroRealm extends AuthorizingRealm {

	@Autowired
	private IAccountService accountService;

	// 消息推送给新平台后,新平台跳转到重创使用的特殊登录密码
	public final static String FS_BLYS_TO_ZC_P = ")(ZXDTerwm!^&()_F!@#Q#4f32412^+)(@%(_)(#$ewr34$#^^%*^$#@$#YFCFE$^UHFQW$67657gdf&&867846234fsdfwqryuio";

	/**
	 * 登入时调用此方法
	 * 
	 * 查找用户，返回用户 Entity
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		Account account = this.accountService.getById(token.getUsername());
		if (account == null) {
			return null;// 用户名不存在
		}

		return new SimpleAuthenticationInfo(new ShiroUser(token.getUsername(), account.getName()),
				account.getPassword(), getName());

	}

	/**
	 * 取得用户的权限清单
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
		String id = shiroUser.userLoginID;
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// 获取用户授权信息
		HashSet<String> sets = accountService.getAllPerdition(id);
		HashSet<String> roles = new HashSet<String>();
		info.setStringPermissions(sets);
		roles.add("workflow");
		info.setRoles(roles);
		return info;
	}

	/**
	 * 设定Password校验的.
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		setCredentialsMatcher(new CustomCredentialsMatcher());
	}

	/**
	 * 
	 * @Title: 自定义 密码验证类
	 * @author: duiqing.huang
	 * @date: 2014年5月30日 下午4:43:44
	 * @version: V1.0
	 * 
	 */
	public static class CustomCredentialsMatcher extends SimpleCredentialsMatcher {
		@Override
		public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
			UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
			if (SSOAuth.isSSORequest()) {// 如果是中烟门户单点那么就不需验证
				return true;
			} else if (WeChaOAuth.isWeChaRequest()) {
				return true;
			} else if ("".equals(getCredentials(info)) && "".equals(String.valueOf(token.getPassword()))) {
				return true; // 空密码
			} else if (FS_BLYS_TO_ZC_P.equals(String.valueOf(token.getPassword()))) {
				return true;
			} else {
				return BCrypt.checkpw(String.valueOf(token.getPassword()), BaseUtil.toString(getCredentials(info)));
			}
		}

	}

	/**
	 * 更新用户授权信息缓存.
	 */
	public void clearCachedAuthorizationInfo(String principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
		clearCachedAuthorizationInfo(principals);
	}

	/**
	 * 清除所有用户授权信息缓存.
	 */
	public void clearAllCachedAuthorizationInfo() {
		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		if (cache != null) {
			for (Object key : cache.keys()) {
				cache.remove(key);
			}
		}
	}

	private static final String OR_OPERATOR = " or ";

	private static final String AND_OPERATOR = " and ";

	private static final String NOT_OPERATOR = "not ";

	/**
	 * 支持or and not 关键词 不支持 and or 混用
	 * 
	 * @param principals
	 * @param permission
	 * @return
	 */
	public boolean isPermitted(PrincipalCollection principals, String permission) {
		if (permission.contains("||") || permission.contains(",") || permission.contains(OR_OPERATOR)) {
			String[] permissions = permission
					.split(permission.contains("||") ? "\\|\\|" : permission.contains(",") ? "," : OR_OPERATOR);
			for (String orPermission : permissions) {
				if (isPermittedWithNotOperator(principals, orPermission)) {
					return true;
				}
			}
			return false;
		} else if (permission.contains("&&") || permission.contains(AND_OPERATOR)) {
			String[] permissions = permission.split(permission.contains("&&") ? "&&" : AND_OPERATOR);
			for (String orPermission : permissions) {
				if (!isPermittedWithNotOperator(principals, orPermission)) {
					return false;
				}
			}
			return true;
		} else {
			return isPermittedWithNotOperator(principals, permission);
		}
	}

	private boolean isPermittedWithNotOperator(PrincipalCollection principals, String permission) {
		if (permission.startsWith(NOT_OPERATOR)) {
			return !super.isPermitted(principals, permission.substring(NOT_OPERATOR.length()));
		} else {
			return super.isPermitted(principals, permission);
		}
	}

	/**
	 * 自定义Authentication对象，
	 */
	public static class ShiroUser implements Serializable {
		private static final long serialVersionUID = -1373760761780840081L;

		private String userLoginID;

		private String loginName;

		public ShiroUser(String userLoginID, String loginName) {
			this.userLoginID = userLoginID;
			this.loginName = loginName;
		}

		public String getUserLoginID() {
			return userLoginID;
		}

		/**
		 * 本函数输出将作为默认的<shiro:principal/>输出.
		 */
		@Override
		public String toString() {
			return loginName;
		}

		/**
		 * 重载hashCode,只计算loginName;
		 */
		@Override
		public int hashCode() {
			return Objects.hashCode(loginName);
		}

		/**
		 * 重载equals,只计算loginName;
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			ShiroUser other = (ShiroUser) obj;
			if (loginName == null) {
				if (other.loginName != null) {
					return false;
				}
			} else if (!loginName.equals(other.loginName)) {
				return false;
			}
			return true;
		}
	}

}
