package com.mindingmybookness.Repository;

import com.mindingmybookness.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    List<User> findAllByUsernameContainingIgnoreCase(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail (String email);

    Optional<User> findUsersByUsername(String username);
}
