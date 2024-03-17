package com.creswave.repository;


import com.creswave.model.Role;
import com.creswave.model.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository  extends JpaRepository<Role,Long> {
    Optional<Role> findByName(RoleEnum name);

    Boolean existsByName(RoleEnum name);

}
