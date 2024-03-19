package org.goormthon.beotkkotthon.rebook.repository;

import org.goormthon.beotkkotthon.rebook.domain.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserStatusRepository extends JpaRepository<UserStatus, UUID> {
}
