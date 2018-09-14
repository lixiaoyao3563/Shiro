package com.lxy.shiro.realms;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;

public class ShiroRealm extends AuthenticatingRealm {

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
			throws AuthenticationException {
		
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		
		String username = upToken.getUsername();
		
		System.out.println("从数据库中查询用户:"+username +"的信息。");
		
//		if("unknown".equals(username)) {
//			throw new UnknownAccountException("用户不存在！");
//		}
//		if("monster".equals(username)) {
//			throw new LockedAccountException("用户被锁定！");
//		}
		Object credentials = "123456"; //明文密码
		if("admin".equals(username)) {
			credentials = "038bdaf98f2037b31f1e75b5b4c9b26e";
		}else if ("guest".equals(username)) {
			credentials = "6af5141317258b4c866bd2bfe6b16e4e";
		}
		
		Object principal = username;
//		Object credentials = "123456";
		String realmName = getName();
		ByteSource credentialsSalt = ByteSource.Util.bytes(username);
		
//		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal, credentials, realmName);
		SimpleAuthenticationInfo info  = new SimpleAuthenticationInfo(principal, credentials, credentialsSalt, realmName);
		
		return info;
	}
	
	//MD5盐值加密
	public static void main(String[] args) {
		String algorithmName = "MD5";
		Object source = "123456";
		Object salt = ByteSource.Util.bytes("guest");
		int hashIterations = 1024;
		
		Object result = new SimpleHash(algorithmName, source, salt, hashIterations);
		System.out.println(result);
		
	}
	

}
