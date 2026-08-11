package com.duong.ecommerce.repoImp;

import com.duong.ecommerce.exception.ResourceNotFoundException;
import com.duong.ecommerce.model.User;
import com.duong.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImp implements UserRepository {

    private final DataSource dataSource;

    @Override
    public Optional<User> findByUsername(String username) {
        String sql = "Select * from users where username = ?";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement pS = connection.prepareStatement(sql);
        ){
            pS.setString(1, username);
            try (ResultSet rs = pS.executeQuery()) {
                if (!rs.next()) {
                    Optional.empty();
                }

                User user = User.builder()
                        .userId(rs.getLong("user_id"))
                        .username(rs.getString("username"))
                        .password(rs.getString("password"))
                        .email(rs.getString("email"))
                        .firstName(rs.getString("first_name"))
                        .lastName(rs.getString("last_name"))
                        .phoneNumber(rs.getString("phone_number"))
                        .dateOfBirth(rs.getDate("date_of_birth").toLocalDate())
                        .createdAt(rs.getTimestamp("created_at").toInstant())
                        .build();

                return Optional.of(user);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public void save(User user) {
        String sql = """
                    INSERT INTO users (username, password, email, first_name, last_name, phone_number, date_of_birth)
                 VALUES (?, ?, ?, ?, ?, ?, ?)
                """;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement pS = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS) // nghĩa là khi object được generate hãy lấy key về
        ){
            pS.setString(1, user.getUsername());
            pS.setString(2,user.getPassword());
            pS.setString(3,user.getEmail());
            pS.setString(4, user.getFirstName());
            pS.setString(5, user.getLastName());
            pS.setString(6, user.getPhoneNumber());
            pS.setDate(7, Date.valueOf(user.getDateOfBirth()));
             pS.executeUpdate();
             try(ResultSet rs = pS.getGeneratedKeys()){
                  if(rs.next()){
                      user.setUserId(rs.getLong(1));
                  }
              }
                System.out.println(user);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteUserByUsername(String username) {
        String sql = "DELETE FROM users WHERE username = ?";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement pS = connection.prepareStatement(sql)
        ){
            pS.setString(1, username);
            pS.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateUser(Long userId, User user) {
        String sql = "UPDATE users SET username = ?, " +
                "password = ?, " +
                "email = ?, " +
                "first_name = ?, " +
                "last_name = ?, " +
                "phone_number = ?, " +
                "date_of_birth = ? " +
                "WHERE user_id = ?";
        try (Connection connection = dataSource.getConnection();
            PreparedStatement pS = connection.prepareStatement(sql)
        ){
            pS.setString(1, user.getUsername());
            pS.setString(2, user.getPassword());
            pS.setString(3, user.getEmail());
            pS.setString(4, user.getFirstName());
            pS.setString(5, user.getLastName());
            pS.setString(6, user.getPhoneNumber());
            pS.setDate(7, Date.valueOf(user.getDateOfBirth()));
            pS.setLong(8, userId);
            int row = pS.executeUpdate();
            if (row == 0) {
                throw new ResourceNotFoundException("User is not existed");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
