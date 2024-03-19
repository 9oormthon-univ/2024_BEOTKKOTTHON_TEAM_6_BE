package org.goormthon.beotkkotthon.rebook.repository;

import org.goormthon.beotkkotthon.rebook.domain.User;
import org.goormthon.beotkkotthon.rebook.dto.type.EProvider;
import org.goormthon.beotkkotthon.rebook.dto.type.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("select u.id as id, u.role as role, u.password as password from User u where u.id = :id and u.refreshToken is not null")
    Optional<UserSecurityForm> findFormById(@Param("id") UUID id);

    @Query("select u.id as id, u.role as role, u.password as password from User u where u.id = :id and u.refreshToken = :refreshToken")
    Optional<UserSecurityForm> findFormByIdAndRefreshToken(@Param("id") UUID id, @Param("refreshToken") String refreshToken);

    @Query("select u.id as id, u.role as role, u.password as password from User u where u.serialId = :serialId and u.provider = :provider")
    Optional<UserSecurityForm> findFormBySerialIdAndProvider(@Param("serialId") String serialId, @Param("provider") EProvider provider);

    @Modifying(clearAutomatically = true)
    @Query("update User u set u.refreshToken = :refreshToken where u.id = :id")
    void updateRefreshToken(UUID id, String refreshToken);

    interface UserSecurityForm {
        UUID getId();
        ERole getRole();
        String getPassword();

        // User To UserSecurityForm
        static UserSecurityForm of(User user) {
            return new UserSecurityForm() {
                @Override
                public UUID getId() {
                    return user.getId();
                }

                @Override
                public ERole getRole() {
                    return user.getRole();
                }

                @Override
                public String getPassword() {
                    return user.getPassword();
                }
            };
        }
    }
}
