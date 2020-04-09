package com.bookstore.admin.portal.repository;

import org.springframework.data.repository.CrudRepository;

import com.bookstore.admin.portal.Enum.RoleName;
import com.bookstore.admin.portal.entity.Role;


public interface RoleRepository extends CrudRepository<Role, Long> {
	
	Role findByname(RoleName name);
}
