package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTemplate {
    public void executeUpdate(User user, String sql, PreparedStatementSetter pss) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try{
            conn = ConnectionMananger.getConnection();
            pstmt = conn.prepareStatement(sql);
            pss.setter(pstmt);

            pstmt.executeUpdate();
        } finally{
            if(pstmt != null) {
                pstmt.close();
            }

            if(conn != null) {
                conn.close();
            }
        }
    }

    public Object executeQuery(String sql, PreparedStatementSetter pss, RowMapper rowMapper) throws SQLException{
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            conn = ConnectionMananger.getConnection();
            pstmt = conn.prepareStatement(sql);
            pss.setter(pstmt);
            rs = pstmt.executeQuery();

            Object obj = null;
            if(rs.next()) {
                return rowMapper.map(rs);
            }

            return obj;
        }finally {
            if(rs != null) {
                rs.close();
            }

            if(pstmt != null) {
                pstmt.close();
            }

            if(conn != null) {
                conn.close();
            }
        }
    }

}
