/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ciar.domain.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class CustomService implements UserDetailsService {

    private DataSource dataSource;

    @Override
    public UserDetails loadUserByUsername(String username) {
        String sql = "SELECT * FROM usuarios WHERE usuario LIKE :username";
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("username", username);

        SimpleJdbcTemplate sjt = new SimpleJdbcTemplate(getDataSource());
        User user = sjt.queryForObject(sql, new UserMapper(), source);
        return user;
    }

    private GrantedAuthority[] getAuthorities(int userId) {
        String sql = "SELECT ur.role FROM user_roles ur INNER JOIN usuarios u ON u.id = ur.usuario WHERE u.id = ?";
        List<String> authorities = new SimpleJdbcTemplate(getDataSource()).query(sql, new RowMapper() {
            @Override
            public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString(1);
            }
        }, userId);

        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(authorities.size());

        for (String auth : authorities) {
            authList.add(new GrantedAuthorityImpl(auth));
        }
        return authList.toArray(new GrantedAuthority[]{});
    }

    private class UserMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int arg1) throws SQLException {
            return new CustomUser(rs.getString("usuario"), // username
                    rs.getString("senha"), // senha
                    rs.getBoolean("enabled"), //enabled
                    true, // accountNonExpired
                    true, // credentialsNonExpired
                    true, // accountNonLocked
                    getAuthorities(rs.getInt("id")), //authorities
                    rs.getString("nome")); // nome
        }
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}