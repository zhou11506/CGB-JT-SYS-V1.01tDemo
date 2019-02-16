package com.jt.sys.service.realm;



import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jt.sys.dao.SysMenuDao;
import com.jt.sys.dao.SysRoleMenuDao;
import com.jt.sys.dao.SysUserDao;
import com.jt.sys.dao.SysUserRoleDao;
import com.jt.sys.entity.SysUser;

@Service
public class ShiroUserRealm extends AuthorizingRealm {
	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	@Autowired
	private SysMenuDao sysMenuDao;
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	
	/**在此方法完成用户权限信息的获取以及封装  授权*/
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// 1.获取当前用户
		System.out.println("doGetAuthorizationInfo");
		SysUser user= (SysUser) principals.getPrimaryPrincipal();
		//2.获取用户 拥有角色信息  基于用户id获取角色id  sys_user_roles
		List<Integer> roleIds = sysUserRoleDao.findRoleIdsByUserId(user.getId());
		if(roleIds==null||roleIds.size()==0){
			throw new AuthorizationException();
		}
		//3.获取这些角色对应菜单（资源，菜单id）基于角色id查菜单id sys_role_menus
		List<Integer> menuIds = sysRoleMenuDao.findMenuIdsByRoleIds(roleIds.toArray(new Integer[]{}));
		if(menuIds==null||menuIds.size()==0){
			throw new AuthorizationException();
		}
		//4.获取菜单id对应的权限标识（sys:user:add,...）  sys_menus
		List<String> permissions = sysMenuDao.findPermissions(menuIds.toArray(new Integer []{}));
		if(permissions==null||permissions.size()==0){
			throw new AuthorizationException();
		}
		//5.封装权限信息，返回
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		Set<String> set =new HashSet<>();
		//将数据放入set集合，去重复数据 
		for(String per:permissions){
			if(!StringUtils.isEmpty(per)){
				set.add(per);
			}
		}
		//接收set的集合数据
		info.setStringPermissions(set);
		return info;//返回给授权管理器
	}
	
	
	
	/**在此方法完成用户信息的获取以及封装  认证*/
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//1.从参数token中获取用户信息
		UsernamePasswordToken upToken=(UsernamePasswordToken)token;
		String username=upToken.getUsername();
		System.out.println(username);
		//2.基于用户名从数据库查找用户信息
		SysUser user=sysUserDao.findUserByUserName(username);
		
		//3.验证用户是否存在
		if(user==null){
			throw new UnknownAccountException();
			
		}
		
		//4.验证用户是否已被禁用
		if(user.getValid()==0){
			throw new LockedAccountException();
		}
		
		
		//5.封装用户信息（包含密码等）
		//盐值  ,处理盐值数据，封装为byteSource对象
	ByteSource	credentialsSalt=ByteSource.Util.bytes(user.getSalt());
		//5.2 封装用户信息（来自数据库）
		SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(user, user.getPassword(), credentialsSalt, getName());
		//user用户身份，//principal (身份)//hashedCredentials(已加密的密码)
		//credentialsSalt //realmName(当前类的名字)
		
		return info;//此值会返回给认证管理器
	}
	
	
	/**设置登录时使用的凭证匹配器*/
	@Override
	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
		// 1.构建凭证匹配器对象
		HashedCredentialsMatcher hcm = new HashedCredentialsMatcher("MD5");
		//2.设置加密次数（需同前面设置密码加密的次数一致）
		hcm.setHashIterations(1);
		//3.
		super.setCredentialsMatcher(hcm);
	}
	

}
