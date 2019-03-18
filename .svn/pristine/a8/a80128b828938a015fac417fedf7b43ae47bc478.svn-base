package com.dafy.config.service;

import com.dafy.config.dao.DAO;
import com.dafy.config.domain.GroupInfo;
import com.dafy.config.domain.Role;
import com.dafy.config.domain.User;
import com.dafy.config.utils.CommonUtils;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class UserRoleService {

	@Autowired
	private DAO<User> userDao;

	@Autowired
	private DAO<Role> roleDao;

	@Autowired
	private DAO<GroupInfo> groupInfoDAO;

	private Cache<String, Object> userRoleCache = CacheBuilder.newBuilder().concurrencyLevel(5).initialCapacity(512)
			.expireAfterWrite(60,TimeUnit.SECONDS)
			.build();

	public User getUserByUsername(String username) {
		User user = userDao.getModelBy("UserRoleMapper.getUserByUsername", username);
		if (user != null) {
			user.getRoles().addAll(this.getRoleListByUserId(user));
			user.getGroups().addAll(this.getGroupIdListByRoleId(user.getRoles()));
		}
		return user;
	}

	public boolean changePwd(String username,String originPwd,String newPwd){
		User user = getUserByUsername(username);
		if(user == null){
			return false;
		}
		if(!user.getPassword().equals(CommonUtils.md5(originPwd+"{" +username+ "}"))){
			return false;
		}
		Map<String,Object> map = Maps.newHashMap();
		map.put("username",username);
		map.put("password",CommonUtils.md5(newPwd+"{" +username+ "}"));
		return userDao.update("UserRoleMapper.updateUserPwd",map) == 1;
	}

	@Transactional(readOnly = false)
	public void save(User user){
		if(user.getId() > 0){
			doUpdate(user);
		}else{
			dosave(user);
		}
	}

	@Transactional(readOnly = false)
	public void enable(User user){
		userDao.update("UserRoleMapper.updateUserEnable", user);
	}

	public void doUpdate(User user){
		List<Role> roles = getRoleListByUserId(user);
		Map<Long, Role> oldRoles = new HashMap<>();
		for(Role r : roles){
			oldRoles.put(r.getId(), r);
		}

		Set<Role> newRoles = new HashSet<>();

		for(Role r : user.getRoles()){
			if (oldRoles.get(r.getId())==null){
				newRoles.add(r);
			}else{
				oldRoles.remove(r.getId());
			}
		}
		user.setRoles(newRoles);

		if(!oldRoles.isEmpty()){
			User newUser = new User();
			newUser.setId(user.getId());
			newUser.setRoles(Sets.newHashSet(oldRoles.values()));
			userDao.delete("UserRoleMapper.deleteUserRoleRelation", newUser);
		}

		int i = userDao.update("UserRoleMapper.updateUser", user);
		if (i > 0 && !user.getRoles().isEmpty()) {
			this.saveUserRoleRelation(user);
		}
		this.userRoleCache.cleanUp();
	}

	public void dosave(User user) {
		int i = this.userDao.save("UserRoleMapper.saveUser", user);
		if (i > 0) {
			this.saveUserRoleRelation(user);
			this.userRoleCache.cleanUp();
		}
	}

	public void saveUserRoleRelation(User user) {
		if (user != null && !user.getRoles().isEmpty()) {
			this.userDao.save("UserRoleMapper.saveUserRoleRelation", user);
		}
	}

	public List<Role> getRoleListByUserId(final User user) {
		return UserRoleService.this.roleDao.getListBy("UserRoleMapper.getRoleListByUserId", user.getId());
	}

	public List<GroupInfo> getGroupIdListByRoleId(Set<Role> roles){
		String roleIds = "";
		for(Role role : roles){
			roleIds += (role.getId() + ",");
		}
		if(roleIds.length() <= 0){
			return Collections.emptyList();
		}
		roleIds = roleIds.substring(0,roleIds.length() - 1);
		if(roleIds == null || roles.size() <= 0){
			return Collections.emptyList();
		}
		Map<String,Object> map = Maps.newHashMap();
		map.put("roles",roles);
		return groupInfoDAO.getListBy("GroupInfoMapper.getGroupListByRoleId",map);
	}

}
