package me.wonwoo.user;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wonwoo on 2016. 9. 18..
 */
public interface UserRepository extends JpaRepository<User, Long> {

  User findByGithub(String github);
}
